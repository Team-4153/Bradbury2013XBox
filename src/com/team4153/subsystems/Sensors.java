/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Sensor singleton that provides access to the sensors on the robot
 * 
 * @author KPT
 */
public class Sensors {
    private static Sensors INSTANCE = new Sensors();
    
    private AnalogChannel tiltPot;
    private AnalogChannel azimuthPot;
    private DigitalInput heightLimitBottom;
    private DigitalInput heightLimitMiddel;
    
    private Sensors() {
        // private to inhibit instantiation by any other (except via reflection)
        	    
	tiltPot = new AnalogChannel(RobotMap.ANALOG_TILT_POT);
        azimuthPot = new AnalogChannel(RobotMap.ANALOG_AZIMUTH_POT);
        heightLimitBottom = new DigitalInput(RobotMap.SW_ELEVATOR_BOTTOM_LIMIT);
	heightLimitMiddel = new DigitalInput(RobotMap.SW_ELEVATOR_MIDDEL_LIMIT);
    }
    
    /**
     * Get the Sensors instance [singleton] for access to sensor data.
     * 
     * @return the Sensor singleton
     */
    public static Sensors getInstance(){
        return INSTANCE;
    }
        
    /**
     *
     * @return The tilt potentiometer AnalogChannel object
     */
    public AnalogChannel getTiltPot() {
        return tiltPot;
    }
    
    /**
     *
     * @return The turret rotation (azimuth) potentiometer AnalogChannel object
     */
    public AnalogChannel getAzimuthPot() {
        return azimuthPot;
    }
    
    /**
     *
     * @return The elevator height limit bottom switch DigitalInput object
     */
    public DigitalInput getHeightLimitBottom() {
        return heightLimitBottom;
    }
    
    /**
     *
     * @return The elevator height limit middel switch DigitalInput object
     */
    public DigitalInput getHeightLimitMiddel() {
        return heightLimitMiddel;
    }
}
