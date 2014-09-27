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
import org.ingrahamrobotics.robot2014.Subsystems;
import org.ingrahamrobotics.robot2014.commands.RunGroundDrive;
import org.ingrahamrobotics.robot2014.pid.GroundDrivePid;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class GroundDrive extends Subsystem {

    /**
     * Rough power to encoder per millisecond.
     */
    public static final double ROUGH_TRANSLATION = -2000;
    public static final double PID_P = 0.5; // Proportional factor
    public static final double PID_I = 0.5; // Integral factor
    public static final double PID_D = 0.0; // Differential factor
    private final Jaguar leftMotor = new Jaguar(Vst.PWM.LEFT_MOTOR_PORT);
    private final Jaguar rightMotor = new Jaguar(Vst.PWM.RIGHT_MOTOR_PORT);
    private final RobotDrive roboDrive;
    private boolean softwareLowSpeed;
    private boolean reversed;
    private final GroundDrivePid leftPid = new GroundDrivePid();
    private final GroundDrivePid rightPid = new GroundDrivePid();

    public GroundDrive() {
        this.roboDrive = new RobotDrive(leftMotor, rightMotor);
        roboDrive.setSafetyEnabled(false);
        stop();
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "GroundDrive:State", "Initialized");
        setReversed(false);
        setSoftwareLowSpeed(false);
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

    public void pidTankDrive(double leftSpeed, double rightSpeed) {
        if (reversed) {
            double temp = leftSpeed;
            leftSpeed = -1 * rightSpeed;
            rightSpeed = -1 * temp;
        }
        if (softwareLowSpeed) {
            leftSpeed *= 0.5;
            rightSpeed *= 0.5;
        }
        int leftEncoder = Subsystems.instance.encoders.getLeftEncoder();
//        int rightEncoder = Subsystems.instance.encoders.getRightEncoder();
        double leftPower = leftPid.calculatePower(leftSpeed, leftEncoder);
//        double rightPower = rightPid.calculatePower(rightSpeed, rightEncoder);
        double rightPower = -leftPower;
        powerTankDriveRaw(leftPower, rightPower);
    }

    /**
     * Sets the reversed setting. When reversed, the left/right speeds are
     * switched, and multiplied by -1.
     *
     * @param reversed The new value for the reversed setting.
     */
    public void setReversed(boolean reversed) {
        Output.output(OutputLevel.HIGH, "GroundDrive:Reversed", reversed);
        this.reversed = reversed;
    }

    /**
     * Returns the reversed setting value. When reversed, the left/right speeds
     * are switched, and multiplied by -1.
     *
     * @return The current reversed setting.
     */
    public boolean isReversed() {
        return reversed;
    }

    public void setSoftwareLowSpeed(boolean softwareLowSpeed) {
        Output.output(OutputLevel.HIGH, "GroundDrive:Software Speed", softwareLowSpeed ? "Low" : "High");
        this.softwareLowSpeed = softwareLowSpeed;
    }

    public boolean isSoftwareLowSpeed() {
        return softwareLowSpeed;
    }

    public void stop() {
        roboDrive.stopMotor();
    }

    /**
     * Power-based tank drive.
     *
     * The left/right values specified are altered by the "reversed" and
     * "softwareLowSpeed" settings.
     *
     * @param leftSpeed Speed for the left motor
     * @param rightSpeed Speed for the right motor
     */
    public void powerTankDrive(double leftSpeed, double rightSpeed) {
        if (reversed) {
            double temp = leftSpeed;
            leftSpeed = -1 * rightSpeed;
            rightSpeed = -1 * temp;
        }

        if (softwareLowSpeed) {
            leftSpeed *= 0.5;
            rightSpeed *= 0.5;
        }
        powerTankDriveRaw(leftSpeed, rightSpeed);
    }

    /**
     * Raw power-based tank drive.
     *
     * "Raw" means that the leftSpeed/rightSpeed aren't altered by the
     * "reversed" and "softwareLowSpeed" settings.
     *
     * "power-based" means that this sets the raw power of the motors, rather
     * than using a PID loop.
     *
     * @param leftSpeed Raw speed for the left motor
     * @param rightSpeed Raw speed for the right motor
     */
    public void powerTankDriveRaw(double leftSpeed, double rightSpeed) {
        if (leftSpeed == 0 && rightSpeed == 0) {
            stop();
        } else {
            roboDrive.tankDrive(leftSpeed, rightSpeed);
        }
//        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Left", leftSpeed);
//        Output.output(OutputLevel.RAW_MOTORS, "GroundDrive:Right", rightSpeed);
    }
}
