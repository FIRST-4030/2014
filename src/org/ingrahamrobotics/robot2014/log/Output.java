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
import java.util.Hashtable;
import org.ingrahamrobotics.dotnettables.DotNetTable;

/**
 * Output, for user feedback.
 */
public class Output {

    private static final Output instance = new Output();
    private final Hashtable values = new Hashtable();
    private final DynamicTableSend tableSend = new DynamicTableSend();
    private final DotNetTable nameTable = tableSend.publish("output-tables");

    private void outputConsole(OutputLevel level, String key, String value) {
        if (value == null) {
            System.out.println("[Output][" + level + "] -" + key);
        } else {
            System.out.println("[Output][" + level + "][" + key + "] " + value);
        }
    }

    private void outputDash(OutputLevel level, String key, String value) {
        DotNetTable table = tableSend.getPublished("output:" + level.level);
        if (table == null) {
            table = tableSend.publish("output:" + level.level);
            nameTable.setValue("output:" + level.level, level.name);
            tableSend.tableChanged(nameTable);
        }
        if (value == null) {
            table.remove(key);
        } else {
            table.setValue(key, value);
        }
        tableSend.tableChanged(table);
        SmartDashboard.putString(key, value);
    }

    public void outputInternal(OutputLevel level, String key, String message) {
        if (key == null) {
            return;
        }
        boolean changed = false;
        synchronized (values) {
            String oldMessage = (String) values.get(key);
            if (message == null ? oldMessage != null : !message.equals(oldMessage)) {
                values.put(key, message);
                changed = true;
            }
        }
        if (changed) {
            outputConsole(level, key, message);
            outputDash(level, key, message);
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
}
