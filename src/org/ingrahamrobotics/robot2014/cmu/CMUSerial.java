/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ingrahamrobotics.robot2014.cmu;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.visa.VisaException;

/**
 * Wrap the FRC SerialPort class to magically know the CMUCam4 parameters and
 * provide String-compatible methods
 *
 * @author profplump
 */
public class CMUSerial {

    private SerialPort port = null;
    private boolean open = false;
    private double timeout = 0.40;
    private char terminator = '\r';

    public CMUSerial() {
        try {
            // Baked in defaults for the CMUCam4
            port = new SerialPort(19200, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
            port.setFlowControl(SerialPort.FlowControl.kNone);
            port.setWriteBufferMode(SerialPort.WriteBufferMode.kFlushOnAccess);

            // Tunable port settings
            port.setTimeout(timeout);
            port.enableTermination(terminator);

            // State tracking
            open = true;
        } catch (VisaException ex) {
            System.err.println("Unable to open serial port: " + ex.toString());
        }
    }

    public void close() {
        if (port != null) {
            port.free();
        }
        open = false;
    }

    public double getTimeout() {
        return timeout;
    }

    public char getTerminator() {
        return terminator;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
        if (open) {
            this.setTimeout(this.timeout);
        }
    }

    public void setTerminator(char terminator) {
        this.terminator = terminator;
        if (open) {
            this.setTerminator(this.terminator);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public String read() {
        String retval = "";
        if (open) {
            try {
                retval = port.readString();
            } catch (VisaException ex) {
                System.err.println("Unable to read from serial port: " + ex.toString());
            }
        }
        return retval;
    }

    public int write(String str) {
        int count = 0;
        if (open) {
            try {
                count = port.write(str.getBytes(), str.length());
            } catch (VisaException ex) {
                System.err.println("Unable to write to serial port: " + ex.toString());
            }
        }
        return count;
    }
}
