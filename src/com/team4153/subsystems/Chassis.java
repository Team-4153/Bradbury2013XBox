
package com.team4153.subsystems;

import com.team4153.RobotMap;
import com.team4153.commands.HumanDrive;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Chassis subsystem for XBox controller drive
 * 
 * @author KPT
 */
public class Chassis extends Subsystem {
    private final static double MOTOR_RAMPING_VALUE = 20.0;
    // get the Sensors singleton. This gives us access to the sensors it implements
    private static Sensors sensors = Sensors.getInstance();
    private RobotDrive robotDrive;
    /**
     * Jaguar CAN bus motor controllers.
     */
    private CANJaguar leftDrive;
    private CANJaguar rightDrive;
    
    /**
     * Default constructor
     */ 
    public Chassis(){
	try {
	    leftDrive = new CANJaguar(RobotMap.JAG_LEFT_DRIVE);
	    rightDrive = new CANJaguar(RobotMap.JAG_RIGHT_DRIVE);
	} catch (CANTimeoutException ex) {
	    System.out.println("!** Chassis constructor CANTimeoutException: " + ex.toString());
	    System.exit(-1);
	}
        this.setVoltageRampRate(MOTOR_RAMPING_VALUE);         
        robotDrive = new RobotDrive(leftDrive, rightDrive);
	robotDrive.setSafetyEnabled(false);
        
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for the subsystem here. This command will run on startup
        // and will always be defaulted to when no other command is running.
        setDefaultCommand(new HumanDrive());
    }
   
	    
    /**
     * The command to drive arcade via XBox controller and "supervisor" joystick
     * 
     * @param stick The driver's joystick (usually via OI)
     * @param supervisorControl The supervisor joystick (manip?) (usually via OI) 
     */
    public void arcadeDrive(Joystick stick, Joystick supervisorControl){
	double driveYval, driveXval;
        double throttleRatio;
        
        //throttleRatio = (supervisorControl.getRawAxis(RobotMap.JOYAXIS_THROTTLE) *-1+1)/2;// TODO fix/document magic numbers
	throttleRatio = 1;
        driveXval = stick.getRawAxis(RobotMap.JOYAXIS_DRIVE_X) * throttleRatio;
        driveYval = stick.getRawAxis(RobotMap.JOYAXIS_DRIVE_Y) * throttleRatio;
        
        // Limit speed when robot is standing tall
        boolean heightLimitValue = sensors.getHeightLimitBottom().get();
	if(heightLimitValue){
	    // scale the speed down when robot is tall
            driveXval *= 0.52;
            driveYval *= 0.7;
	}
        // TODO do the "change voltageRampRate setting based on height of robot" code here
        
        // Command the robot to drive
        this.robotDrive.arcadeDrive(driveYval, driveXval);
    }

    /**
     * Stop the robot chassis from moving
     */
    public void driveHalt() {
	System.out.println("** driveHalt");
	this.robotDrive.setLeftRightMotorOutputs(0,0);
    }
    
    /**
     * Set the voltage ramping rate for the drive motors. Set this to 0.0 to disable rate limiting.
     * @param rate The rate in volts/sec
     */
    private void setVoltageRampRate(double rate){
        try {
            leftDrive.setVoltageRampRate(rate);
            rightDrive.setVoltageRampRate(rate);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}

