package org.ingrahamrobotics.robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ExampleCommand extends Command {

    private final SubsystemStore ss;
    private boolean finished;

    public ExampleCommand(SubsystemStore ss) {
        this.ss = ss;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ss.exampleSubsystem.doSomething();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    protected void interrupted() {
    }
}
