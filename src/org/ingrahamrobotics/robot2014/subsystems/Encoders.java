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

    private Encoder encoder1 = new Encoder(new DigitalInput(2), new DigitalInput(3));
    private Encoder encoder2 = new Encoder(new DigitalInput(4), new DigitalInput(5));

    protected void initDefaultCommand() {
        setDefaultCommand(new EncoderRead());
    }

    public int getEncoder1() {
        return encoder1.get();
    }

    public int getEncoder2() {
        return encoder2.get();
    }

    public int getRawEncoder1() {
        return encoder1.getRaw();
    }

    public int getRawEncoder2() {
        return encoder2.getRaw();
    }
}
