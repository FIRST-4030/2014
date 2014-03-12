package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.commands.ReadSonar;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

/**
 * This is the pressure switch in the compressor system.
 */
public class Sonar extends Subsystem {
    
    private final AnalogChannel sonar = new AnalogChannel(Vst.ANALOG_IO.SONAR);

    public Sonar() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "Sonar:State", "Initialized");
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadSonar());
    }

    public double readDistance() {
        double distance = -1.0;
        double voltage = sonar.getVoltage();
        if (voltage > 0.1) {
            // 5.0 is the assumed Vcc at the sensor
            // 5120.0 is the scaling factor per the sensor spec sheet
            // 25.4 is mm->inches
            distance = (voltage * (5.0 / 5120.0)) / 25.4;
        }
        Output.output(OutputLevel.HIGH, "Sonar", distance);
        return distance;
    }
}
