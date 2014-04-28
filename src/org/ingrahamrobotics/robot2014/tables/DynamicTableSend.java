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

import java.util.Hashtable;
import org.ingrahamrobotics.dotnettables.DotNetTable;
import org.ingrahamrobotics.dotnettables.DotNetTables;

public class DynamicTableSend {

    private static final long MAX_TIME = 5000;
    private final Hashtable tables = new Hashtable();

    public DynamicTableSend() {
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
                table.setInterval((int) MAX_TIME);
                tables.put(name, table);
            }
            return table;
        }
    }
}
