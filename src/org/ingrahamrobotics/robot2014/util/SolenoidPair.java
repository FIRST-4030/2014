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
package org.ingrahamrobotics.robot2014.util;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Represents two solenoids.
 */
public class SolenoidPair {

    private final Solenoid extendingSolenoid;
    private final Solenoid retractingSolenoid;
    private boolean extending;

    public SolenoidPair(int extendingSolenoidPort, int retractingSolenoidPort, boolean extendingFirst) {
        extendingSolenoid = new Solenoid(extendingSolenoidPort);
        retractingSolenoid = new Solenoid(retractingSolenoidPort);
        extendingSolenoid.set(extendingFirst);
        retractingSolenoid.set(!extendingFirst);
        extending = extendingFirst;
    }

    public void setExtending(boolean extending) {
        retractingSolenoid.set(!extending);
        extendingSolenoid.set(extending);
        this.extending = extending;
    }

    public boolean isExtending() {
        return extending;
    }

    public String getState() {
        return extending ? "Extending" : "Retracting";
    }
}
