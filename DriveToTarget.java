/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.RobotLocation;
import org.usfirst.frc.team4541.robot.XYRController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command is a test to see if I can implement the 3 PID controller idea into the command based
 * program structure.  The command is based on the Drive Straight example code provided.  This method will establish
 * 3 PID controllers, one each for the X,Y,& R axes of motion.  Location data and target is supplied.
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is supplied from vision input and gyro, but the extended idea is that the 
 * user can supply the location data from anywhere
 * encoders.
 */
public class DriveToTarget extends Command {

	private XYRController driverx, drivery, driverr;
	private double fineTargetThreshold=10;
	public boolean setAbortFlag = false;

	public DriveToTarget(double xtarget,double ytarget,double rtarget) {
		requires(Robot.drivetrain);
		setTimeout(10);
		driverx = new XYRController(.2,0,0,0,.5,0,0);
		drivery = new XYRController(.2,0,0,0,.5,0,0);
		driverr = new XYRController(.2,0,0,0,.5,0,0);
				
		/* Set PID Controller parameters*/
		driverx.driveController.setInputRange(-40, 40);
		drivery.driveController.setInputRange(0, 40);
		driverr.driveController.setInputRange(-180.0, 180.0);
		driverr.driveController.setContinuous(true);
		
		
		/*   Set target values*/
		driverx.driveController.setSetpoint(xtarget);
		drivery.driveController.setSetpoint(ytarget);
		driverr.driveController.setSetpoint(rtarget);
		SmartDashboard.putString("Controller Setup ","TRUE");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state
		//enable the auto drive controller
		driverx.driveController.enable();
		drivery.driveController.enable();
		driverr.driveController.enable();
		SmartDashboard.putString("Controller enabled ","TRUE");

	}
	protected void execute(){
		// write the location data to the controller and read the motor data
		// Get the current Robot Location */
		SmartDashboard.putString("Executing ","TRUE");
		RobotLocation.getRobotLocation(0);
		

		/*Update PID Sources*/
		driverx.setInput(RobotLocation.xlocation_robot);
		drivery.setInput(RobotLocation.ylocation_robot);
		driverr.setInput(RobotLocation.rlocation_robot);

		SmartDashboard.putNumber("Robot X Location", RobotLocation.xlocation_robot);
		SmartDashboard.putNumber("X Error", driverx.driveController.getError());
		SmartDashboard.putNumber("Robot Y Location", RobotLocation.ylocation_robot);
		SmartDashboard.putNumber("Y Error", drivery.driveController.getError());
		SmartDashboard.putNumber("Robot R Location", RobotLocation.rlocation_robot);
		SmartDashboard.putNumber("R Error", driverr.driveController.getError());
		// Adjust the PID controller for coarse or fine adjustment

		if (Math.abs(driverx.driveController.getAvgError())<fineTargetThreshold)
			driverx.setMode(1);
		else driverx.setMode(0);

		if (Math.abs(drivery.driveController.getAvgError())<fineTargetThreshold)
			drivery.setMode(1);
		else drivery.setMode(0);

		if (Math.abs(driverr.driveController.getAvgError())<fineTargetThreshold)
			driverr.setMode(1);
		else driverr.setMode(0);

		// Drive based on PID output values;
		Robot.drivetrain.drive(driverx.motorSpeed,drivery.motorSpeed,driverr.motorSpeed);	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (isTimedOut())
			setAbortFlag=true;
		return ((driverx.driveController.onTarget() && drivery.driveController.onTarget() && driverr.driveController.onTarget()) || isTimedOut());
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		driverx.driveController.disable();
		drivery.driveController.disable();
		driverr.driveController.disable();
		Robot.drivetrain.drive(0, 0, 0);
	}
}
