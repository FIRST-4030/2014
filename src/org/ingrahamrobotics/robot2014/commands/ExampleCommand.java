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

import org.ingrahamrobotics.robot2014.SubsystemStore;
import edu.wpi.first.wpilibj.command.Command;

public class ExampleCommand extends Command {

    private final SubsystemStore ss;
    private boolean finished;

    public ExampleCommand(SubsystemStore ss) {
        this.ss = ss;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ss.exampleSubsystem.doSomething();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    protected void interrupted() {
    }
}
