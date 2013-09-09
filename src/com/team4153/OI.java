package com.team4153;


import com.team4153.commands.ShiftGear;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private Joystick controllerMcDeath;
    private Joystick supervisorControl;
    private JoystickButton triggerButton;

    public OI() {
        // Main driving controller.
        controllerMcDeath = new Joystick(RobotMap.JOYSTICK_MAIN_DRIVING);
        
        /* Throttle and whether the shooter wheel is running. */
        supervisorControl = new Joystick(RobotMap.JOYSTICK_SUPERVISOR);
        
        // to fire a frisbee. Buttons created this way are polled by the
        // scheduler so we don't have to worry about doing that.
        triggerButton = new JoystickButton(controllerMcDeath, RobotMap.JOYBUTTON_FIRE);
//        triggerButton.whenPressed(new ShootCommand());
        
        // Shift gear when the button is pressed
        (new JoystickButton(controllerMcDeath, RobotMap.JOYBUTTON_SHIFT)).whenPressed(new ShiftGear());
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    //
    // TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    public Joystick getDriverJoystick() {
        return controllerMcDeath;
    }

    public Joystick getSupervisorJoystick() {
        return supervisorControl;
    }
//    public Button getTriggerButton() {
//        return triggerButton;
//    }
}
