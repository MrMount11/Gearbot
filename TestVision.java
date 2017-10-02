/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.RobotLocation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command is a test to see if we can calculate the X and Y distance to the visual targets
 * 
 *  */
public class TestVision extends Command {

	public TestVision() {
		//requires(null);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("Test Vision Enabled", true);
	}
	protected void execute(){
		// write the location data to the controller and read the motor data
		// Get the current Robot Location */
		RobotLocation.getRobotLocation(0);

		SmartDashboard.putNumber("Robot X Location", RobotLocation.xlocation_robot);
		SmartDashboard.putNumber("Robot Y Location", RobotLocation.ylocation_robot);
		SmartDashboard.putNumber("Robot R Location", RobotLocation.rlocation_robot);
		// Adjust the PID controller for coarse or fine adjustment
	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false ;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		SmartDashboard.putBoolean("Test Vision Enabled", false);
	}
}
