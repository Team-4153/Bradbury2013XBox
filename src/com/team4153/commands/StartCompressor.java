/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.commands;

/**
 *
 * @author kirk
 */
public class StartCompressor extends CommandBase {
    
    public StartCompressor() {
        // Use requires() here to declare subsystem dependencies
        requires(compressor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.setInterruptible(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        compressor.startCompressor();
        System.out.println("StartCompressor.execute(): compressor.startCompressor() called");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}