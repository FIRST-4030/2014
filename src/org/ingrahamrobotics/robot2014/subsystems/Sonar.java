package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

/**
 * This is the pressure switch in the compressor system.
 */
public class Sonar extends Subsystem {
    
    private final AnalogChannel pSwitch = new AnalogChannel(Vst.ANALOG_IO.SONAR);

    public Sonar() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "Sonar:State", "Initialized");
    }

    public void initDefaultCommand() {
    }

    public double readDistance() {
        double distance = -1.0;
        double voltage = pSwitch.getVoltage();
        if (voltage > 0) {
            distance = (voltage / 5120.0) / 25.4;
        }
        Output.output(OutputLevel.HIGH, "Sonar", distance);
        return distance;
    }
}
