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
import org.ingrahamrobotics.robot2014.commands.RunTurnTable;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class TurnTable extends Subsystem {

    public final static class Speed {

        public final static int LOW = 0;
        public final static int HIGH = 1;
    }

    private int range = Speed.LOW;
    private final Jaguar firstMotor = new Jaguar(Vst.PWM.TURN_TABLE_1_PORT);
    private final Jaguar secondMotor = new Jaguar(Vst.PWM.TURN_TABLE_2_PORT);

    public TurnTable() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "TurnTable:State", "Initialized");
        stop();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new RunTurnTable());
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void drive(double speed) {
        if (speed == 0) {
            Output.output(OutputLevel.RAW_MOTORS, "TurnTable:Speed", "Stopped");
            firstMotor.stopMotor();
            secondMotor.stopMotor();
        } else {
            if (this.range == Speed.LOW) {
                speed *= 0.50;
            }
            Output.output(OutputLevel.RAW_MOTORS, "TurnTable:Range", this.range == Speed.LOW ? "Low" : "High");
            Output.output(OutputLevel.RAW_MOTORS, "TurnTable:Speed", speed);
            firstMotor.set(speed);
            secondMotor.set(speed);
        }
    }

    public void stop() {
        Output.output(OutputLevel.RAW_MOTORS, "TurnTable:Speed", "Stopped");
        firstMotor.stopMotor();
        secondMotor.stopMotor();
    }
}
