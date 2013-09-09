/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author KPT
 */
public class Sensors {
    private static Sensors INSTANCE = new Sensors();
    
    private AnalogChannel tiltPot;
    private AnalogChannel azimuthPot;
    private DigitalInput heightLimit;
    
    private Sensors() {
        // private to inhibit instantiation by any other (except via reflection)
        	    
	tiltPot = new AnalogChannel(RobotMap.ANALOG_TILT_POT);
        azimuthPot = new AnalogChannel(RobotMap.ANALOG_AZIMUTH_POT);
        heightLimit = new DigitalInput(RobotMap.SW_ELEVATOR_TOP_LIMIT);
    }
    
    /**
     * Get the Sensors instance for access to sensor data.
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
     * @return The elevator height limit switch DigitalInput object
     */
    public DigitalInput getHeightLimit() {
        return heightLimit;
    }
}
