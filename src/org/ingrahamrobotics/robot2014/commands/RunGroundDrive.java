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
package org.ingrahamrobotics.robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables2.util.List;
import java.util.Enumeration;
import java.util.Hashtable;
import org.ingrahamrobotics.robot2014.Subsystems;
import org.ingrahamrobotics.robot2014.input.AMap;
import org.ingrahamrobotics.robot2014.input.JInput;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;

public class RunGroundDrive extends Command {

    private final Subsystems ss = Subsystems.instance;
    private long firstTime = -1;
    private long lastTime;
    private int lastEncoder;
    private Hashtable map = new Hashtable();

    public RunGroundDrive() {
        requires(ss.groundDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
//        ss.groundDrive.arcadeDrive(JInput.getAxis(AMap.arcadeDriveY), JInput.getAxis(AMap.arcadeDriveX));
//        ss.groundDrive.pidTankDrive(JInput.getAxis(AMap.tankDriveLeft), JInput.getAxis(AMap.tankDriveRight));

        if (firstTime == -1) {
            firstTime = System.currentTimeMillis();
        }
        long now = System.currentTimeMillis();

        double speedToTest = -20;
        for (int i = 1; i <= 10; i++) {
            if (now <= firstTime + 10000 * i) {
                speedToTest = 0.1 * i;
                break;
            }
        }
        if (speedToTest == -20) {
            for (int i = 1; i <= 10; i++) {
                if (now <= firstTime + 10000 * (10 + i)) {
                    speedToTest = -0.1 * i;
                    break;
                }
            }
        }
        if (speedToTest == -20) {
            firstTime = now;
            Enumeration keys = map.keys();
            for (Double key = (Double) keys.nextElement(); keys.hasMoreElements(); key = (Double) keys.nextElement()) {
                double sum = 0;
                List results = (List) map.get(key);
                for (int i = 0; i < results.size(); i++) {
                    sum += ((Double) results.get(i)).doubleValue();
                }
                double average = sum / results.size();
                Output.output(OutputLevel.HIGH, "Movement-" + key, average);
            }
            map.clear();
            speedToTest = 0.1;
        }

        Object key = Double.valueOf(speedToTest);
        List results = (List) map.get(key);
        if (results == null) {
            results = new List();
            map.put(key, results);
        }

        long timeDiff = now - lastTime;
        if (timeDiff > 1500) {
            lastTime = now;
            int encoder = ss.encoders.getLeftEncoder();
            int encoderDiff = encoder - lastEncoder;
            lastEncoder = encoder;
            double result = ((double) encoderDiff) / timeDiff * 1000;
            Output.output(OutputLevel.RAW_MOTORS, "Movement-" + now, speedToTest + " = " + result);
            results.add(Double.valueOf(result));
        }
        ss.groundDrive.powerTankDriveRaw(speedToTest, -speedToTest);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ss.groundDrive.stop();
    }

    protected void interrupted() {
        ss.groundDrive.stop();
    }
}
