package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4541.robot.Robot;

/**
 * Have the robot drive with Mecanum style with and x box style joystick until interrupted.
 */
public class DriveWithJoystick extends Command {

	public DriveWithJoystick() {
		requires(Robot.drivetrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.drivetrain.drive(-Robot.oi.modStickIn(1),Robot.oi.modStickIn(0),-Robot.oi.modStickIn(4));
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.drive(0, 0, 0);
	}
}

			