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

import edu.wpi.first.wpilibj.command.Command;
import org.ingrahamrobotics.robot2014.Subsystems;

public abstract class StateCommand extends Command {

    protected final Subsystems ss = Subsystems.instance;
    protected long startTime;
    protected long lastSwitch;
    protected int currentState;
    protected final long[] states;

    public StateCommand(long[] states) {
        this.states = states;
    }

    protected void initialize() {
        startTime = lastSwitch = System.currentTimeMillis();
        currentState = 0;
        startState(currentState);
    }

    protected void execute() {
        boolean next = executeState(currentState) || (states[currentState] > 0 && System.currentTimeMillis() > lastSwitch + states[currentState]);
        if (next) {
            lastSwitch = System.currentTimeMillis();
            currentState += 1;
            System.out.println("Switching to state " + currentState);
            startState(currentState);
        }
    }

    protected boolean isFinished() {
        return currentState >= states.length;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    protected abstract boolean executeState(int state);

    protected abstract void startState(int state);
}
