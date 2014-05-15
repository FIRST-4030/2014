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
package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class TurnTableStops extends Subsystem {

    private final DigitalInput leftInput = new DigitalInput(Vst.DIGITAL_IO.LEFT_TURNTABLE_SWITCH);
    private final DigitalInput rightInput = new DigitalInput(Vst.DIGITAL_IO.RIGHT_TURNTABLE_SWITCH);
    private long lastLeft;
    private long lastRight;

    protected void initDefaultCommand() {
    }

    public boolean getLeft() {
        boolean value = leftInput.get();
        if (value) {
            lastLeft = System.currentTimeMillis();
        } else {
            // If we were triggered in the last 500 milliseconds, treat it as still triggered.
            if (System.currentTimeMillis() - lastLeft < 500) {
                value = true;
            }
        }
        Output.output(OutputLevel.RAW_SENSORS, "TurnTable:LeftSwitch", value);
        return value;
    }

    public boolean getRight() {
        boolean value = rightInput.get();
        if (value) {
            lastRight = System.currentTimeMillis();
        } else {
            // If we were triggered in the last 500 milliseconds, treat it as still triggered.
            if (System.currentTimeMillis() - lastRight < 500) {
                value = true;
            }
        }
        Output.output(OutputLevel.RAW_SENSORS, "TurnTable:RightSwitch", value);
        return value;
    }
}
