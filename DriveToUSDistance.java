/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.UltraSonicUtiliy;
import org.usfirst.frc.team4541.robot.Robot;
import org.usfirst.frc.team4541.robot.SIDE;
import org.usfirst.frc.team4541.robot.XYRController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command is a uses 2 simultaneous PID controllers to control the distance sensed by one of the (4) robot ultrasonic sensors
 * and the gyro angle of the robot.  The command is based on the Drive Straight example code provided.  This method will establish
 * 2 PID controllers, process the source data from the gyro and ultrasonic sensors and send that data to the drive system.
 * The distance target and which side sensor are specified by the calling operation.
 */
public class DriveToUSDistance extends Command {

	private XYRController driverxy, angleController;
	private double fineTargetThreshold=10;
	private int sensorNum, sensorDataReg;
	private double[][] sensorDir = {{0,1},{1,0},{0,-1},{-1,0}};		

	public DriveToUSDistance(double distanceTarget, SIDE side) {
		requires(Robot.drivetrain);
		XYRController driverxy = new XYRController(.5,0,0,0,.5,0,0);
		XYRController angleController = new XYRController(.5,0,0,0,.5,0,0);
				
		/* Set PID Controller parameters*/
		driverxy.driveController.setInputRange(-40, 40);
		angleController.driveController.setInputRange(-180.0, 180.0);
		angleController.driveController.setContinuous(true);
		
		/*   Set target values*/
		driverxy.driveController.setSetpoint(distanceTarget);
		angleController.driveController.setSetpoint(Robot.drivetrain.getGyroYaw());
		
		//Determine which direction the robot is to move based on the US sensor 
		// If front +Y, Right +X, Back, -Y, Left -X
		// Determine data register based on US sensor.
		sensorDataRegister = UltraSonicUtiliy.getSensorReg(side);
		sensorNum=UltraSonicUtiliy.getSensorNum(side);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state
		//enable the auto drive controller
		driverxy.driveController.enable();
		angleController.driveController.enable();
	}
	protected void execute(){
		/* During each iteration of the execute method the following tings are done
		 * Get the distance from the correct ultra sonic sensor and yaw angle from the gyro
		 * Send the position data to the PID controllers via the setInput method
		 * Set the appropriate mode for the PID controller 
		 * Return output values from the PID controller and update update the drive
		 */
		
		//Update robot current position data and send to PID controllers
		driverxy.setInput(UltraSonicUtiliy.getUltrasonic(side));
		angleController.setInput(Robot.drivetrain.getGyroYaw());

		SmartDashboard.putNumber("Robot Distance", UltraSonicUtiliy.getUltrasonic(FRONT));
		SmartDashboard.putNumber("Error", driverxy.driveController.getError());
		SmartDashboard.putNumber("Robot R Location", Robot.drivetrain.getGyroYaw());
		SmartDashboard.putNumber("R Error", angleController.driveController.getError());
		
		// Adjust the PID controller for coarse or fine adjustment

		if (Math.abs(driverxy.driveController.getAvgError())<fineTargetThreshold)
			driverxy.setMode(1);
		else driverxy.setMode(0);

		if (Math.abs(angleController.driveController.getAvgError())<fineTargetThreshold)
			angleController.setMode(1);
		else angleController.setMode(0);

		// Drive based on PID output values;
		
		Robot.drivetrain.drive(sensorDir[sensorNum][0]*driverxy.motorSpeed, sensorDir[sensorNum][1]*driverxy.motorSpeed, angleController.motorSpeed);	
	}

	// Finish command if robot is on target  ... Consider time out??
	@Override
	protected boolean isFinished() {
		return (driverxy.driveController.onTarget() && angleController.driveController.onTarget());
	}

	// Called once after isFinished returns true.. Disable controllers and stop drive motors
	@Override
	protected void end() {
		// Stop PID and the wheels
		driverxy.driveController.disable();
		angleController.driveController.disable();
		Robot.drivetrain.drive(0, 0, 0);
	}
}
