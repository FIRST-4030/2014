/*
 * Copyright (C) 2014 Ingraham Robotics Team 4030
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ingrahamrobotics.robot2014.log;

import java.util.Enumeration;
import java.util.Hashtable;
import org.ingrahamrobotics.dotnettables.DotNetTable;
import org.ingrahamrobotics.dotnettables.DotNetTables;
import org.ingrahamrobotics.util.LinkedList;
import org.ingrahamrobotics.util.ListIterator;

public class DynamicTableSend {

    private static final long MIN_TIME = 300;
    private static final long MAX_TIME = 5000;
    private final LinkedList tablesNeedingUpdate = new LinkedList();
    private final Hashtable tables = new Hashtable();
    private final Object threadPausedLock = new Object();

    public DynamicTableSend() {
        start();
    }

    private void start() {
        new UpdateThread().start();
    }

    public DotNetTable getPublished(String name) {
        synchronized (tables) {
            return (DotNetTable) tables.get(name);
        }
    }

    public DotNetTable publish(String name) {
        synchronized (tables) {
            DotNetTable table = (DotNetTable) tables.get(name);
            if (table == null) {
                table = DotNetTables.publish(name);
                tables.put(name, table);
            }
            return table;
        }
    }

    public void tableChanged(DotNetTable table) {
        synchronized (tablesNeedingUpdate) {
            if (!tablesNeedingUpdate.contains(table)) {
                tablesNeedingUpdate.add(table);
            }
        }
        synchronized (threadPausedLock) {
            threadPausedLock.notifyAll();
        }
    }

    private class UpdateThread extends Thread {

        public void run() {
            long lastFullUpdate = System.currentTimeMillis();
            long lastPartialUpdate = System.currentTimeMillis();
            long time;
            try {
                while (true) {
                    synchronized (threadPausedLock) {
                        threadPausedLock.wait(MAX_TIME);
                    }
                    time = System.currentTimeMillis();
                    if (time < lastPartialUpdate + MIN_TIME) {
                        Thread.sleep(MIN_TIME + lastPartialUpdate - time);
                    }
                    lastPartialUpdate = time = System.currentTimeMillis();
                    if (time > lastFullUpdate + MAX_TIME) {
                        lastFullUpdate = time;
                        updateAllTables();
                    } else {
                        updateTables();
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                start();
            }
        }

        private void updateTables() {
            DotNetTable table;
            while (true) {
                synchronized (tablesNeedingUpdate) {
                    table = (DotNetTable) tablesNeedingUpdate.poll();
                }
                if (table == null) {
                    break;
                }
                bump(table);
                table.send();
            }
        }

        private void updateAllTables() {
            synchronized (tablesNeedingUpdate) {
                tablesNeedingUpdate.clear();
            }
            LinkedList list = new LinkedList();
            synchronized (tables) {
                Enumeration e = tables.elements();
                ListIterator iterator = list.listIterator(0);
                while (e.hasMoreElements()) {
                    iterator.add(e.nextElement());
                }
            }
            DotNetTable table;
            for (ListIterator iterator = list.listIterator(0); iterator.hasNext();) {
                table = (DotNetTable) iterator.next();
                bump(table);
                table.send();
            }
        }

        /**
         * When running a DotNetTables server on the robot, sending a table
         * doesn't work if the number of keys is the same as last time.
         * <br>
         * This method is a hack to get around that issue. It will add or remove
         * the '_bump' key depending if it already exists.
         *
         * @param table the table to 'bump'.
         */
        private void bump(DotNetTable table) {
            if (table.exists("_bump")) {
                table.remove("_bump");
            } else {
                table.setValue("_bump", "_bump");
            }
        }
    }
}
