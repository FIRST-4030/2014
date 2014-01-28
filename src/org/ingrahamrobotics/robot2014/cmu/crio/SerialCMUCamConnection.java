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
package org.ingrahamrobotics.robot2014.cmu.crio;

import java.io.IOException;
import org.ingrahamrobotics.robot2014.cmu.AbstractDebug;
import org.ingrahamrobotics.robot2014.cmu.CMUCamConnection;
import org.ingrahamrobotics.robot2014.log.Output;

public class SerialCMUCamConnection extends CMUCamConnection {

    public SerialCMUCamConnection() {
        super(new FirstDebug());
    }

    protected void setBaud(int baud) throws IOException {
    }

    protected void close() throws IOException {
    }

    public static class FirstDebug implements AbstractDebug {

        public void log(String msg) {
            Output.output("SerialDebug", msg, false);
        }
    }
}
