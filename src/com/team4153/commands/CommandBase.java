package com.team4153.commands;

import com.team4153.OI;
import com.team4153.subsystems.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.xxx
 * 
 * @author KPT
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems here
    public static Chassis chassis = new Chassis();
    public static Shifter shifter = new Shifter();
    public static CompressorOfPower compressor = new CompressorOfPower(); 
    public static TurretHead turretHead = new TurretHead();
    public static Elevator elevator = new Elevator();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be instantiated
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        
        // start the Compressor thread. The WPI Compressor class (instance returned by
        // calling compressor.getCompressor()) seems to take care
        // of all its own thread and driver station mode management so we start it here
        // and assume it stops when FMS et al. stops the robot.
        //compressor.getCompressor().start(); TODO if works via OI, remove this
        
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(chassis);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
