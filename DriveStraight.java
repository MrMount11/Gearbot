
package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotLocation;
import org.usfirst.frc.team4541.robot.XYRController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command is uses an XYR controller, to control/hold the target angle.
 *
 */
public class DriveStraight extends Command {

	private XYRController angleController;
	private double fineTargetThreshold=10;
	private boolean isAngleChanging=false;

	public DriveStraight(double rtarget) {
		requires(Robot.drivetrain);
		angleController = new XYRController(.5,0,0,0,.5,0,0);
				
		/* Set any required PID Controller parameters*/
		angleController.driveController.setInputRange(-180.0, 180.0);
		angleController.driveController.setContinuous(true);
	}
	
	@Override
	protected void initialize() {  // Called just before this Command runs the first time
		// Get everything in a safe starting state
		//enable the auto drive controller
		angleController.driveController.enable();
		angleController.driveController.setSetpoint(Robot.drivetrain.getGyroYaw());
		SmartDashboard.putBoolean("Straight Drive Enabled", true);
	}
	protected void execute(){
		// write the location data to the PID controller and read the motor data

		/*Update PID Sources*/
		//if there is R input from the joy stick then allow the robot to rotate in response 
		//if this input stops then update the angle target to te new gyro yaw angle.
		if (Math.abs(Robot.oi.modStickIn(4)) !=  0){
			Robot.drivetrain.drive(-Robot.oi.modStickIn(1),Robot.oi.modStickIn(0),-Robot.oi.modStickIn(4));
			isAngleChanging = true;
		}
		else {	
			if (isAngleChanging){
				angleController.driveController.setSetpoint(Robot.drivetrain.getGyroYaw());
				isAngleChanging = false;
			}
			angleController.setInput(Robot.drivetrain.getGyroYaw());

			SmartDashboard.putNumber("Robot R Location", RobotLocation.rlocation_robot);
			SmartDashboard.putNumber("R Error", angleController.driveController.getError());
			// Adjust the PID controller for coarse or fine adjustment

			if (Math.abs(angleController.driveController.getAvgError())<fineTargetThreshold)
				angleController.setMode(1);
			else angleController.setMode(0);

			// Drive based on PID output values;
			Robot.drivetrain.drive(-Robot.oi.modStickIn(1),Robot.oi.modStickIn(0),angleController.motorSpeed);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
			return false;  //Continue until interrupted by command toggle
	}

	protected void interrupted(){
		end();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		angleController.driveController.disable();
		Robot.drivetrain.drive(0, 0, 0);
		SmartDashboard.putBoolean("Straight Drive Enabled", false);
	}
}
