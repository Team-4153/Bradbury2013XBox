/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.commands;

import com.team4153.OI;
import com.team4153.RobotMap;

/**
 *
 * @author 4153student
 */
public class HumanElevator extends CommandBase {

    public HumanElevator(){
	requires(elevator);
    }
    
    protected void initialize() {
    }

    protected void execute() {
	double power = oi.getDriverJoystick().getRawAxis(RobotMap.JOYAXIS_DPAD_X);
	elevator.elevate(power);
    }

    protected boolean isFinished() {
	// we return false to indicate that this command is never finished (always active)
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    
    
}
