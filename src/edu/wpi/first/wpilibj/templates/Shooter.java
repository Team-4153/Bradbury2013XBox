/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author 4153student
 */
public class Shooter {
    static final double SHOOTER_VOLTAGE = 5.5;//7.7

    Solenoid shooterRetract = new Solenoid(6);
    Solenoid shooterExtend = new Solenoid(5);
    Relay discRetention = new Relay(3);
    CANJaguar shooterWheel;
    boolean extended;
    boolean fireable;
    boolean wheelRun;

    public Shooter() throws CANTimeoutException {
	shooterWheel = new CANJaguar(9, ControlMode.kVoltage);
	shooterExtend.set(false);
	shooterRetract.set(true);
	fireable = true;
    }

    public void update() {
	try {
	    if (wheelRun) {
		shooterWheel.setX(SHOOTER_VOLTAGE);
	    } else {
		shooterWheel.setX(0);
	    }
	} catch (CANTimeoutException ex) {
	    ex.printStackTrace();
	}
    }

    public void toggle() {
	wheelRun = !wheelRun;
    }

    public void fire() {
	if (fireable&&wheelRun) {
	    fireable = false;
	    //Start a new thread instead of delaying the whole teleopPeriodic()
	    Thread firer = new FireThread();
	    firer.start();
	}
    }

    public void reset() {
	shooterExtend.set(false);
	shooterRetract.set(true);
	discRetention.set(Relay.Value.kOff);
    }

    //Thread to handle the firing of the frisbees so as not to delay the loop
    public class FireThread extends Thread {

	public void run() {
	    discRetention.set(Relay.Value.kForward);
	    try {
		sleep(30);
	    } catch (InterruptedException ex) {
		ex.printStackTrace();
	    }

	    shooterRetract.set(false);
	    shooterExtend.set(true);
	    try {
		sleep(250);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    reset();
	    try {
		sleep(1500);
	    } catch (Exception e) {
		e.printStackTrace();
	    };
	    fireable = true;
	}
    }
}
