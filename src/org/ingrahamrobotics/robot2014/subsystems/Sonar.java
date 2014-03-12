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

    public static final double MIN_RANGE = 20.0;
    public static final double MAX_RANGE = 190.0;
    private static final double SENSOR_VCC = 5.0;
    private static final double MILLIVOLTS_PER_MM = 5120.0;
    private static final double MM_PER_INCH = 25.4;
    private final AnalogChannel sonar = new AnalogChannel(Vst.ANALOG_IO.SONAR);

    public Sonar() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "Sonar:State", "Initialized");
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadSonar());
    }

    public double readDistance() {
        double voltage = sonar.getVoltage();
        Output.output(OutputLevel.RAW_SENSORS, "Sonar Volts", voltage);

        double distance = (voltage / (SENSOR_VCC / MILLIVOLTS_PER_MM)) / MM_PER_INCH;
        if (distance > MIN_RANGE && distance < MAX_RANGE) {
            Output.output(OutputLevel.RAW_SENSORS, "Sonar Inches", distance);
            Output.output(OutputLevel.HIGH, ":RangeGUI", distance);
        } else {
            distance = -1.0;
        }
        return distance;
    }
}
