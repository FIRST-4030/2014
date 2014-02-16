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

import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.log.Output;
import org.ingrahamrobotics.robot2014.log.OutputLevel;
import org.ingrahamrobotics.robot2014.util.SolenoidPair;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class CollectorSolenoids extends Subsystem {

    private final SolenoidPair solenoids = new SolenoidPair(Vst.SOLENOID.COLLECTOR_SOLENOID_EXTEND, Vst.SOLENOID.COLLECTOR_SOLENOID_RETRACT, false);

    public CollectorSolenoids() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "CollectorSolenoids:State", "Initialized");
        setExtending(false);
    }

    protected void initDefaultCommand() {
    }

    public void setExtending(boolean extending) {
        Output.output(OutputLevel.RAW_MOTORS, "Collector:Extending", extending);
        solenoids.setExtending(extending);
    }

    public boolean isExtending() {
        return solenoids.isExtending();
    }
}
