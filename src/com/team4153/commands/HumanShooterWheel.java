/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.commands;

/**
 *
 * @author George
 */
public class HumanShooterWheel extends CommandBase {

    public HumanShooterWheel(){
        requires(shooterWheel);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        shooterWheel.buttonPressed();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
