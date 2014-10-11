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
package org.ingrahamrobotics.robot2014.pid;

import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

public class GroundDrivePid {

    private double pidLastError = 0.0; // Absolute error in last iteration
    private double pidAccumulatedError = 0.0; // Accumulated error
    private long lastEncoderTime; // Timestamp to determine time since last update
    private double lastEncoder;
    private double currentSpeedVar;
    private final String outputName;

    public GroundDrivePid(String name) {
        this.outputName = name;
    }

    public double calculatePower(double targetSpeed, int encoderValue) {
        double speed = calculateSpeed(targetSpeed * GroundDrive.ROUGH_TRANSLATION, encoderValue);
        double resultInPower = speed / GroundDrive.ROUGH_TRANSLATION;
        if (resultInPower > 1) {
            resultInPower = 1;
        } else if (resultInPower < -1) {
            resultInPower = -1;
        }
        return resultInPower;
    }

    /**
     * @param targetSpeed Target speed, in encoder change per millisecond
     * @param encoderValue Current encoder value.
     * @return
     */
    public double calculateSpeed(double targetSpeed, int encoderValue) {
        long now = System.currentTimeMillis();
        long timePassed = now - lastEncoderTime;
        if (timePassed < 15) {
            // not enough time passed
            return currentSpeedVar;
        }
        lastEncoderTime = now;

        double actualChange = ((double) (lastEncoder - encoderValue)) / timePassed; // this is now in encoder difference per millisecond rather than encoder difference

        Output.output(OutputLevel.RAW_MOTORS, "GroundDrivePid:Difference:" + outputName, "Target:" + targetSpeed + " Actual:" + actualChange + " Current:" + currentSpeedVar);
        lastEncoder = encoderValue;

        return pidCalculate(timePassed, targetSpeed, actualChange);
    }

    private double pidCalculate(long deltaT, double target, double actual) {
        double error = target - actual;
        pidAccumulatedError += (error * deltaT);
        double derivativeError = (error - pidLastError) / deltaT;
        pidLastError = error;

        double change = (GroundDrive.PID_P * error) + (GroundDrive.PID_I * pidAccumulatedError) + (GroundDrive.PID_D * derivativeError);

        currentSpeedVar += change;
        return currentSpeedVar;
    }
}
