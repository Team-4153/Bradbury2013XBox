package com.team4153;

/**
 * The RobotMap is a mapping from the ports, sensors and actuators that are wired 
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    //
    // **ADDENDUM TO ABOVE BY KPT: You should use UPPERCASE for constant identifiers. The
    //                             WPI guys are showing you bad habits above. This is a style
    //                             thing and has no syntactical significance.
    
    
    // Joysticks and axes IDs
    // Driver
    public static final int JOYSTICK_MAIN_DRIVING = 1;
    public static final int JOYBUTTON_SHIFT = 6;//RIGHT_BUMPER
    public static final int JOYBUTTON_FIRE =  5;//LEFT_BUMPER
    public static final int JOYAXIS_DRIVE_Y = 2;
    public static final int JOYAXIS_DRIVE_X = 1;
    public static final int JOYAXIS_DPAD_X = 6;
    public static final int JOYAXIS_AZIMUTH = 4;//RIGHT_ANALOG_X
    public static final int JOYAXIS_ELEVATION = 5;//RIGHT_ANALOG_Y 
    // Manip
    public static final int JOYSTICK_SUPERVISOR = 2;
    public static final int JOYBUTTON_WHEEL_TOGGLE = 1;//BACK//No longer back, b/c it's on supervisor TODO clarify this comment
    public static final int JOYAXIS_THROTTLE = 4;// Throttle lever
    
    // Shooter solenoid, shifter solenoid, and relay channel IDs
    public static final int SOL_SHOOTER_RETRACT = 6;
    public static final int SOL_SHOOTER_EXTEND = 5;
    public static final int RELAY_DISC_RETAINER = 3;
    public static final int SOL_SHIFT_UP = 2;
    public static final int SOL_SHIFT_DOWN = 1;
    
    // Compressor channel IDs
    public static final int COMPRSSR_PRESSURE_SW_CHANNEL = 7;
    public static final int COMPRSSR_RELAY_CHANNEL = 2;
    
    // Jaguar motor controller CAN bus ID's
    public static final int JAG_RIGHT_DRIVE = 4;
    public static final int JAG_LEFT_DRIVE = 8;
    public static final int JAG_HEIGHT_MOTOR = 3;
    public static final int JAG_TILT_MOTOR = 5;
    public static final int JAG_AZIMUTH_MOTOR = 7;
    public static final int JAG_SHOOTER_MOTOR = 9;
    
    // Limit switches
    public static final int SW_ELEVATOR_MIDDEL_LIMIT=8;
    public static final int SW_ELEVATOR_BOTTOM_LIMIT=9;
    
    // Analog I/O
    public static final int ANALOG_AZIMUTH_POT = 1;
    public static final int ANALOG_TILT_POT = 2;
    
}
