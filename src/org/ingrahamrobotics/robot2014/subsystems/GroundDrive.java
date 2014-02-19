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
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class GroundDrive extends Subsystem {

    private final Jaguar leftMotor = new Jaguar(Vst.PWM.LEFT_MOTOR_PORT);
    private final Jaguar rightMotor = new Jaguar(Vst.PWM.RIGHT_MOTOR_PORT);
    private final RobotDrive roboDrive;
    private boolean softwareLowSpeed;
    private boolean reversed;

    public GroundDrive() {
        this.roboDrive = new RobotDrive(leftMotor, rightMotor);
        roboDrive.setSafetyEnabled(false);
        roboDrive.stopMotor();
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "GroundDrive:State", "Initialized");
        setReversed(false);
        setSoftwareLowSpeed(false);
        arcadeDrive(0, 0);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new RunGroundDrive());
    }

    public void arcadeDrive(double speed, double turn) {
        if (reversed) {
            speed *= -1;
        }
        if (softwareLowSpeed) {
            speed *= 0.5;
        }
        roboDrive.arcadeDrive(speed, turn);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Left", leftMotor.get());
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Right", rightMotor.get());
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        if (reversed) {
            double temp = leftSpeed;
            leftSpeed = -1 * rightSpeed;
            rightSpeed = -1 * temp;
        }

        if (softwareLowSpeed) {
            leftSpeed *= 0.5;
            rightSpeed *= 0.5;
        }
        roboDrive.tankDrive(leftSpeed, rightSpeed);
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Left", leftMotor.get());
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Right", rightMotor.get());
    }

    public void setRaw(double leftSpeed, double rightSpeed) {
        roboDrive.tankDrive(leftSpeed, rightSpeed);
        if (leftSpeed == 0 && rightSpeed == 0) {
            roboDrive.stopMotor();
            leftMotor.stopMotor();
            rightMotor.stopMotor();
        }
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

    public void setSoftwareLowSpeed(boolean softwareLowSpeed) {
        Output.output(OutputLevel.MEDIUM, "GroundDrive:SoftwareLow", softwareLowSpeed);
        this.softwareLowSpeed = softwareLowSpeed;
    }

    public boolean isSoftwareLowSpeed() {
        return softwareLowSpeed;
    }

    public void stop() {
        roboDrive.stopMotor();
    }
}
