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

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

public class CollectorMotors extends Subsystem {

    private final Jaguar topMotor = new Jaguar(Vst.PWM.COLLECTOR_TOP_MOTOR);
    private final Jaguar sideMotors = new Jaguar(Vst.PWM.COLLECTOR_SIDE_MOTORS);
    private boolean topMotorStopped;
    private boolean sideMotorsStopped;

    public CollectorMotors() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "CollectorMotors:State", "Initialized");
        stopBoth();
    }

    protected void initDefaultCommand() {
    }

    public void stopBoth() {
        stopTop();
        stopSide();
    }

    public void setBothSpeed(double speed) {
        setTopSpeed(speed);
        setSideSpeed(speed);
    }

    public void stopTop() {
        topMotorStopped = true;
        topMotor.stopMotor();
        Output.output(OutputLevel.RAW_MOTORS, "Collector:Top", "Stopped");
    }

    public void stopSide() {
        sideMotorsStopped = true;
        sideMotors.stopMotor();
        Output.output(OutputLevel.RAW_MOTORS, "Collector:Side", "Stopped");
    }

    public void setTopSpeed(double speed) {
        if (speed == 0) {
            stopTop();
        } else {
            topMotorStopped = false;
            topMotor.set(speed);
            Output.output(OutputLevel.RAW_MOTORS, "Collector:Top", speed);
        }
    }

    public void setSideSpeed(double speed) {
        if (speed == 0) {
            stopSide();
        } else {
            sideMotorsStopped = false;
            sideMotors.set(speed);
            Output.output(OutputLevel.RAW_MOTORS, "Collector:Side", speed);
        }
    }

    public double getAverageSpeed() {
        return (getTopSpeed() + getSideSpeed()) / 2.0;
    }

    public double getTopSpeed() {
        return topMotorStopped ? 0 : topMotor.get();
    }

    public double getSideSpeed() {
        return sideMotorsStopped ? 0 : sideMotors.get();
    }
}
