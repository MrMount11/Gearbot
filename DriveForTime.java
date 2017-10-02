package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * This command is used to drive for a specific length of time
 */
public class DriveForTime extends TimedCommand {
	double xRate, yRate, rRate;

    public DriveForTime(double xrate, double yrate, double rrate, double timeout) {
        super(timeout);
        requires(Robot.drivetrain);
        xRate = xrate;
        yRate = yrate;
        rRate = rrate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive(xRate, yRate, rRate);	
    }

    // Called once after timeout
    protected void end(){
    	Robot.drivetrain.drive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}