package org.ingrahamrobotics.robot2014.pid;

import org.ingrahamrobotics.robot2014.subsystems.GroundDrive;

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

    private long pidLastRun = 0; // Timestamp to determine time since last update
    private double pidLastError = 0.0; // Absolute error in last iteration
    private double pidAccumulatedError = 0.0; // Accumulated error
    private double lastEncoder;
    private double lastPowerOutput;

    public double calculateSpeed(int targetSpeed, int encoderValue) {
        double actualChange = lastEncoder - encoderValue;
        lastEncoder = encoderValue;
        double targetChange = targetSpeed;
        return pidCalculate(targetChange, actualChange);
    }

    private double pidCalculate(double target, double actual) {
        long now = System.currentTimeMillis();
        long deltaT = now - pidLastRun;

        if (deltaT < 5) {
            // not enough time passed
            return lastPowerOutput;
        }

        double error = target - actual;
        pidAccumulatedError += (error * deltaT);
        double derivativeError = (error - pidLastError) / deltaT;

        pidLastError = error;
        pidLastRun = now;
        double output = (GroundDrive.PID_P * error) + (GroundDrive.PID_I * pidAccumulatedError) + (GroundDrive.PID_D * derivativeError);
        if (output > 100) {
            output = 100;
        } else if (output < -100) {
            output = -100;
        }
        lastPowerOutput = output;
        return output;
    }
}
