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

    private static Settings instance;
    private final DotNetTable defaultSettings = DotNetTables.publish("robot-input-default");
    private final DotNetTable driverSettings = DotNetTables.subscribe("robot-input");
    private final DotNetTable driverLoop = DotNetTables.publish("robot-input-loopback");

    public void publishDefaults() {
        defaultSettings.setValue("test-setting-1", "default-value-1");
        defaultSettings.setValue("test-set-2", "default-2");
        defaultSettings.setInterval(3000);
        driverSettings.onChange(this);
        driverSettings.onStale(this);
    }

    public void changed(DotNetTable table) {
        driverLoop.setValue("_counter", table.getValue("_counter"));
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
            return defaultSettings.getValue(key);
        } else {
            throw new IllegalArgumentException("Unknown setting");
        }
    }

    public static void initInstance() {
        instance = new Settings();
    }

    public static String get(String key) {
        return instance.getSetting(key);
    }
}
