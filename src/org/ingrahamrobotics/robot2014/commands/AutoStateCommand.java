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

public class AutoStateCommand extends StateCommand {

    public AutoStateCommand() {
        super(new long[]{1100, 1000, 1500, 500});
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
                return false;
//                return ss.encoders.getRightEncoder() > 25000 || ss.encoders.getLeftEncoder() > 25000
//                        || ss.encoders.getRightEncoder() < -25000 || ss.encoders.getLeftEncoder() < -25000;
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
                this.cancel();
                ss.shooterSolenoids.setExtending(true);
                break;
            case 3:
                ss.shooterSolenoids.setExtending(false);
                break;
        }
    }
}
