
package com.team4153.commands;

/**
 * Chassis drive command via XBox controller.
 * 
 * @author KPT
 */
public class HumanDrive extends CommandBase {

    public HumanDrive() {
        // Use requires() here to declare subsystem dependencies
	requires(chassis);
        
	System.out.println("HumanDrive constructor finished");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	// Drive with XBox controller. The method takes care of scaling, etc.
        chassis.arcadeDrive(oi.getDriverJoystick(), oi.getSupervisorJoystick());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	// we return false to indicate that this command is never finished (always active)
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
	chassis.driveHalt();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//	end();
        
    }
}
