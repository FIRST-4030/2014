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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.commands.EncoderRead;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class Encoders extends Subsystem {

    private final DigitalInput[] inputs = new DigitalInput[]{
        new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT2),
        new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT2)};
    private final Encoder leftEncoder = new Encoder(inputs[0], inputs[1]);
    private final Encoder rightEncoder = new Encoder(inputs[2], inputs[3]);

    private int displacementIndex = 0;
    private static final int NUM_SAMPLES = 100;
    private final int[] lastReading = new int[2];
    private final int[][] displacement = new int[2][NUM_SAMPLES];

    public Encoders() {
        leftEncoder.start();
        rightEncoder.start();
        resetAccumulator();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new EncoderRead());
    }

    public void reset() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public int getRightEncoder() {
        return rightEncoder.get();
    }

    public int getLeftEncoder() {
        return leftEncoder.get();
    }

    public void resetAccumulator() {
        for (int i = 0; i < NUM_SAMPLES; i++) {
            displacement[GroundDrive.LEFT][i] = 0;
            displacement[GroundDrive.RIGHT][i] = 0;
        }
    }

    public void appendReading(int left, int right) {
        appendDistance(left - lastReading[GroundDrive.LEFT], right - lastReading[GroundDrive.RIGHT]);
        lastReading[GroundDrive.LEFT] = left;
        lastReading[GroundDrive.RIGHT] = right;
    }

    public void appendDistance(int left, int right) {
        if (left < 0) {
            left *= -1;
        }
        if (right < 0) {
            right *= -1;
        }
        displacement[GroundDrive.LEFT][displacementIndex] = left;
        displacement[GroundDrive.RIGHT][displacementIndex] = right;
        displacementIndex = (displacementIndex + 1) % NUM_SAMPLES;

        Output.output(OutputLevel.RAW_SENSORS, "Encoder:AverageLeft", averageDisplacement(GroundDrive.LEFT));
        Output.output(OutputLevel.RAW_SENSORS, "Encoder:AverageRight", averageDisplacement(GroundDrive.RIGHT));
    }

    public double accumulatedDisplacement(int side) {
        double sum = 0.0;
        for (int i = 0; i < NUM_SAMPLES; i++) {
            sum += displacement[side][i];
        }
        return sum;
    }

    public double averageDisplacement(int side) {
        return accumulatedDisplacement(side) / NUM_SAMPLES;
    }
}
