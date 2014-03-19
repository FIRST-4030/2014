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
import org.ingrahamrobotics.robot2014.commands.AutoStateCommand;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.tables.Settings;

public class RobotMain extends IterativeRobot {

    private Command autoCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        DotNetTables.startCRIO();
        Settings.initInstance();
        Output.output(OutputLevel.HIGH, "RobotState", "Starting");
        autoCommand = new AutoStateCommand();
        // Initialize all commands
        Subsystems.instance.initCommands();
        Output.output(OutputLevel.HIGH, "RobotState", "Disabled");
    }

    public void autonomousInit() {
        Output.output(OutputLevel.HIGH, "RobotState", "Autonomous");
        autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        Output.output(OutputLevel.HIGH, "RobotState", "Teleoperated");
        autoCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    public void testInit() {
        Output.output(OutputLevel.HIGH, "RobotState", "Test");
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    public void disabledInit() {
        Output.output(OutputLevel.HIGH, "RobotState", "Disabled");
        autoCommand.cancel();
    }

    public void disabledPeriodic() {
    }
}
