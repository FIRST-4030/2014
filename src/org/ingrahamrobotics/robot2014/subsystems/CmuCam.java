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
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.visa.VisaException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.ingrahamrobotics.robot2014.cmu.AbstractDebug;
import org.ingrahamrobotics.robot2014.cmu.CMUCamConnection;
import org.ingrahamrobotics.robot2014.cmu.CMUColorTracking;
import org.ingrahamrobotics.robot2014.cmu.api.CMUColorTrackingListener;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;

public class CmuCam extends Subsystem {

    private final CrioCmuCam cam;
    private final CMUColorTracking tracking;
    private int[] lastColorTrackingData;

    /**
     * Values is an array of values:
     *
     * [0]: Average X [1]: Average Y [2]: Left X [3]: Top Y [4]: Right X [5]:
     * Bottom Y
     *
     * @return values
     */
    public int[] getColorTrackingData() {
        return lastColorTrackingData;
    }

    public CmuCam() {
        cam = new CrioCmuCam();
        tracking = new CMUColorTracking(10);

        tracking.registerListener(new TrackingListener());
    }

    public void startTrackingThread() {
        new TrackingThread().start();
    }

    protected void initDefaultCommand() {
    }

    private class TrackingListener implements CMUColorTrackingListener {

        public void onNewColorTrackingData(int[] values) {
            lastColorTrackingData = values;
        }
    }

    private class TrackingThread extends Thread {

        public TrackingThread() {
            super("CMUCam tracking thread");
        }

        public void run() {
            try {
                cam.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            while (true) {
                try {
                    cam.runCommandSet(tracking);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static class CrioCmuCam extends CMUCamConnection {

        private SerialPort serialPort;

        public CrioCmuCam() {
            super(new FirstDebug());
        }

        protected void setBaud(int baud) throws IOException {
            if (serialPort != null) {
                serialPort.free();
            }
            try {
                serialPort = new SerialPort(19200, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
                serialPort.setFlowControl(SerialPort.FlowControl.kNone);
                serialPort.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess);
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
                Output.output(OutputLevel.RAW_SENSORS, "SerialDebug", msg);
            }
        }
    }
}
