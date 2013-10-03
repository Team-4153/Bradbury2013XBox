/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import com.team4153.RobotMap;
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
    private static final double ELEVATOR_SPEED_RATIO = -0.8 ;

    // Shooter control
    private Shooter shooter;

    /* Main driving controller. */
    private Joystick controllerMcDeath = new Joystick(RobotMap.JOYSTICK_MAIN_DRIVING);
    /* Throttle and whether the shooter wheel is running. */
    private Joystick supervisorControl = new Joystick(RobotMap.JOYSTICK_SUPERVISOR);
    
    private CANJaguar leftDrive;
    private CANJaguar rightDrive;
    private CANJaguar heightMotor;
    private CANJaguar tiltMotor;
    private CANJaguar azimuthMotor;
    private Compressor compressor;// = new Compressor(RobotMap.COMPRSSR_PRESSURE_SW_CHANNEL, RobotMap.COMPRSSR_RELAY_CHANNEL);
    private RobotDrive robotDrive;
    private Solenoid shiftUp = new Solenoid(2);
    private Solenoid shiftDown = new Solenoid (1);
    private TCPComms comms;
    
    private AnalogChannel tiltPot;
//    private AnalogChannel rotPot;
    private DigitalInput heightLimit;
    
    private boolean shift;
    private boolean previousTriggerValue;
    private boolean previousHeightLimitValue;
    private boolean previousWheelToggle;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	try {
	    leftDrive = new CANJaguar(RobotMap.JAG_LEFT_DRIVE);
	    rightDrive = new CANJaguar(RobotMap.JAG_RIGHT_DRIVE);
	    heightMotor = new CANJaguar(RobotMap.JAG_HEIGHT_MOTOR);
	    tiltMotor = new CANJaguar(RobotMap.JAG_TILT_MOTOR);
	    azimuthMotor = new CANJaguar(RobotMap.JAG_AZIMUTH_MOTOR);
	    robotDrive = new RobotDrive(leftDrive, rightDrive);
	    
	    shooter = new Shooter();//remember to change your diaper deary
	    comms = new TCPComms();
	    comms.init();
	    
	    leftDrive.setVoltageRampRate(20); // TODO fix magic numbers
	    rightDrive.setVoltageRampRate(20);// TODO fix magic numbers
	    
	    tiltPot = new AnalogChannel(RobotMap.ANALOG_TILT_POT);
//	    rotPot = new AnalogChannel(1);
	    heightLimit = new DigitalInput(RobotMap.SW_ELEVATOR_TOP_LIMIT);
	    
	} catch (CANTimeoutException e) {
	    e.printStackTrace();
	}
	compressor = new Compressor(RobotMap.COMPRSSR_PRESSURE_SW_CHANNEL, RobotMap.COMPRSSR_RELAY_CHANNEL);
	compressor.start();
	shift = false;
	previousTriggerValue = false;
	shiftUp.set(true);
	shiftDown.set(false);//Never gonna give you up. Never gonna let you dooowwn. Never gonna tell a lie. Never gonna say goodbye
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
	System.out.println("Switch: " + compressor.getPressureSwitchValue() + "Enabled: " + compressor.enabled());
	//What does a computer scientist ghost say? BOOlean
	double throttle = (supervisorControl.getRawAxis(4) *-1+1)/2;// TODO fix magic numbers
	boolean shiftTriggerValue = controllerMcDeath.getRawButton(RobotMap.JOYBUTTON_SHIFT);
	//robotDrive.tankDrive(joystickLeft.getY()*throttle, joystickRight.getY()*throttle);
	/* If the robot is at maximum height then limit the speed of the robot to
	 * prevent it tipping over.
	 */
	boolean heightLimitValue = heightLimit.get();
	if(heightLimitValue){
	    robotDrive.arcadeDrive(
		    controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_DRIVE_Y)*0.65*0.8*throttle, // TODO fix this math!
		    controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_DRIVE_X)*0.7*throttle// TODO fix magic numbers
		    );
	}else{
	    robotDrive.arcadeDrive(
		    controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_DRIVE_Y)*0.8*throttle, // TODO fix magic numbers
		    controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_DRIVE_X)*0.8*throttle// TODO fix magic numbers
		    );
	}
	if (shiftTriggerValue&&shiftTriggerValue!=previousTriggerValue){
	    shift = !shift;
	    shiftUp.set(shift);
	    shiftDown.set(!shift);
	}
	previousTriggerValue=shiftTriggerValue;
	//boolean wheelToggle = controllerMcDeath.getRawButton(WHEEL_TOGGLE);
        boolean wheelToggle = supervisorControl.getRawButton(RobotMap.JOYBUTTON_WHEEL_TOGGLE);
	if (wheelToggle&&wheelToggle !=previousWheelToggle){
	    shooter.toggle();
	}
	previousWheelToggle = wheelToggle;
        
	shooter.update();
	if (heightLimitValue != previousHeightLimitValue) {
	    //System.out.println(heightLimitValue+ " " + previousHeightLimitValue);
	    if (heightLimitValue) {
		try {
		    leftDrive.setVoltageRampRate(10);// TODO fix magic numbers
		    rightDrive.setVoltageRampRate(10);// TODO fix magic numbers
		} catch (CANTimeoutException ex) {
		    ex.printStackTrace();
		}
	    } else {
		try {
		    leftDrive.setVoltageRampRate(20);// TODO fix magic numbers
		    rightDrive.setVoltageRampRate(20);// TODO fix magic numbers
		} catch (CANTimeoutException ex) {
		    ex.printStackTrace();
		}
	    }
	}
	previousHeightLimitValue = heightLimitValue;
        
        // Shoot if requested
	if(controllerMcDeath.getRawButton(RobotMap.JOYBUTTON_FIRE)){
	    shooter.fire();//contollerMcDeath really?
	}
        
	double tiltJoystickValue = -controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_ELEVATION);
	double tiltPotValue = tiltPot.getVoltage();
	try{
	    // TODO define what these values mean
            if((tiltJoystickValue < 0 && tiltPotValue > 1.5) || // TODO fix magic numbers
		    (tiltJoystickValue > 0 && tiltPotValue < 4.3)){// TODO fix magic numbers
		tiltMotor.setX(tiltJoystickValue*0.3);// TODO fix magic numbers
	    }else{
		tiltMotor.setX(0);//There are 10 types of people in the world. Those who understand binary and those who don't,and those who didnt expect this joke to be in base three 
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
	double azimuthMotorValue = controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_AZIMUTH);
	try{
	    if (Math.abs(azimuthMotorValue) > 0.3){// TODO fix magic numbers
		azimuthMotor.setX(azimuthMotorValue);
	    } else {
		azimuthMotor.setX(0);
	    }
	}
	catch(CANTimeoutException ex){
	    ex.printStackTrace();
	}
	double heightJoystick = controllerMcDeath.getRawAxis(RobotMap.JOYAXIS_DPAD_X);
	try {
	    if ((heightLimitValue && heightJoystick > 0) || // TODO fix magic numbers
		    heightJoystick < 0){
		heightMotor.setX(heightJoystick* ELEVATOR_SPEED_RATIO); 
	}else 
	    {
		heightMotor.setX(0);
	    }
	}
	catch (CANTimeoutException ex){
	    ex.printStackTrace();//break time. Eat nachos.
	}
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
}
