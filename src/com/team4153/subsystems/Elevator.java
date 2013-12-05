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
	boolean switchVal=!Sensors.getInstance().getHeightLimitBottom().get();
        try{
	    if(switchVal && power < 0){
		motor.setX(0);
	    }else{
		motor.setX(power * 0.7); //TODO take out scaling factor after testing
	    }
	}catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
    }

    
    
}
