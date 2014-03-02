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
 *//*
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

public class OldAutoCommand extends Command {

    private final Subsystems ss = Subsystems.instance;

    public OldAutoCommand() {
        requires(ss.groundDrive);
    }

    protected void initialize() {
        ss.encoders.reset();
        new ExtendCollectorSolenoids().start();
    }

    protected void execute() {
        ss.groundDrive.setRaw(1, 1);
    }

    protected boolean isFinished() {
        return ss.encoders.getRightEncoder() > 28875 || ss.encoders.getLeftEncoder() > 28875
                || ss.encoders.getRightEncoder() < -28875 || ss.encoders.getLeftEncoder() < -28875;
    }

    protected void end() {
        ss.groundDrive.stop();
        new ExtendShooterSolenoids().start();
    }

    protected void interrupted() {
        ss.groundDrive.stop();
    }
}
