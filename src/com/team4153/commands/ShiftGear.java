/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.commands;

/**
 * The gear shifting command. Triggered in OI.
 * 
 * @author KPT
 */
public class ShiftGear extends CommandBase {
    
    public ShiftGear() {
        // Use requires() here to declare subsystem dependencies
        requires(shifter);
    }

    // This method sets up the command and is called immediately before the 
    // command is executed for the first time and every subsequent time it 
    // is started. Any initialization code should be here.
    protected void initialize() {
        //this.setInterruptible(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shifter.shiftToggle(); // ignore retval
    }

    // Make this return true when this Command no longer needs to run execute().
    // This is called by the scheduler after an execute() iteration has completed.
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