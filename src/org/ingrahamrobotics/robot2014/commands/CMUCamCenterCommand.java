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
package org.ingrahamrobotics.robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.ingrahamrobotics.robot2014.Subsystems;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;

public class CMUCamCenterCommand extends Command {

    private final Subsystems ss = Subsystems.instance;

    public CMUCamCenterCommand() {
        requires(ss.turnTable);
    }

    protected void initialize() {
    }

    protected void execute() {
        int[] values = ss.cmuCam.getColorTrackingData();
        int x = values[0];
        int y = values[1];
        if (x == 0 || y == 0) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "No objects found");
        } else if (x > 240 || x < 0) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "X value " + x + " out of range (0 - 240).");
        } else if (y > 240 || y < 0) {
            Output.output(OutputLevel.RAW_SENSORS, "CmuCam:CenterCommand", "Y value " + y + " out of range (0 - 240).");
        } else {
            Output.output(OutputLevel.LOW, "CmuCam:CenterCommand", "Object at [" + (x - 120) + ", " + (y - 120) + "]");
            ss.turnTable.drive((x - 120) / 120);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ss.turnTable.drive(0);
    }

    protected void interrupted() {
        ss.turnTable.drive(0);
    }
}
