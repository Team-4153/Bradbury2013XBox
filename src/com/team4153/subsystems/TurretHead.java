/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import com.team4153.commands.CommandBase;
import com.team4153.commands.HumanTurret;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for control of the turret movement
 * 
 * @author Nate He's a PROgrammer
 */
public class TurretHead extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private CANJaguar pitchMotor;
    private CANJaguar yawMotor;
    //private AnalogChannel pitchPot;
    
    public TurretHead (){
        try{
            yawMotor = new CANJaguar(RobotMap.JAG_AZIMUTH_MOTOR);
            pitchMotor = new CANJaguar(RobotMap.JAG_TILT_MOTOR);
            //pitchPot = new AnalogChannel(RobotMap.ANALOG_TILT_POT);
        } catch (CANTimeoutException ex) {
	    System.out.println("!** TurretHead constructor CANTimeoutException: " + ex.toString());
	    System.exit(-1);
	}
    }
    
    /**
     * Makes the turret pitch and yaw
     * 
     * @param pitchJoystickValue the pitch from the joystick
     * 
     * @param yawMotorValue the yaw from the joystick
    */
    public void turretDrive(double pitchJoystickValue, double yawMotorValue){
        
	double pitchPotValue = Sensors.getInstance().getTiltPot().getVoltage();
	try{
	    // TODO define what these values mean
            if((pitchJoystickValue < 0 && pitchPotValue > 1.5) || // TODO fix magic numbers
		    (pitchJoystickValue > 0 && pitchPotValue < 4.3)){// TODO fix magic numbers
		pitchMotor.setX(pitchJoystickValue*0.3);// TODO fix magic numbers
	    }else{
		pitchMotor.setX(0);
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
	
	try{
	    if (Math.abs(yawMotorValue) > 0.3){// TODO fix magic numbers
		yawMotor.setX(yawMotorValue);
	    } else {
		yawMotor.setX(0);
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
    }
    
    /**
     * Moves the turret based on the joystick values
     * 
     * @param stick the joystick (XBox controller)
     */
    
    public void turretDrive(Joystick stick){
        double pitchJoystickValue = -stick.getRawAxis(RobotMap.JOYAXIS_ELEVATION);
        double yawMotorValue = stick.getRawAxis(RobotMap.JOYAXIS_AZIMUTH);
        turretDrive(pitchJoystickValue,yawMotorValue);
    }
     
             
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new HumanTurret());
    }
}
