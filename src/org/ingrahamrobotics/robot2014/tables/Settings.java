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
package org.ingrahamrobotics.robot2014.tables;

import java.util.Enumeration;
import java.util.Hashtable;
import org.ingrahamrobotics.dotnettables.DotNetTable;
import org.ingrahamrobotics.dotnettables.DotNetTables;

public class Settings implements DotNetTable.DotNetTableEvents {

    public static final String CMUCAM_DEFAULT_COLOR = "CMUcam Default Color";
    public static final String AUTOCOMMAND_USE_ENCODERS = "AutoCommand Use Encoders";
    public static final String AUTOCOMMAND_STOP_TIME = "AutoCommand Stopped Time";
    public static final String AUTOCOMMAND_ENCODER_DISTANCE = "AutoCommand Encoder Distance";
    private static Settings instance;
    private final DotNetTable defaultSettings = DotNetTables.publish("robot-input-default");
    private final DotNetTable driverSettings = DotNetTables.subscribe("robot-input");
    private final Hashtable lastValues = new Hashtable();

    public void publishDefaults() {
        // Defaults
        defaultSettings.setValue(AUTOCOMMAND_USE_ENCODERS, "true");
        defaultSettings.setValue(CMUCAM_DEFAULT_COLOR, "RED");
        defaultSettings.setValue(AUTOCOMMAND_USE_ENCODERS, "true");
        defaultSettings.setValue(AUTOCOMMAND_STOP_TIME, "0.001");
        defaultSettings.setValue(AUTOCOMMAND_ENCODER_DISTANCE, "23000");
        // End defaults
        defaultSettings.setInterval(3000);
        driverSettings.onChange(this);
        driverSettings.onStale(this);
    }

    public void changed(DotNetTable table) {
        String feedback = table.getValue("_DRIVER_FEEDBACK_KEY");
        for (Enumeration e = table.keys(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            if (key.startsWith("_")) {
                continue;
            }
            String value = table.getValue(key);
            if (!lastValues.containsKey(key)) {
                lastValues.put(key, value);
                System.out.println("[Settings] New key " + key + ": " + value);
            } else if (!lastValues.get(key).equals(value)) {
                lastValues.put(key, value);
                System.out.println("[Settings] Updated key " + key + ": " + value);
            }
        }
        if (feedback != null) {
            defaultSettings.setValue("_DRIVER_FEEDBACK_KEY", feedback);
            defaultSettings.send();
        }

    }

    public void stale(DotNetTable table) {
        System.out.println("--- Warning!");
        System.out.println("--- @ Driver setttings stale");
        System.out.println("--- Warning!");
    }

    public String getSetting(String key) {
        if (driverSettings.exists(key)) {
            return driverSettings.getValue(key);
        } else if (defaultSettings.exists(key)) {
//            System.out.println("Warning: No setting for " + key);
            return defaultSettings.getValue(key);
        } else {
            throw new IllegalArgumentException("Unknown setting");
        }
    }

    public double getDoubleSetting(String key) {
        if (driverSettings.exists(key)) {
            try {
                return Double.parseDouble(driverSettings.getValue(key));
            } catch (NumberFormatException ex) {
            }
        }
        if (defaultSettings.exists(key)) {
//            System.out.println("Warning: No setting for " + key);
            return Double.parseDouble(defaultSettings.getValue(key));
        } else {
            throw new IllegalArgumentException("Unknown setting");
        }
    }

    public boolean getBoolSetting(String key) {
        if (driverSettings.exists(key)) {
            String str = driverSettings.getValue(key);
            if (str.equalsIgnoreCase("true")) {
                return true;
            } else if (str.equalsIgnoreCase("false")) {
                return false;
            }
        }
        if (defaultSettings.exists(key)) {
//            System.out.println("Warning: No setting for " + key);
            return "true".equals(defaultSettings.getValue(key));
        } else {
            throw new IllegalArgumentException("Unknown setting");
        }
    }

    public static void initInstance() {
        instance = new Settings();
        instance.publishDefaults();
    }

    public static String get(String key) {
        return instance.getSetting(key);
    }

    public static double getDouble(String key) {
        return instance.getDoubleSetting(key);
    }

    public static boolean getBoolean(String key) {
        return instance.getBoolSetting(key);
    }
}
