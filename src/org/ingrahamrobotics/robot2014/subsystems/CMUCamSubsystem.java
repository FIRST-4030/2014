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

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.visa.VisaException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.ingrahamrobotics.robot2014.cmu.AbstractDebug;
import org.ingrahamrobotics.robot2014.cmu.CMUCamConnection;
import org.ingrahamrobotics.robot2014.log.Output;

public class CMUCamSubsystem extends CMUCamConnection {

    private SerialPort serialPort;

    public CMUCamSubsystem() {
        super(new FirstDebug());
    }

    protected void setBaud(int baud) throws IOException {
        if (serialPort != null) {
            serialPort.free();
        }
        try {
            serialPort = new SerialPort(baud, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
        } catch (VisaException ex) {
            ex.printStackTrace();
            throw new IOException("VisaException: " + ex.getMessage());
        }
        InputStream input = new SerialInputStream(serialPort);
        OutputStream output = new SerialOutputStream(serialPort);
        super.init(input, output);
    }

    protected void close() throws IOException {
        serialPort.free();
    }

    public static class SerialInputStream extends InputStream {

        private final SerialPort serialPort;

        public SerialInputStream(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        public int read() throws IOException {
            try {
                return serialPort.read(1)[0];
            } catch (VisaException ex) {
                ex.printStackTrace();
                throw new IOException("VisaException: " + ex.getMessage());
            }
        }
    }

    public static class SerialOutputStream extends OutputStream {

        private final SerialPort serialPort;

        public SerialOutputStream(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        public void write(int i) throws IOException {
            try {
                serialPort.write(new byte[]{(byte) i}, 1);
            } catch (VisaException ex) {
                ex.printStackTrace();
                throw new IOException("VisaException: " + ex.getMessage());
            }
        }

        public void flush() throws IOException {
            try {
                serialPort.flush();
            } catch (VisaException ex) {
                ex.printStackTrace();
                throw new IOException("VisaException: " + ex.getMessage());
            }
        }

        public void write(byte[] b) throws IOException {
            try {
                serialPort.write(b, b.length);
            } catch (VisaException ex) {
                ex.printStackTrace();
                throw new IOException("VisaException: " + ex.getMessage());
            }
        }
    }

    public static class FirstDebug implements AbstractDebug {

        public void log(String msg) {
            Output.output("SerialDebug", msg, false);
        }
    }
}