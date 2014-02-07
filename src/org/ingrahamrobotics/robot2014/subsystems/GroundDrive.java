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
package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.commands.RunGroundDrive;
import org.ingrahamrobotics.robot2014.log.Output;
import org.ingrahamrobotics.robot2014.log.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class GroundDrive extends Subsystem {

    private final Jaguar leftMotor = new Jaguar(Vst.PWM.LEFT_MOTOR_PORT);
    private final Jaguar rightMotor = new Jaguar(Vst.PWM.RIGHT_MOTOR_PORT);
    private final RobotDrive roboDrive;
    private boolean reversed;

    public GroundDrive() {
        this.roboDrive = new RobotDrive(leftMotor, rightMotor);
        roboDrive.setSafetyEnabled(false);
        roboDrive.stopMotor();
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "GroundDrive:Initialized", true);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new RunGroundDrive());
    }

    public void arcadeDrive(double speed, double turn) {
        if (reversed) {
            speed *= -1;
        }
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Speed", speed);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Turn", turn);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:TankLeft", -1);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:TankRight", -1);
        roboDrive.arcadeDrive(speed, turn);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Left", leftMotor.get());
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Right", rightMotor.get());
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Speed", -1);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Turn", -1);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:TankLeft", leftSpeed);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:TankRight", rightSpeed);
        roboDrive.tankDrive(leftSpeed, rightSpeed);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Left", leftMotor.get());
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Right", rightMotor.get());
    }

    public void setReversed(boolean reversed) {
        Output.output(OutputLevel.MEDIUM, "GroundDrive:Reversed", reversed);
        this.reversed = reversed;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void stop() {
        roboDrive.stopMotor();
    }
}
