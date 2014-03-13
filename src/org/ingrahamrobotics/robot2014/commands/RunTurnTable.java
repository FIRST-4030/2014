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
import org.ingrahamrobotics.robot2014.input.AMap;
import org.ingrahamrobotics.robot2014.input.JInput;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

public class RunTurnTable extends Command {

    private final Subsystems ss = Subsystems.instance;

    public RunTurnTable() {
        requires(ss.turnTable);
        requires(ss.turnTableStops);
    }

    protected void initialize() {
    }

    protected void execute() {
        double value = JInput.getAxis(AMap.turnTable);

        // Limit movement when we're at the stops
        if (ss.turnTableStops.getLeft() && value < 0) {
            Output.output(OutputLevel.HIGH, "TurnTable:StoppingBecause", "Left");
            value = 0;
        } else if (ss.turnTableStops.getRight() && value > 0) {
            Output.output(OutputLevel.HIGH, "TurnTable:StoppingBecause", "Right");
            value = 0;
        } else {
            Output.output(OutputLevel.HIGH, "TurnTable:StoppingBecause", null);
        }

        // Anti-drift dead zone
        if (value > -0.10 && value < 0.10) {
            ss.turnTable.stop();
        } else {
            ss.turnTable.drive(value);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ss.turnTable.stop();
    }

    protected void interrupted() {
        ss.turnTable.stop();
    }
}
