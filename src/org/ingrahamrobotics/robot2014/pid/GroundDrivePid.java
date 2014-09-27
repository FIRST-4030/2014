package org.ingrahamrobotics.robot2014.pid;

import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

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
public class GroundDrivePid {

    private double pidLastError = 0.0; // Absolute error in last iteration
    private double pidAccumulatedError = 0.0; // Accumulated error
    private long lastEncoderTime; // Timestamp to determine time since last update
    private double lastEncoder;
    private double lastPowerOutput;

    public double calculateSpeed(double targetSpeed, int encoderValue) {
        long now = System.currentTimeMillis();
        long timePassed = now - lastEncoderTime;
        if (timePassed < 5) {
            // not enough time passed
            return lastPowerOutput;
        }
        lastEncoderTime = now;

        double targetChange = targetSpeed * GroundDrive.ROUGH_POWER_TO_ENCODER_PER_MILLISECOND; // this is now in encoder difference per second rather than power
        double actualChange = ((double) (lastEncoder - encoderValue)) * timePassed; // this is now in encoder difference per second rather than encoder difference
        lastEncoder = encoderValue;

        Output.output(OutputLevel.RAW_MOTORS, "GroundDrivePid:Difference", "Target Speed:" + targetSpeed + "Modifier:" + GroundDrive.ROUGH_POWER_TO_ENCODER_PER_MILLISECOND
                + " Target:" + targetChange + " Actual:" + actualChange);
        return pidCalculate(timePassed, targetChange, actualChange) / GroundDrive.ROUGH_POWER_TO_ENCODER_PER_MILLISECOND;
    }

    private double pidCalculate(long deltaT, double target, double actual) {
        double error = target - actual;
        pidAccumulatedError += (error * deltaT);
        double derivativeError = (error - pidLastError) / deltaT;
        pidLastError = error;

        double output = (GroundDrive.PID_P * error) + (GroundDrive.PID_I * pidAccumulatedError) + (GroundDrive.PID_D * derivativeError);
        double max = GroundDrive.ROUGH_POWER_TO_ENCODER_PER_MILLISECOND;
        if (max < 0) {
            max *= -1;
        }
        if (output > max) {
            output = max;
        } else if (output < -max) {
            output = -max;
        }
        lastPowerOutput = output;
        return output;
    }
}
