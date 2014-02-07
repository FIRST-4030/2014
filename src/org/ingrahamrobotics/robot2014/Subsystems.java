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

import org.ingrahamrobotics.robot2014.commands.GroundDriveFastLeft;
import org.ingrahamrobotics.robot2014.commands.GroundDriveFastRight;
import org.ingrahamrobotics.robot2014.commands.GroundDriveToggleHighSpeed;
import org.ingrahamrobotics.robot2014.commands.ReverseGroundDrive;
import org.ingrahamrobotics.robot2014.input.BMap;
import org.ingrahamrobotics.robot2014.input.JInput;
import org.ingrahamrobotics.robot2014.subsystems.ExampleSubsystem;
import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;
import org.ingrahamrobotics.robot2014.subsystems.GroundDriveShifter;
import org.ingrahamrobotics.robot2014.subsystems.TurnTable;

public class Subsystems {

    public static Subsystems instance = new Subsystems();
    public final ExampleSubsystem exampleSubsystem;
    public final GroundDrive groundDrive;
    public final GroundDriveShifter groundDriveShifter;
    public final TurnTable turnTable;

    public Subsystems() {
        exampleSubsystem = new ExampleSubsystem();
        groundDrive = new GroundDrive();
        groundDriveShifter = new GroundDriveShifter();
        turnTable = new TurnTable();
    }

    public void initCommands() {
        JInput.getButton(BMap.reverseGroundDrive).whenPressed(new ReverseGroundDrive());
        JInput.getButton(BMap.groundDriveFastLeft).whenPressed(new GroundDriveFastLeft());
        JInput.getButton(BMap.groundDriveFastRight).whenPressed(new GroundDriveFastRight());
        JInput.getButton(BMap.groundDriveToggleHighSpeed).whenActive(new GroundDriveToggleHighSpeed());
    }
}
