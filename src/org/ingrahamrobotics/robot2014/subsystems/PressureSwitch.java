package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.log.Output;
import org.ingrahamrobotics.robot2014.log.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

/**
 * SubSystem for reading the PressureSwitch and outputting to VstP.
 *
 * This is the pressure switch in the compressor system.
 */
public class PressureSwitch extends Subsystem {

    private final DigitalInput pSwitch = new DigitalInput(Vst.DIGITAL_IO.PRESSURE_SWITCH);

    public PressureSwitch() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "PressureSwitch:Initialized", true);
    }

    public void initDefaultCommand() {
    }

    public boolean getAtPressure() {
        // Switch is normally closed, so invert the reading
        boolean val = !pSwitch.get();
        Output.output(OutputLevel.RAW_SENSORS, "PressureSwitch:AtPressure", val);
        return val;
    }
}
