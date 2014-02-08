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
import org.ingrahamrobotics.robot2014.commands.GroundDriveToggleShifter;
import org.ingrahamrobotics.robot2014.commands.GroundDriveToggleSoftwareLow;
import org.ingrahamrobotics.robot2014.commands.ReverseGroundDrive;
import org.ingrahamrobotics.robot2014.input.BMap;
import org.ingrahamrobotics.robot2014.input.JInput;
import org.ingrahamrobotics.robot2014.subsystems.ExampleSubsystem;
import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;
import org.ingrahamrobotics.robot2014.subsystems.GroundDriveShifter;
import org.ingrahamrobotics.robot2014.subsystems.PressureCompressor;
import org.ingrahamrobotics.robot2014.subsystems.PressureSwitch;
import org.ingrahamrobotics.robot2014.subsystems.TurnTable;

public class Subsystems {

    public static Subsystems instance = new Subsystems();
    public final ExampleSubsystem exampleSubsystem;
    public final GroundDrive groundDrive;
    public final GroundDriveShifter groundDriveShifter;
    public final TurnTable turnTable;
    public final PressureSwitch pressureSwitch;
    public final PressureCompressor compressor;

    public Subsystems() {
        exampleSubsystem = new ExampleSubsystem();
        groundDrive = new GroundDrive();
        groundDriveShifter = new GroundDriveShifter();
        turnTable = new TurnTable();
        pressureSwitch = new PressureSwitch();
        compressor = new PressureCompressor();
    }

    public void initCommands() {
        JInput.getButton(BMap.reverseGroundDrive1).whenPressed(new ReverseGroundDrive());
        JInput.getButton(BMap.groundDriveFastLeft1).whenPressed(new GroundDriveFastLeft());
        JInput.getButton(BMap.groundDriveFastRight1).whenPressed(new GroundDriveFastRight());
        JInput.getButton(BMap.groundDriveToggleShifter1).whenActive(new GroundDriveToggleShifter());
        JInput.getButton(BMap.groundDriveToggleSoftwareLow1).whenActive(new GroundDriveToggleSoftwareLow());

        JInput.getButton(BMap.reverseGroundDrive2).whenPressed(new ReverseGroundDrive());
        JInput.getButton(BMap.groundDriveFastLeft2).whenPressed(new GroundDriveFastLeft());
        JInput.getButton(BMap.groundDriveFastRight2).whenPressed(new GroundDriveFastRight());
        JInput.getButton(BMap.groundDriveToggleShifter2).whenActive(new GroundDriveToggleShifter());
        JInput.getButton(BMap.groundDriveToggleSoftwareLow2).whenActive(new GroundDriveToggleSoftwareLow());
    }
}
