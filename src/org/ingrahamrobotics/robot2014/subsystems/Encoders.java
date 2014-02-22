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
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class Encoders extends Subsystem {

    private DigitalInput[] inputs = new DigitalInput[]{
        new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT2),
        new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT2)};
    private Encoder leftEncoder = new Encoder(inputs[0], inputs[1]); // 3,5
    private Encoder rightEncoder = new Encoder(inputs[2], inputs[3]); // 2,4

    public Encoders() {
        leftEncoder.start();
        rightEncoder.start();
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

    public String getDigital() {
        return "rawLeft[" + raw(0) + ", " + raw(1) + "] rawRight[" + raw(2) + ", " + raw(3) + "]";
    }

    private int raw(int num) {
        return inputs[num].get() ? 1 : 0;
    }
}
