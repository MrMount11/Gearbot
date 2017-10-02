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
public class DriveRotateToAngle extends Command {

	private XYRController angleController;
	private double fineTargetThreshold=10;

	public DriveRotateToAngle(double rtarget) {
		requires(Robot.drivetrain);
		angleController = new XYRController(.5,0,0,0,.5,0,0);
				
		/* Set any required PID Controller parameters*/
		angleController.driveController.setInputRange(-180.0, 180.0);
		angleController.driveController.setContinuous(true);
		
		/*   Set target values*/
		angleController.driveController.setSetpoint(rtarget);
		
		/*PID constructor code from Drive Straight Example
		 * pid = new PIDController(4, 0, 0, new PIDSource() {  // the constructor starts up the three PID controllers
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return Robot.drivetrain.getDistance();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double d) {
				Robot.drivetrain.drive(d, d);
			}
		});
		pid.setAbsoluteTolerance(0.01);
		pid.setSetpoint(distance);*/
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state
		//enable the auto drive controller
		angleController.driveController.enable();
	}
	protected void execute(){
		// write the location data to the controller and read the motor data

		/*Update PID Sources*/
		angleController.setInput(Robot.drivetrain.getGyroYaw());

		SmartDashboard.putNumber("Robot R Location", RobotLocation.rlocation_robot);
		SmartDashboard.putNumber("R Error", angleController.driveController.getError());
		// Adjust the PID controller for coarse or fine adjustment

		if (Math.abs(angleController.driveController.getAvgError())<fineTargetThreshold)
			angleController.setMode(1);
		else angleController.setMode(0);

		// Drive based on PID output values;
		Robot.drivetrain.drive(0,0,angleController.motorSpeed);	
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (angleController.driveController.onTarget());
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		angleController.driveController.disable();
		Robot.drivetrain.drive(0, 0, 0);
	}
}
