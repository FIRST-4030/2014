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

public class OutputLevel {

    public static final OutputLevel DEBUG = new OutputLevel(-1, "Debug");
    public static final OutputLevel INITIALIZED_SYSTEMS = new OutputLevel(0, "Initialized");
    public static final OutputLevel RAW_MOTORS = new OutputLevel(1, "Raw Motors");
    public static final OutputLevel RAW_SENSORS = new OutputLevel(2, "Raw Sensors");
    public static final OutputLevel CMU = new OutputLevel(3, "CMUcam");
    public static final OutputLevel AUTO = new OutputLevel(4, "Autonomous");
    public static final OutputLevel HIGH = new OutputLevel(5, "Important");
    public final int level;
    public final String name;

    public OutputLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
