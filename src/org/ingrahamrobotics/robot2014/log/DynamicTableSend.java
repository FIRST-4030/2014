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

import java.util.Hashtable;
import org.ingrahamrobotics.dotnettables.DotNetTable;
import org.ingrahamrobotics.util.LinkedList;

public class DynamicTableSend {

    private final LinkedList tablesNeedingUpdate = new LinkedList();
    private Boolean needsFullUpdate;
    private final Hashtable tables = new Hashtable();
    private final Object threadPausedLock = new Object();

    public DynamicTableSend() {
        new UpdateThread().start();
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
            while (true) {
                long lastFullUpdate;

                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (needUpdateLock) {
                    long currentTime = System.currentTimeMillis();
                    while ((table = (DotNetTable) tablesNeedingUpdate.poll()) != null) {
                        System.out.println("Updating table " + table.name());
                        table.send();
                    }
                }
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
                table.send();
            }
        }

        private void updateAllTables() {
            DotNetTable table;
        }
    }
}
