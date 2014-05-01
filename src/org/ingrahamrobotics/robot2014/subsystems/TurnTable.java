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

        public final static Speed LOW = new Speed(0.25, "Low");
        public final static Speed HIGH = new Speed(1.0, "High");
        private final double multiplier;
        private final String representation;

        private Speed(double multiplier, String representation) {
            this.multiplier = multiplier;
            this.representation = representation;
        }

        public double getMultiplier() {
            return multiplier;
        }

        public String toString() {
            return representation;
        }
    }
    private Speed range = Speed.LOW;
    private final Jaguar firstMotor = new Jaguar(Vst.PWM.TURN_TABLE_1_PORT);
    private final Jaguar secondMotor = new Jaguar(Vst.PWM.TURN_TABLE_2_PORT);

    public TurnTable() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "TurnTable:State", "Initialized");
        stop();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new RunTurnTable());
    }

    public Speed getRange() {
        return this.range;
    }

    public void setRange(Speed range) {
        Output.output(OutputLevel.RAW_MOTORS, "TurnTable:Range", range.toString());
        this.range = range;
    }

    public void drive(double speed) {
        if (speed == 0) {
            stop();
        } else {
            speed *= this.range.getMultiplier();
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
