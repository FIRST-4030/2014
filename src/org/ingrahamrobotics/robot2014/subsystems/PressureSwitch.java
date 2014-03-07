package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.tables.Output;
import org.ingrahamrobotics.robot2014.tables.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

/**
 * This is the pressure switch in the compressor system.
 */
public class PressureSwitch extends Subsystem {

    private final DigitalInput pSwitch = new DigitalInput(Vst.DIGITAL_IO.PRESSURE_SWITCH);

    public PressureSwitch() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "PressureSwitch:State", "Initialized");
        getAtPressure();
    }

    public void initDefaultCommand() {
    }

    public boolean getAtPressure() {
        // Switch is normally closed, so invert the reading
        boolean val = !pSwitch.get();
        Output.output(OutputLevel.HIGH, "PressureSwitch", val ? "At pressure" : "Not at pressure");
        return val;
    }
}
