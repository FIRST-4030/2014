package org.ingrahamrobotics.robot2014.commands;

import org.ingrahamrobotics.robot2014.subsystems.ExampleSubsystem;

public class SubsystemStore {

    public final ExampleSubsystem exampleSubsystem;

    public SubsystemStore() {
        this.exampleSubsystem = new ExampleSubsystem();
    }

    public void initCommands() {
        // Initialize commands here, for example:
        ExampleCommand example = new ExampleCommand(this);
    }
}
