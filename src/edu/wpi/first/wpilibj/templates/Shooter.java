/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.team4153.RobotMap;
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
    private static final double SHOOTER_VOLTAGE = 7.7;//7.7

    private Solenoid shooterRetract = new Solenoid(RobotMap.SHOOTER_SOLENOID_RETRACT);
    private Solenoid shooterExtend = new Solenoid(RobotMap.SHOOTER_SOLENOID_EXTEND);
    private Relay discRetention = new Relay(RobotMap.RELAY_DISC_RETAINER);
    private CANJaguar shooterWheel;
    private boolean fireable;
    private boolean wheelRun;

    public Shooter() throws CANTimeoutException {
	shooterWheel = new CANJaguar(RobotMap.JAG_SHOOTER_MOTOR, ControlMode.kVoltage);
	shooterExtend.set(false);
	shooterRetract.set(true);
	fireable = true;
    }

    /**
     * This method appears to make sure the shooter wheel motor gets a setting to keep
     * the motor running during iterative robot calls to teleopPeriodic()
     */
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
    private class FireThread extends Thread {

	public void run() {
	    discRetention.set(Relay.Value.kForward);
	    try {
		sleep(30);
	    } catch (InterruptedException ex) {
		ex.printStackTrace();
	    }

	    shooterRetract.set(false);
	    shooterExtend.set(true);
	    msDelay(250);
	    reset();
	    msDelay(1500);
	    fireable = true;
	}
    }
    
    private static void msDelay(long period)
    {
        if (period <= 0) return;
        long end = System.currentTimeMillis() + period;
        boolean interrupted = false;
        do {
            try {
                Thread.sleep(period);
            } catch (InterruptedException ie)
            {
                interrupted = true;
            }
            period = end - System.currentTimeMillis();
        } while (period > 0);
        if (interrupted)
            Thread.currentThread().interrupt();
    }
}
