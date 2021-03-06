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

    public static final BMap reverseGroundDrive1 = new BMap(JMap.DRIVE_JOYSTICK1, JMap.Button.JoystickTop.BOTTOM);
    public static final BMap reverseGroundDrive2 = new BMap(JMap.DRIVE_JOYSTICK2, JMap.Button.JoystickTop.BOTTOM);
    public static final BMap groundDriveFastLeft1 = new BMap(JMap.DRIVE_JOYSTICK1, JMap.Button.JoystickTop.LEFT);
    public static final BMap groundDriveFastLeft2 = new BMap(JMap.DRIVE_JOYSTICK2, JMap.Button.JoystickTop.LEFT);
    public static final BMap groundDriveFastRight1 = new BMap(JMap.DRIVE_JOYSTICK1, JMap.Button.JoystickTop.RIGHT);
    public static final BMap groundDriveFastRight2 = new BMap(JMap.DRIVE_JOYSTICK2, JMap.Button.JoystickTop.RIGHT);
    public static final BMap groundDriveToggleShifter = new BMap(JMap.DRIVE_JOYSTICK1, JMap.Button.JoystickTop.TRIGGER);
    public static final BMap groundDriveToggleSoftwareLow1 = new BMap(JMap.DRIVE_JOYSTICK1, JMap.Button.JoystickTop.MIDDLE);
    public static final BMap groundDriveToggleSoftwareLow2 = new BMap(JMap.DRIVE_JOYSTICK2, JMap.Button.JoystickTop.MIDDLE);
    public static final BMap pullCollectorMotors = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickStand.RIGHT_TOP);
    public static final BMap pushCollectorMotors = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickStand.RIGHT_BOTTOM);
    public static final BMap extendCollectorSolenoids = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickStand.LEFT_TOP);
    public static final BMap retractCollectorSolenoids = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickStand.LEFT_BOTTOM);
    public static final BMap colorTrackingReset = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickStand.BOTTOM_LEFT);
    public static final BMap shooterSolenoidsControl = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickTop.TRIGGER);
    public static final BMap colorTrackingTrigger = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickTop.MIDDLE);
    public static final BMap turntableRangeTrigger = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickTop.BOTTOM);
    public static final BMap disableStops = new BMap(JMap.SHOOTER_JOYSTICK, JMap.Button.JoystickTop.LEFT);
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
