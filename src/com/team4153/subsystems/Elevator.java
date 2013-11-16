/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.team4153.RobotMap;
import com.team4153.commands.HumanElevator;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
	
	

/**
 *
 * @author 4153student
 */
public class Elevator extends Subsystem {

    CANJaguar motor;
    public Elevator(){
	try {
	    motor = new CANJaguar(RobotMap.JAG_HEIGHT_MOTOR);
	} catch (CANTimeoutException ex) {
	    ex.printStackTrace();
	}
    }
    
    protected void initDefaultCommand() {
	setDefaultCommand(new HumanElevator());
    }
    
    public void elevate(double power){
	try{
	    if(Sensors.getInstance().getHeightLimitBottom().get() && power < 0){
		motor.setX(0);
	    }else{
		motor.setX(power);
	    }
	}catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
    }

    
    
}
