/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    static final int JOYSTICK_AZIMUTH = 4;//RIGHT_ANALOG_X
    static final int JOYSICK_ELEVATION = 5;//RIGHT_ANALOG_Y
    static final int TRIGGERS = 3;
    static final int JOYSTICK_MOVEMENT_X = 1;//LEFT_ANALOG_X
    static final int JOYSICK_MOVEMENT_Y = 2;//LEFT_ANALOG_Y
    static final int DPAD_X = 6;
    
    static final double ELEVATOR_SPEED_RATIO = -0.8 ;
    
    static final int A = 1;
    static final int B = 2;
    static final int X = 3;
    static final int Y = 4;
    static final int FIRE = 5;//LEFT_BUMPER
    static final int SHIFT = 6;//RIGHT_BUMPER
    static final int WHEEL_TOGGLE = 1;//BACK//No longer back, b/c it's on supervisor
    static final int START = 8;
    static final int LEFT_STICK_PRESS = 9;
    static final int RIGHT_STICK_PRESS = 10;
    
    Shooter shooter;

    Joystick controllerMcDeath = new Joystick(1);
    Joystick supervisorControl = new Joystick(2);
    
    CANJaguar leftDrive;
    CANJaguar rightDrive;
    CANJaguar heightMotor;
    CANJaguar tiltMotor;
    CANJaguar azimuthMotor;
    Compressor compressor = new Compressor(7, 2);
    RobotDrive robotDrive;
    Solenoid shiftUp = new Solenoid(2);
    Solenoid shiftDown = new Solenoid (1);
    TCPComms comms = new TCPComms();
    
    AnalogChannel tiltPot;
    AnalogChannel rotPot;
    DigitalInput heightLimit;
    
    double throttle;
    double tiltJoystick;
    double tiltPotValue;
    double heightJoystick;
    double azimuthMotorValue;
    boolean shift;
    boolean previousTriggerValue;
    boolean shiftTriggerValue;
    boolean previousHeightLimitValue;
    boolean heightLimitValue;
    boolean previousWheelToggle;
    boolean wheelToggle;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	comms.init();
	try {
	    leftDrive = new CANJaguar(8);
	    rightDrive = new CANJaguar(4);
	    heightMotor = new CANJaguar(3);
	    tiltMotor = new CANJaguar(5);
	    azimuthMotor = new CANJaguar(7);
	    robotDrive = new RobotDrive(leftDrive, rightDrive);
	    shooter = new Shooter();
	    leftDrive.setVoltageRampRate(20);
	    rightDrive.setVoltageRampRate(20);
	    
	    tiltPot = new AnalogChannel(2);
	    rotPot = new AnalogChannel(1);
	    heightLimit = new DigitalInput(9);
	    
	} catch (CANTimeoutException e) {
	    e.printStackTrace();
	}
	compressor.start();
	shift = false;
	previousTriggerValue = false;
	shiftUp.set(true);
	shiftDown.set(false);
	previousWheelToggle = false;
	previousHeightLimitValue = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic(){
	//comms.updateTCP();
	throttle = (supervisorControl.getRawAxis(4) *-1+1)/2;
	shiftTriggerValue = controllerMcDeath.getRawButton(SHIFT);
	//robotDrive.tankDrive(joystickLeft.getY()*throttle, joystickRight.getY()*throttle);
	if(heightLimitValue){
	    robotDrive.arcadeDrive(
		    controllerMcDeath.getRawAxis(JOYSICK_MOVEMENT_Y)*0.65*0.8*throttle, 
		    controllerMcDeath.getRawAxis(JOYSTICK_MOVEMENT_X)*0.7*throttle
		    );
	}else{
	    robotDrive.arcadeDrive(
		    controllerMcDeath.getRawAxis(JOYSICK_MOVEMENT_Y)*0.8*throttle, 
		    controllerMcDeath.getRawAxis(JOYSTICK_MOVEMENT_X)*0.8*throttle
		    );
	}
	if (shiftTriggerValue&&shiftTriggerValue!=previousTriggerValue){
	    shift = !shift;
	    shiftUp.set(shift);
	    shiftDown.set(!shift);
	}
	previousTriggerValue=shiftTriggerValue;
	wheelToggle = supervisorControl.getRawButton(WHEEL_TOGGLE);//controllerMcDeath.getRawButton(WHEEL_TOGGLE);
	if (wheelToggle&&wheelToggle !=previousWheelToggle){
	    shooter.toggle();
	}
	previousWheelToggle = wheelToggle;
	shooter.update();
	if (heightLimitValue != previousHeightLimitValue) {
	    //System.out.println(heightLimitValue+ " " + previousHeightLimitValue);
	    if (heightLimitValue) {
		try {
		    leftDrive.setVoltageRampRate(10);
		    rightDrive.setVoltageRampRate(10);
		} catch (CANTimeoutException ex) {
		    ex.printStackTrace();
		}
	    } else {
		try {
		    leftDrive.setVoltageRampRate(20);
		    rightDrive.setVoltageRampRate(20);
		} catch (CANTimeoutException ex) {
		    ex.printStackTrace();
		}
	    }
	}
	previousHeightLimitValue = heightLimitValue;
	if(controllerMcDeath.getRawButton(FIRE)){
	    shooter.fire();
	}
	tiltJoystick = -controllerMcDeath.getRawAxis(JOYSICK_ELEVATION);
	tiltPotValue = tiltPot.getVoltage();
	try{
	    if((tiltJoystick < 0 && tiltPotValue > 1.5) || 
		    (tiltJoystick > 0 && tiltPotValue < 4.3)){
		tiltMotor.setX(tiltJoystick*0.3);
	    }else{
		tiltMotor.setX(0);
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
	azimuthMotorValue = controllerMcDeath.getRawAxis(JOYSTICK_AZIMUTH);
	try{
	    if (Math.abs(azimuthMotorValue) > 0.3){
		azimuthMotor.setX(azimuthMotorValue);
	    } else {
		azimuthMotor.setX(0);
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
	heightJoystick = controllerMcDeath.getRawAxis(DPAD_X);
	heightLimitValue = heightLimit.get();
	try {
	    if ((heightLimitValue && heightJoystick > 0) || 
		    heightJoystick < 0){
		heightMotor.setX(heightJoystick* ELEVATOR_SPEED_RATIO); 
	}else 
	    {
		heightMotor.setX(0);
	    }
	}
	catch (CANTimeoutException ex){
	    ex.printStackTrace();
	}
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
}
