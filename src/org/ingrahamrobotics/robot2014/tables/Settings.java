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

import org.ingrahamrobotics.dotnettables.DotNetTable;
import org.ingrahamrobotics.dotnettables.DotNetTables;

public class Settings implements DotNetTable.DotNetTableEvents {

    public static final String CMUCAM_DEFAULT_COLOR = "CMUcam Default Color";
    public static final String AUTOCOMMAND_USE_ENCODERS = "AutoCommand Use Encoders";
    public static final String AUTOCOMMAND_STOP_TIME = "AutoCommand Stopped Time";
    public static final String AUTOCOMMAND_ENCODER_DISTANCE = "AutoCommand Encoder Distance";
    public static final String FEEDBACK_KEY = "_DRIVER_FEEDBACK_KEY";
    private static Settings instance;
    private final DotNetTable defaultSettings = DotNetTables.publish("robot-input-default");
    private final DotNetTable driverSettings = DotNetTables.subscribe("robot-input");

    public void publishDefaults() {
        defaultSettings.setValue(CMUCAM_DEFAULT_COLOR, "RED");
        defaultSettings.setValue(AUTOCOMMAND_USE_ENCODERS, "true");
        defaultSettings.setValue(AUTOCOMMAND_STOP_TIME, "0.001");
        defaultSettings.setValue(AUTOCOMMAND_ENCODER_DISTANCE, "23000");
        defaultSettings.setInterval(3000);
        driverSettings.onChange(this);
        driverSettings.onStale(this);
        outputSettings();
    }

    public void changed(DotNetTable table) {
        String feedback = table.getValue(FEEDBACK_KEY);
        if (feedback != null) {
            defaultSettings.setValue(FEEDBACK_KEY, feedback);
            defaultSettings.send();
            outputSettings();
        }
    }

    public void stale(DotNetTable table) {
        System.out.println("--- Warning, driver settings stale");
    }

    private void outputSettings() {
        Output.output(OutputLevel.DEBUG, "AutoCommand:UseEncoders", getBoolSetting(AUTOCOMMAND_USE_ENCODERS));
        Output.output(OutputLevel.DEBUG, "AutoCommand:EncoderDistance", (long) getDouble(Settings.AUTOCOMMAND_ENCODER_DISTANCE));
        Output.output(OutputLevel.DEBUG, "AutoCommand:StopTime", (long) (getDouble(Settings.AUTOCOMMAND_STOP_TIME) * 1000));
    }

    public String getSetting(String key) {
        if (driverSettings.exists(key)) {
            return driverSettings.getValue(key);
        } else if (defaultSettings.exists(key)) {
            return defaultSettings.getValue(key);
        } else {
            throw new IllegalArgumentException("Unknown setting");
        }
    }

    public double getDoubleSetting(String key) {
        try {
            return Double.parseDouble(getSetting(key));
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    public boolean getBoolSetting(String key) {
        if (getSetting(key).equalsIgnoreCase("true")) {
            return true;
        }
        return false;
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
