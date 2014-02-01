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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import java.util.Hashtable;

public class JInput {

    private static final Joystick[] JOYSTICKS = new Joystick[4];
    private static final Hashtable[] BUTTONS = new Hashtable[JOYSTICKS.length];

    static {
        for (int i = 0; i < JOYSTICKS.length; i++) {
            JOYSTICKS[i] = new Joystick(i + 1);
            BUTTONS[i] = new Hashtable();
        }
    }

    public static JoystickButton getButton(BMap button) {
        Hashtable map = BUTTONS[button.getJoystick()];
        JoystickButton joystickButton = (JoystickButton) map.get(Integer.valueOf(button.getButton()));
        if (joystickButton == null) {
            joystickButton = new JoystickButton(JOYSTICKS[button.getJoystick()], button.getButton());
            map.put(Integer.valueOf(button.getButton()), joystickButton);
        }
        return joystickButton;
    }

    public static double getAxis(AMap axis) {
        return JOYSTICKS[axis.getJoystick()].getRawAxis(axis.getAxis());
    }
}
