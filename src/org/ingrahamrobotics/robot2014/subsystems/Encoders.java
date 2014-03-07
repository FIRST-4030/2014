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
import org.ingrahamrobotics.robot2014.commands.DebugOutput;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class Encoders extends Subsystem {

//    private final DigitalInput[] raw = new DigitalInput[]{null, new DigitalInput(2), new DigitalInput(3), new DigitalInput(4), new DigitalInput(5), new DigitalInput(6), new DigitalInput(7)};
    private final DigitalInput[] inputs = new DigitalInput[]{
        new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.LEFT_ENCODER_INPUT2),
        new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT1), new DigitalInput(Vst.DIGITAL_IO.RIGHT_ENCODER_INPUT2)};
    private final Encoder leftEncoder = new Encoder(inputs[0], inputs[1]);
    private final Encoder rightEncoder = new Encoder(inputs[2], inputs[3]);

    public Encoders() {
        leftEncoder.start();
        rightEncoder.start();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DebugOutput());
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

//    public String getDigital() {
//        return " 2=" + raw(1) + " 3=" + raw(2) + " 4=" + raw(3) + " 5=" + raw(4) + " 6=" + raw(5) + " 7=" + raw(6);
//    }
//
//    private int raw(int num) {
//        return raw[num].get() ? 1 : 0;
//    }
}
