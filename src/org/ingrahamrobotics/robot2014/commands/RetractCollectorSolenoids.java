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

public class RetractCollectorSolenoids extends Command {

    private final Subsystems ss = Subsystems.instance;
    private boolean finished;

    public RetractCollectorSolenoids() {
        requires(ss.collectorSolenoids);
    }

    protected void initialize() {
        finished = false;
    }

    protected void execute() {
        ss.collectorSolenoids.setExtending(false);
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
