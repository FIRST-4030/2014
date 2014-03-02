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
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

public class ExtendShooterSolenoids extends Command {

    private static final int SHOOT_TIME = 2000;
    private final Subsystems ss = Subsystems.instance;
    private long startTime;
    private boolean canceled;

    public ExtendShooterSolenoids() {
        requires(ss.shooterSolenoids);
    }

    protected void initialize() {
        System.out.println("Starting shoot");
        startTime = System.currentTimeMillis();
        if (!ss.collectorSolenoids.isExtending()) {
            Output.output(OutputLevel.HIGH, "ShooterError", "Collector solenoids retracted.");
            System.out.println("Error: Collector retracted.");
            canceled = true;
        } else {
            Output.output(OutputLevel.HIGH, "ShooterError", null);
            ss.shooterSolenoids.setExtending(true);
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() > startTime + SHOOT_TIME || canceled;
    }

    protected void end() {
        ss.shooterSolenoids.setExtending(false);
    }

    protected void interrupted() {
        ss.shooterSolenoids.setExtending(false);
    }
}
