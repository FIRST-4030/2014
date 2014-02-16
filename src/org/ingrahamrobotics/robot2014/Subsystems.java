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

import org.ingrahamrobotics.robot2014.commands.ExtendCollectorSolenoids;
import org.ingrahamrobotics.robot2014.commands.ExtendShooterSolenoids;
import org.ingrahamrobotics.robot2014.commands.GroundDriveFastLeft;
import org.ingrahamrobotics.robot2014.commands.GroundDriveFastRight;
import org.ingrahamrobotics.robot2014.commands.GroundDriveToggleShifter;
import org.ingrahamrobotics.robot2014.commands.GroundDriveToggleSoftwareLow;
import org.ingrahamrobotics.robot2014.commands.RetractCollectorSolenoids;
import org.ingrahamrobotics.robot2014.commands.RetractShooterSolenoids;
import org.ingrahamrobotics.robot2014.commands.ReverseGroundDrive;
import org.ingrahamrobotics.robot2014.commands.PullCollectorMotors;
import org.ingrahamrobotics.robot2014.commands.PushCollectorMotors;
import org.ingrahamrobotics.robot2014.input.BMap;
import org.ingrahamrobotics.robot2014.input.JInput;
import org.ingrahamrobotics.robot2014.subsystems.CollectorMotors;
import org.ingrahamrobotics.robot2014.subsystems.CollectorSolenoids;
import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;
import org.ingrahamrobotics.robot2014.subsystems.GroundDriveShifter;
import org.ingrahamrobotics.robot2014.subsystems.PressureCompressor;
import org.ingrahamrobotics.robot2014.subsystems.PressureSwitch;
import org.ingrahamrobotics.robot2014.subsystems.ShooterSolenoids;
import org.ingrahamrobotics.robot2014.subsystems.TurnTable;

public class Subsystems {

    public static Subsystems instance = new Subsystems();
    public final GroundDrive groundDrive;
    public final GroundDriveShifter groundDriveShifter;
    public final TurnTable turnTable;
    public final PressureSwitch pressureSwitch;
    public final PressureCompressor compressor;
    public final ShooterSolenoids shooterSolenoids;
    public final CollectorSolenoids collectorSolenoids;
    public final CollectorMotors collectorMotors;

    public Subsystems() {
        groundDrive = new GroundDrive();
        groundDriveShifter = new GroundDriveShifter();
        turnTable = new TurnTable();
        pressureSwitch = new PressureSwitch();
        compressor = new PressureCompressor();
        shooterSolenoids = new ShooterSolenoids();
        collectorSolenoids = new CollectorSolenoids();
        collectorMotors = new CollectorMotors();
    }

    public void initCommands() {
        JInput.getButton(BMap.reverseGroundDrive1).whenPressed(new ReverseGroundDrive());
        JInput.getButton(BMap.groundDriveFastLeft1).whenPressed(new GroundDriveFastLeft());
        JInput.getButton(BMap.groundDriveFastRight1).whenPressed(new GroundDriveFastRight());
        JInput.getButton(BMap.groundDriveToggleShifter1).whenPressed(new GroundDriveToggleShifter());
        JInput.getButton(BMap.groundDriveToggleSoftwareLow1).whenPressed(new GroundDriveToggleSoftwareLow());

        JInput.getButton(BMap.reverseGroundDrive2).whenPressed(new ReverseGroundDrive());
        JInput.getButton(BMap.groundDriveFastLeft2).whenPressed(new GroundDriveFastLeft());
        JInput.getButton(BMap.groundDriveFastRight2).whenPressed(new GroundDriveFastRight());
        JInput.getButton(BMap.groundDriveToggleShifter2).whenPressed(new GroundDriveToggleShifter());
        JInput.getButton(BMap.groundDriveToggleSoftwareLow2).whenPressed(new GroundDriveToggleSoftwareLow());

        JInput.getButton(BMap.pullCollectorMotors).whenPressed(new PullCollectorMotors());
        JInput.getButton(BMap.pushCollectorMotors).whenPressed(new PushCollectorMotors());
        JInput.getButton(BMap.extendCollectorSolenoids).whenPressed(new ExtendCollectorSolenoids());
        JInput.getButton(BMap.retractCollectorSolenoids).whenPressed(new RetractCollectorSolenoids());
        JInput.getButton(BMap.shooterSolenoidsControl).whenPressed(new ExtendShooterSolenoids());
//        JInput.getButton(BMap.shooterSolenoidsControl).whenReleased(new RetractShooterSolenoids());
    }
}
