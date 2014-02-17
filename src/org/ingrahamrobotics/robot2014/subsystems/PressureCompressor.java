package org.ingrahamrobotics.robot2014.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.ingrahamrobotics.robot2014.commands.RunCompressor;
import org.ingrahamrobotics.robot2014.output.Output;
import org.ingrahamrobotics.robot2014.output.OutputLevel;
import org.ingrahamrobotics.robot2014.variablestore.Vst;

/**
 * This is the subsystem to handle the PressureCompressor.
 */
public class PressureCompressor extends Subsystem {

    private final Relay compressor = new Relay(Vst.RELAY.COMPRESSOR_SPIKE);

    public PressureCompressor() {
        Output.output(OutputLevel.INITIALIZED_SYSTEMS, "PressureCompressor:State", "Initialized");
        setRunning(false);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new RunCompressor());
    }

    public void setRunning(boolean running) {
        if (running) {
            compressor.set(Relay.Value.kOn);
        } else {
            compressor.set(Relay.Value.kOff);
        }
        Output.output(OutputLevel.RAW_MOTORS, "Compressor:Running", running);
    }
}
