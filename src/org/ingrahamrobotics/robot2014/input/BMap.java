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

public class BMap {

    public static final BMap reverseGroundDrive = new BMap(JMap.DRIVE_JOYSTICK, JMap.Button.JoystickTop.BOTTOM);
    public static final BMap groundDriveFastLeft = new BMap(JMap.DRIVE_JOYSTICK, JMap.Button.JoystickTop.LEFT);
    public static final BMap groundDriveFastRight = new BMap(JMap.DRIVE_JOYSTICK, JMap.Button.JoystickTop.RIGHT);
    public static final BMap groundDriveEnableHighSpeed = new BMap(JMap.DRIVE_JOYSTICK, JMap.Button.JoystickStand.BOTTOM_LEFT);
    public static final BMap groundDriveDisableHighSpeed = new BMap(JMap.DRIVE_JOYSTICK, JMap.Button.JoystickStand.BOTTOM_RIGHT);
    private final int joystick;
    private final int button;

    private BMap(int joystick, int button) {
        this.joystick = joystick;
        this.button = button;
    }

    public int getJoystick() {
        return joystick;
    }

    public int getButton() {
        return button;
    }
}
