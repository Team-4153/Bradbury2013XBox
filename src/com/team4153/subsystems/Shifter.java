/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The gearbox shifting subsystem
 * 
 * @author KPT
 */
public class Shifter extends Subsystem {
    /**
     * Used to indicate when the gearbox is in high gear
     * @see #shiftToggle
     */
    public static int STATE_HIGH_GEAR = 0;
    /**
     * Used to indicate when the gearbox is in low gear
     * @see #shiftToggle
     */
    public static int STATE_LOW_GEAR = 1;
    
    private Solenoid shiftUp; 
    private Solenoid shiftDown;
    private int shiftState;
    /**
     * default constructor
     */
    public Shifter() {
        shiftUp = new Solenoid(RobotMap.SOL_SHIFT_UP);
        shiftDown = new Solenoid (RobotMap.SOL_SHIFT_DOWN);
        
        // init default state and gear position
        shiftDown.set(false);
        shiftUp.set(true);
        shiftState = STATE_HIGH_GEAR;
    }
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Shift gears. This is a toggle so if in high gear, we are shifted to low (and vice versa)
     * 
     * @return The gear state that is current after this call: STATE_HIGH_GEAR or STATE_LOW_GEAR 
     * @see #STATE_HIGH_GEAR
     * @see #STATE_LOW_GEAR
     */
    public int shiftToggle(){
        boolean shiftToLow = (shiftState == STATE_HIGH_GEAR);
        
        this.shiftDown.set(shiftToLow);
        this.shiftUp.set(!shiftToLow);
        shiftState = shiftToLow?STATE_LOW_GEAR:STATE_HIGH_GEAR;
        return shiftState;
    }
}