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

import edu.wpi.first.wpilibj.networktables2.util.List;
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
    private final Hashtable valueTable = new Hashtable();
    private final Hashtable tablesTable = new Hashtable();
    private final Hashtable lastSendDates = new Hashtable();
    private final Object needUpdateLock = new Object();
    private final LinkedList tablesNeedingUpdate = new LinkedList();
    private boolean updating = false;

    private void outputConsole(String key, String value) {
        System.out.println("[Output] [" + key + "] " + value);
    }

    private void outputDash(OutputLevel level, String key, String value) {
        DotNetTable table = (DotNetTable) tablesTable.get(level);
        if (table == null) {
            table = DotNetTables.publish(String.valueOf(level.level));
            table.setValue("table.name", level.name);
            tablesTable.put(level, table);
        }
        table.setValue(key, value);
        synchronized (needUpdateLock) {
            if (!tablesNeedingUpdate.contains(table)) {
                tablesNeedingUpdate.add(table);
            }
            if (!updating) {
                updating = true;
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                            updating = false;
                            return;
                        }
                        synchronized (needUpdateLock) {
                            updating = false;
                            DotNetTable table;
                            while ((table = (DotNetTable) tablesNeedingUpdate.poll()) != null) {
                                table.send();
                            }
                        }
                    }
                }.start();
            }
        }
//        SmartDashboard.putString(key, value);
    }

    public void outputInternal(OutputLevel level, String key, String message) {
        if (key == null || message == null) {
            return;
        }
        StoredInfo old;
        synchronized (valueTable) {
            old = (StoredInfo) valueTable.get(key);
            if (old == null) {
                valueTable.put(key, new StoredInfo(message, level));
            } else if (!message.equals(old.message)) {
                old.message = message;
            }
        }
        if (old == null || !message.equals(old.message)) {
            outputConsole(key, message);
        }
        outputDash(level, key, message);
    }

    /**
     * Re-push all tables.
     */
    public void pushAll() {
        for (Enumeration e = tablesTable.elements(); e.hasMoreElements();) {
            ((DotNetTable) e.nextElement()).send();
        }
//        Enumeration e = valueTable.keys();
//        while (e.hasMoreElements()) {
//            String key = (String) e.nextElement();
//            StoredInfo info = (StoredInfo) valueTable.get(key);
//            outputDash(info.level, key, info.message);
//        }
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
        instance.outputInternal(level, key, String.valueOf(value ? "Yes" : "No"));
    }

    public static void repushDashboard() {
        instance.pushAll();
    }

    public static class StoredInfo {

        private String message;
        private final OutputLevel level;

        public StoredInfo(String message, OutputLevel level) {
            this.message = message;
            this.level = level;
        }
    }
}
