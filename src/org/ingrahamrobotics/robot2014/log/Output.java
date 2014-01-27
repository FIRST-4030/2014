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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Main RobotDebugger Class. Always use this instead of pushing directly to
 * SmartDashboard/Console.
 */
public class Output {

    private static final Output main = new Output();
    private final Hashtable valueTable = new Hashtable();

    private void outputConsole(String key, String value) {
        System.out.println("[Output] [" + key + "] " + value);
    }

    private void outputDash(String key, String value) {
        SmartDashboard.putString(key.replace(':', '|'), value);
    }

    public void outputInternal(String key, String message, boolean dashboard) {
        if (key == null || message == null) {
            return;
        }
        StoredInfo old;
        synchronized (valueTable) {
            old = (StoredInfo) valueTable.get(key);
            if (old == null) {
                valueTable.put(key, new StoredInfo(message, dashboard));
            } else if (!message.equals(old.message)) {
                old.message = message;
            }
        }
        if (old == null || !message.equals(old.message)) {
            outputConsole(key, message);
        }
        if (dashboard) {
            outputDash(key, message);
        }
    }

    /**
     * Clear the map. This will basically force RobotDebugger to push the next
     * values of keys to SmartDashboard/Console.
     *
     * Regularly the RobotDebugger stores the last set value for a key, and
     * won't push to Console/SmartDashboard unless that value has changed. This
     * method removes all stored key values.
     */
    public void pushAll() {
        Enumeration e = valueTable.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            StoredInfo info = (StoredInfo) valueTable.get(key);
            if (info.dashboard) {
                outputDash(key, info.message);
            }
        }
    }

    public static void output(String key, String value, boolean dashboard) {
        main.outputInternal(key, value, dashboard);
    }

    public static void output(String key, int value, boolean dashboard) {
        main.outputInternal(key, String.valueOf(value), dashboard);
    }

    public static void output(String key, double value, boolean dashboard) {
        main.outputInternal(key, String.valueOf(((int) (value * 100)) / 100.0), dashboard);
    }

    public static void output(String key, short value, boolean dashboard) {
        main.outputInternal(key, String.valueOf(value), dashboard);
    }

    public static void output(String key, boolean value, boolean dashboard) {
        main.outputInternal(key, String.valueOf(value ? "Yes" : "No"), dashboard);
    }

    public static void repushDashboard() {
        main.pushAll();
    }

    public static class StoredInfo {

        private String message;
        private boolean dashboard;

        public StoredInfo(String message, boolean dashboard) {
            this.message = message;
            this.dashboard = dashboard;
        }
    }
}
