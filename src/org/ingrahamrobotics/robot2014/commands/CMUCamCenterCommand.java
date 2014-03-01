/*
 * Copyright (C) 2014 Ingraham Robotics Team 4030
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implie d warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ingrahamrobotics.robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.ingrahamrobotics.robot2014.Subsystems;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;

public class CMUCamCenterCommand extends Command {

    private static final boolean TURN_TABLE = false;
    private static final boolean SMOOTH = false;
    private final Subsystems ss = Subsystems.instance;

    public CMUCamCenterCommand() {
        if (TURN_TABLE) {
            requires(ss.turnTable);
        } else {
            requires(ss.groundDrive);
        }
    }

    protected void initialize() {
    }

    protected void execute() {
        int[] values = ss.cmuCam.getColorTrackingData();
        int x = values[0] - 120;
        int y = values[1] - 120;
        if (x == 0 || y == 0) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "No objects found");
        } else if (x > 120 || x < -120) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "X value " + x + " out of range (-120 - 120).");
        } else if (y > 120 || y < -120) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "Y value " + y + " out of range (-120 - 120).");
        } else if (SMOOTH) {
            Output.output(OutputLevel.LOW, "CmuCam:CenterCommand", "Object at [" + (x - 120) + ", " + (y - 120) + "]");
            drive(x / 120.0);
        } else if (x < 100) {
            drive(-0.8);
        } else if (x > 100) {
            drive(0.8);
        } else if (x < 50) {
            drive(-0.5);
        } else if (x > 50) {
            drive(0.5);
        } else if (x < 0) {
            drive(-0.2);
        } else if (x > 0) {
            drive(0.2);
        } else {
            drive(0);
        }
    }

    private void drive(double speed) {
        if (TURN_TABLE) {
            ss.turnTable.drive(speed);
        } else {
            ss.groundDrive.arcadeDrive(0, speed);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        if (TURN_TABLE) {
            ss.turnTable.drive(0);
        } else {
            ss.groundDrive.stop();
        }
    }

    protected void interrupted() {
        if (TURN_TABLE) {
            ss.turnTable.drive(0);
        } else {
            ss.groundDrive.setRaw(0, 0);
        }
    }
}
