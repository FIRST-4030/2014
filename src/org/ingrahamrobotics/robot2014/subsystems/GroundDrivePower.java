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

import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.commands.ReadGroundDrivePower;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

public class GroundDrivePower extends Subsystem {

    private int powerIndex = 0;
    private static final int NUM_SAMPLES = 100;
    private final double[][] power = new double[2][NUM_SAMPLES];

    public GroundDrivePower() {
        reset();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadGroundDrivePower());
    }

    public void reset() {
        for (int i = 0; i < NUM_SAMPLES; i++) {
            power[GroundDrive.LEFT][i] = 0.0;
            power[GroundDrive.RIGHT][i] = 0.0;
        }
    }

    public void appendPower(double left, double right) {
        if (left < 0) {
            left *= -1.0;
        }
        if (right < 0) {
            right *= -1.0;
        }
        power[GroundDrive.LEFT][powerIndex] = left;
        power[GroundDrive.RIGHT][powerIndex] = right;
        powerIndex = (powerIndex + 1) % NUM_SAMPLES;

        Output.output(OutputLevel.RAW_MOTORS, "GroundDrivePower:AverageLeft", averagePower(GroundDrive.LEFT));
        Output.output(OutputLevel.RAW_MOTORS, "GroundDrivePower:AverageRight", averagePower(GroundDrive.RIGHT));
    }

    public double accumulatedPower(int side) {
        double sum = 0.0;
        for (int i = 0; i < NUM_SAMPLES; i++) {
            sum += power[side][i];
        }
        return sum;
    }

    public double averagePower(int side) {
        return accumulatedPower(side) / NUM_SAMPLES;
    }
}
