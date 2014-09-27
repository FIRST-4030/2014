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
import org.ingrahamrobotics.robot2014.input.AMap;
import org.ingrahamrobotics.robot2014.input.JInput;

public class RunGroundDrive extends Command {

    private final Subsystems ss = Subsystems.instance;
    private static final boolean TANK = false;

    public RunGroundDrive() {
        requires(ss.groundDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (TANK) {
            double left = JInput.getAxis(AMap.tankDriveLeft);
            double right = JInput.getAxis(AMap.tankDriveRight);
            if (left < 0.05 && left > -0.05) {
                left = 0;
            }
            if (right < 0.05 && right > -0.05) {
                right = 0;
            }
            ss.groundDrive.pidTankDrive(left, right);
        } else {
            double speed = JInput.getAxis(AMap.arcadeDriveY);
            double turn = JInput.getAxis(AMap.arcadeDriveX);
            if (speed < 0.05 && speed > -0.05) {
                speed = 0;
            }
            if (turn < 0.05 && turn > -0.05) {
                turn = 0;
            }
            ss.groundDrive.pidArcadeDrive(speed, turn);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ss.groundDrive.stop();
    }

    protected void interrupted() {
        ss.groundDrive.stop();
    }
}
