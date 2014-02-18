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

public class Encoders extends Subsystem {

    private DigitalInput[] inputs = new DigitalInput[]{new DigitalInput(3), new DigitalInput(5), new DigitalInput(2), new DigitalInput(4)};
    private Encoder leftEncoder = new Encoder(inputs[0], inputs[1]); //2,3 3,5
    private Encoder rightEncoder = new Encoder(inputs[2], inputs[3]); // 0,1 2,4

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

    public int getEncoder1() {
        return rightEncoder.get();
    }

    public int getEncoder2() {
        return leftEncoder.get();
    }

    public String getDigital() {
        return "rawLeft[" + io(0) + ", " + io(1) + "] rawRight[" + io(2) + ", " + io(3) + "]";
    }

    private int io(int num) {
        return inputs[num].get() ? 1 : 0;
    }
}
