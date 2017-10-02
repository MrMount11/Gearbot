package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;

public class ToggleFlap extends Command {
	boolean isOpening;	
	public ToggleFlap() {
		requires(Robot.gearbox);
		setTimeout(10);
		System.out.println("Toggle Flap is active");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.gearbox.isFlapClosed()){
			Robot.gearbox.openFlap();
			isOpening = true;
		}
		else if (Robot.gearbox.isFlapOpen()){
			Robot.gearbox.closeFlap();
			isOpening = false;
		}
	}
	// Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (isOpening){
			return Robot.gearbox.isFlapOpen() || isTimedOut();
		}
		else return Robot.gearbox.isFlapClosed() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.gearbox.stopFlap();
		SmartDashboard.putBoolean("FLAP OPEN", Robot.gearbox.isFlapOpen());
		SmartDashboard.putBoolean("FLAP CLOSED", Robot.gearbox.isFlapClosed());
		}
}
