/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.commands;

/**
 *
 * @author frc
 */
public class HumanTurret extends CommandBase {
    
    public HumanTurret() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(turretHead);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        turretHead.turretDrive(oi.getDriverJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // we return false to indicate that this command is never finished (always active)
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        
    }
}
