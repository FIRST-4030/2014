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

public class PullCollectorMotors extends Command {

    private final Subsystems ss = Subsystems.instance;
    private boolean finished;

    public PullCollectorMotors() {
        requires(ss.collectorMotors);
    }

    protected void initialize() {
        finished = false;
    }

    protected void execute() {
        if (ss.collectorMotors.getBothSpeed() < 0) {
            ss.collectorMotors.setBothSpeed(0);
        } else {
            ss.collectorMotors.setBothSpeed(0.75);
        }
        finished = true;
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}