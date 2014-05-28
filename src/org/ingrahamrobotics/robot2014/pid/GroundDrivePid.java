package org.ingrahamrobotics.robot2014.pid;

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

    public static final double PID_P = 1.0; // Proportional factor
    public static final double PID_I = 0.0; // Integral factor
    public static final double PID_D = 0.0; // Differential factor
    private long pidLastRun = 0; // Timestamp to determine time since last update
    private double pidLastError = 0.0; // Absolute error in last iteration
    private double pidAccumulatedError = 0.0; // Accumulated error
    private double lastPowerOutput;

    public double calculate(double target, double actual) {
        System.out.println("Target(" + target + ") Actual(" + actual + ");");
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
        double output = (PID_P * error) + (PID_I * pidAccumulatedError) + (PID_D * derivativeError);
        if (output > 1) {
            output = 1;
        } else if (output < -1) {
            output = -1;
        }
        lastPowerOutput = output;
        return output;
    }
}
