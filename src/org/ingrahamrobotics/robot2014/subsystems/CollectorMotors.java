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

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class CollectorMotors extends Subsystem {

    private final Jaguar topMotor = new Jaguar(Vst.PWM.COLLECTOR_TOP_MOTOR);
    private final Jaguar sideMotors = new Jaguar(Vst.PWM.COLLECTOR_SIDE_MOTORS);

    public CollectorMotors() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "ShooterMotors:State", "Initialized");
        setBothSpeed(0);
    }

    protected void initDefaultCommand() {
    }

    public void setBothSpeed(double speed) {
        topMotor.set(speed);
        sideMotors.set(speed);
        Output.output(OutputLevel.RAW_MOTORS, "ShooterMotors:TopSpeed", speed);
        Output.output(OutputLevel.RAW_MOTORS, "ShooterMotors:SideSpeed", speed);
    }

    public double getBothSpeed() {
        return (topMotor.get() + sideMotors.get()) / 2;
    }

    public void getTopSpeed(double speed) {
        topMotor.get();
    }
}
