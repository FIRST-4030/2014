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

    public RunGroundDrive() {
        requires(ss.groundDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
//        ss.groundDrive.arcadeDrive(JInput.getAxis(AMap.arcadeDriveY), JInput.getAxis(AMap.arcadeDriveX));
        ss.groundDrive.pidTankDrive(JInput.getAxis(AMap.tankDriveLeft), JInput.getAxis(AMap.tankDriveRight));
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
