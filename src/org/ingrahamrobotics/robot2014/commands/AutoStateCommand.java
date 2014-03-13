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
package org.ingrahamrobotics.robot2014.commands;

import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.tables.Settings;

public class AutoStateCommand extends StateCommand {

    public AutoStateCommand() {
        Output.output(OutputLevel.RAW_MOTORS, "AutoCommand:StopTime", (long) (Settings.getDouble(Settings.AUTOCOMMAND_STOP_TIME) * 1000));
        long encoderDistance = (long) Settings.getDouble(Settings.AUTOCOMMAND_ENCODER_DISTANCE);
        Output.output(OutputLevel.RAW_MOTORS, "AutoCommand:EncoderDistance", encoderDistance);
        requires(ss.groundDrive);
        requires(ss.groundDriveShifter);
        requires(ss.shooterSolenoids);
        requires(ss.collectorSolenoids);
        requires(ss.turnTable);
        requires(ss.collectorMotors);
    }

    protected boolean executeState(int state) {
        switch (state) {
            case 0:
                ss.groundDrive.setRaw(1, 1);
                long encoderDistance = (long) Settings.getDouble(Settings.AUTOCOMMAND_ENCODER_DISTANCE);
                Output.output(OutputLevel.RAW_MOTORS, "AutoCommand:EncoderDistance", encoderDistance);
                return ss.encoders.getRightEncoder() > encoderDistance || ss.encoders.getLeftEncoder() > encoderDistance
                        || ss.encoders.getRightEncoder() < -encoderDistance || ss.encoders.getLeftEncoder() < -encoderDistance;
            case 1:
                return false;
            case 2:
                return false;
        }
        return true;
    }

    protected void startState(int state) {
        switch (state) {
            case 0:
                ss.encoders.reset();
                ss.groundDriveShifter.setSpeed(true);
                ss.collectorSolenoids.setExtending(true);
                ss.collectorMotors.setBothSpeed(0.75);
                break;
            case 1:
                ss.groundDrive.setRaw(0, 0);
                break;
            case 2:
                ss.shooterSolenoids.setExtending(true);
                break;
            case 3:
                ss.shooterSolenoids.setExtending(false);
                break;
        }
    }

    protected long[] getNextStates() {
        Output.output(OutputLevel.RAW_MOTORS, "AutoCommand:StopTime", (long) (Settings.getDouble(Settings.AUTOCOMMAND_STOP_TIME) * 1000));
        return new long[]{
            (Settings.getBoolean(Settings.AUTOCOMMAND_USE_ENCODERS) ? 0 : 1100), // Drive forward
            (long) (Settings.getDouble(Settings.AUTOCOMMAND_STOP_TIME) * 1000), // Pause
            1500, // Shoot
            500, // Retract
            30
        };
    }
}
