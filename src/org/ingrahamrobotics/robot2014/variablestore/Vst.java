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
package org.ingrahamrobotics.robot2014.variablestore;

/**
 * This is the main variable store, holding all static cable-related values.
 */
public final class Vst {

    /**
     * PWM Statics
     */
    public static final class PWM {

        public static final int LEFT_MOTOR_PORT = 1;
        public static final int RIGHT_MOTOR_PORT = 2;
        public static final int TURN_TABLE_1_PORT = 3;
        public static final int TURN_TABLE_2_PORT = 4;
        public static final int COLLECTOR_SIDE_MOTORS = 5;
        public static final int COLLECTOR_TOP_MOTOR = 6;
    }

    public static final class SOLENOID {

        public static final int GROUND_DRIVE_SHIFTER_EXTEND = 1;
        public static final int GROUND_DRIVE_SHIFTER_RETRACT = 2;
        public static final int COLLECTOR_SOLENOID_EXTEND = 3;
        public static final int COLLECTOR_SOLENOID_RETRACT = 4;
        public static final int SHOOTER_EXTEND = 8;
        public static final int SHOOTER_RETRACT = 6;
    }

    /**
     * Digital IO Statics
     */
    public static final class DIGITAL_IO {

        /**
         * Port for the pressure switch
         */
        public static final int PRESSURE_SWITCH = 1;
        public static final int LEFT_ENCODER_INPUT1 = 3;
        public static final int LEFT_ENCODER_INPUT2 = 5;
        public static final int RIGHT_ENCODER_INPUT1 = 2;
        public static final int RIGHT_ENCODER_INPUT2 = 4;
    }

    /**
     * Relay Statics
     */
    public static final class RELAY {

        /**
         * Port for the compressor spike
         */
        public static final int COMPRESSOR_SPIKE = 1;
    }
}
