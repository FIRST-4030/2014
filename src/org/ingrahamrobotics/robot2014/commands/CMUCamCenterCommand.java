/*
 * Copyright (C) 2014 Ingraham Robotics Team 4030
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implie d warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ingrahamrobotics.robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.ingrahamrobotics.robot2014.Subsystems;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.tables.Settings;

public class CMUCamCenterCommand extends Command {

    private static final boolean TURN_TABLE = true;
    private static final boolean SMOOTH = true;
    private final Subsystems ss = Subsystems.instance;

    public CMUCamCenterCommand() {
        requires(ss.cmuCam);
        if (TURN_TABLE) {
            requires(ss.turnTable);
            requires(ss.turnTableStops);
        } else {
            requires(ss.groundDrive);
        }
    }

    protected void initialize() {
        if (!ss.cmuCam.isRunning()) {
            ss.cmuCam.startTrackingThread();
        }
    }

    protected void execute() {
        Output.output(OutputLevel.CMU, "CMUcam:DefaultColor", Settings.get(Settings.CMUCAM_DEFAULT_COLOR));
        int[] values = ss.cmuCam.getColorTrackingData();
        if (values != null) {
            if (values[0] == 0) {
                Output.output(OutputLevel.CMU, "CMUcam:Centering", "No objects found");
                return;
            }
            if (values[0] < 0 || values[0] > 160) {
                Output.output(OutputLevel.CMU, "CMUcam:Centering", "X value " + values[0] + " out of range (0 - 160).");
                return;
            }
            int x = values[0] - 80;
            if (SMOOTH) {
                drive(x / 80.0);
            } else if (x < 0) {
                if (x < -60) {
                    drive(-0.8);
                } else if (x < -30) {
                    drive(-0.5);
                } else {
                    drive(-0.2);
                }
            } else if (x > 0) {
                if (x > 60) {
                    drive(0.8);
                } else if (x > 30) {
                    drive(0.5);
                } else {
                    drive(0.2);
                }
            } else {
                drive(0);
            }
            Output.output(OutputLevel.CMU, "CMUcam:Centering", "Object at " + x);
        }
    }

    private void drive(double speed) {
        if (TURN_TABLE) {
            boolean left = ss.turnTableStops.getLeft();
            boolean right = ss.turnTableStops.getRight();
            if (speed < 0 && left) {
                speed = 0;
                if (!ss.slowLeft.isRunning()) {
                    if (ss.slowRight.isRunning()) {
                        ss.slowRight.cancel();
                    }
                    ss.slowLeft.start();
                }
            }
            if (speed > 0 && right) {
                if (!ss.slowRight.isRunning()) {
                    if (ss.slowLeft.isRunning()) {
                        ss.slowLeft.cancel();
                    }
                    ss.slowRight.start();
                }
                speed = 0;
            }
            ss.turnTable.drive(speed);
        } else {
            ss.groundDrive.arcadeDrive(0, speed);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Output.output(OutputLevel.CMU, "CMUcam:Centering", null);
        if (TURN_TABLE) {
            ss.turnTable.drive(0);
        } else {
            ss.groundDrive.stop();
        }
    }

    protected void interrupted() {
        Output.output(OutputLevel.CMU, "CMUcam:Centering", null);
        if (TURN_TABLE) {
            ss.turnTable.drive(0);
        } else {
            ss.groundDrive.setRaw(0, 0);
        }
    }
}
