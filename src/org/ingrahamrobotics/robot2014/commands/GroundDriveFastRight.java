/*
 * Copyright (C) 2013-2014 Ingraham Robotics Team 4030
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

import edu.wpi.first.wpilibj.command.Command;
import org.ingrahamrobotics.robot2014.Subsystems;

public class GroundDriveFastRight extends Command {

    private final Subsystems ss = Subsystems.instance;
    private static final double speedDif = 0.1;
    private static final double reverseSpeedDif = 0.3;
    private static final long timeOn = 125;
    private static final long timeReversed = 1;
    private boolean finished;
    private double speed;
    /**
     * State.
     *
     * 0 for speeding up.
     *
     * 1 for at max speed.
     *
     * 2 for slowing down.
     *
     * 3 is reverse blip.
     */
    private int state;
    /**
     * Time that speed reached max.
     */
    private long maxTimeStamp;
    /**
     * Time that the speed reached 0.
     */
    private long doneTimeStamp;

    public GroundDriveFastRight() {
        requires(ss.groundDrive);
    }

    protected void initialize() {
        state = 0;
        finished = false;
        speed = 0;
    }

    protected void execute() {
        if (state == 0) {
            if (speed + speedDif >= 1) {
                speed = 1;
                maxTimeStamp = System.currentTimeMillis();
                state = 1;
            } else {
                speed += speedDif;
            }
        } else if (state == 1) {
            if (System.currentTimeMillis() - maxTimeStamp > timeOn) {
                state = 2;
            }
        } else if (state == 2) {
            if (speed - reverseSpeedDif <= 0) {
                speed = -speedDif;
                state = 3;
                doneTimeStamp = System.currentTimeMillis();
            } else {
                speed -= reverseSpeedDif;
            }
        } else if (state == 3) {
            if (System.currentTimeMillis() - doneTimeStamp > timeReversed) {
                speed = 0;
                finished = true;
            }
        }
        ss.groundDrive.arcadeDrive(0, speed);
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
