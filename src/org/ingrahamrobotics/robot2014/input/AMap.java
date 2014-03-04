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
package org.ingrahamrobotics.robot2014.input;

public class AMap {

    public static final AMap arcadeDriveX = new AMap(JMap.DRIVE_JOYSTICK1, JMap.Axis.X);
    public static final AMap arcadeDriveY = new AMap(JMap.DRIVE_JOYSTICK1, JMap.Axis.Y);
    public static final AMap tankDriveLeft = new AMap(JMap.DRIVE_JOYSTICK1, JMap.Axis.Y);
    public static final AMap tankDriveRight = new AMap(JMap.DRIVE_JOYSTICK2, JMap.Axis.Y);
    public static final AMap turnTable = new AMap(JMap.SHOOTER_JOYSTICK, JMap.Axis.X);
    private final int joystick;
    private final int axis;
    // XBox
//    public static final AMap tankDriveLeft = new AMap(0, 2);
//    public static final AMap tankDriveRight = new AMap(0, 4);

    public AMap(int joystick, int axis) {
        this.joystick = joystick;
        this.axis = axis;
    }

    public int getJoystick() {
        return joystick;
    }

    public int getAxis() {
        return axis;
    }
}
