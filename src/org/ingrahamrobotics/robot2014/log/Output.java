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

/**
 * Output, for user feedback.
 */
public class Output {

    private static final Output instance = new Output();
    private final Hashtable values = new Hashtable();
    private final Hashtable tables = new Hashtable();
    private final Object needUpdateLock = new Object();
    private final LinkedList updatingTables = new LinkedList();
    private final DotNetTable tableNames = DotNetTables.publish("output-tables");
    private boolean updateRunning = false;

    private void outputConsole(OutputLevel level, String key, String value) {
        System.out.println("[Output][" + level + "][" + key + "] " + value);
    }

    private void outputDash(OutputLevel level, String key, String value) {
        DotNetTable table = (DotNetTable) tables.get("output-" + level.level);
        if (table == null) {
            table = DotNetTables.publish(String.valueOf(level.level));
            tables.put(level, table);
            tableNames.setValue("output-" + level.level, level.name);
        }
        table.setValue(key, value);
        synchronized (needUpdateLock) {
            if (!updatingTables.contains(table)) {
                updatingTables.add(table);
            }
            if (!updateRunning) {
                updateRunning = true;
                new UpdateThread().start();
            }
        }
//        SmartDashboard.putString(key, value);
    }

    public void outputInternal(OutputLevel level, String key, String message) {
        if (key == null || message == null) {
            return;
        }
        boolean changed = false;
        synchronized (values) {
            String oldMessage = (String) values.get(key);
            if (oldMessage == null || !message.equals(oldMessage)) {
                values.put(key, message);
                changed = true;
            }
        }
        if (changed) {
            outputConsole(level, key, message);
            outputDash(level, key, message);
        }
    }

    /**
     * Resends all tables.
     */
    public void pushAll() {
        for (Enumeration e = tables.elements(); e.hasMoreElements();) {
            ((DotNetTable) e.nextElement()).send();
        }
    }

    public static void output(OutputLevel level, String key, String value) {
        instance.outputInternal(level, key, value);
    }

    public static void output(OutputLevel level, String key, int value) {
        instance.outputInternal(level, key, String.valueOf(value));
    }

    public static void output(OutputLevel level, String key, double value) {
        instance.outputInternal(level, key, String.valueOf(((int) (value * 100)) / 100.0));
    }

    public static void output(OutputLevel level, String key, short value) {
        instance.outputInternal(level, key, String.valueOf(value));
    }

    public static void output(OutputLevel level, String key, boolean value) {
        instance.outputInternal(level, key, value ? "true" : "false");
    }

    public static void repushDashboard() {
        instance.pushAll();
    }

    private class UpdateThread extends Thread {

        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            synchronized (needUpdateLock) {
                updateRunning = false;
                DotNetTable table;
                while ((table = (DotNetTable) updatingTables.poll()) != null) {
                    table.send();
                }
            }
        }
    }
}
