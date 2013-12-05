/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import com.team4153.commands.HumanShooterWheel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author George
 */
public class ShooterWheel extends Subsystem {

    CANJaguar motor;
    boolean running=false;
    
    public ShooterWheel(){
        try{
        motor=new CANJaguar(RobotMap.JAG_SHOOTER_MOTOR);
        }catch (CANTimeoutException ex) {
	    System.out.println("!** Shooter Wheel constructor CANTimeoutException: " + ex.toString());
	    System.exit(-1);
	}
    }
    
    protected void initDefaultCommand() {
    }
    
    public void buttonPressed(){
        if(running){
            running=false;
            try{
            motor.setX(0);
            }catch (CANTimeoutException ex) {
	    System.out.println("!** ShifterWheel.buttonPressed CANTimeoutException: " + ex.toString());
	    System.exit(-1);
            }
        }
        else{
            running=true;
            try{
            motor.setX(0.7);
            }catch (CANTimeoutException ex) {
	    System.out.println("!** ShifterWheel.buttonPressed CANTimeoutException: " + ex.toString());
	    System.exit(-1);
            } 
        
        }
    }
    
}
