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

import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.log.Output;
import org.ingrahamrobotics.robot2014.log.OutputLevel;
import org.ingrahamrobotics.robot2014.util.SolenoidPair;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class GroundDriveShifter extends Subsystem {

    private final SolenoidPair shifterPair = new SolenoidPair(Vst.SOLENOID.GROUND_DRIVE_SHIFTER_EXTEND, Vst.SOLENOID.GROUND_DRIVE_SHIFTER_RETRACT, true);

    public GroundDriveShifter() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "GroundDrive:Initialized", true);
    }

    protected void initDefaultCommand() {
    }

    public void setSpeed(boolean speed) {
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:HighSpeed", speed);
        shifterPair.setExtending(speed);
    }
}
