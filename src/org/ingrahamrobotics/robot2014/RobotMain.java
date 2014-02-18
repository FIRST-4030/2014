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
package org.ingrahamrobotics.robot2014;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.ingrahamrobotics.dotnettables.DotNetTables;
import org.ingrahamrobotics.robot2014.commands.AutoCommand;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;

public class RobotMain extends IterativeRobot {

    private Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        DotNetTables.startServer();
        autoCommand = new AutoCommand();
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "Robot:State", "Starting");
        // Initialize all commands
        Subsystems.instance.initCommands();
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "Robot:State", "Ready!");
    }

    public void autonomousInit() {
        autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        autoCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    public void disabledInit() {
        autoCommand.cancel();
    }

    public void disabledPeriodic() {
    }
}
