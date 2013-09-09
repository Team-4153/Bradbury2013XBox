/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.subsystems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;

/**
 *
 * @author KPT
 */
public class CompressorOfPower extends Subsystem {
    private Compressor compressor = new Compressor(RobotMap.COMPRSSR_PRESSURE_SW_CHANNEL, RobotMap.COMPRSSR_RELAY_CHANNEL);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /** Start the compressor thread
     *
     */
    public void startCompressor(){
        compressor.start();
    }
    
    /**
     * Stop the compressor thread
     */
    public void stopCompressor(){
        compressor.stop();
    }
}