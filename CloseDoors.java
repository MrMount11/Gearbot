package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;

public class CloseDoors extends Command {	
	public CloseDoors() {
		requires(Robot.gearbox);
		setTimeout(1);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
			Robot.gearbox.closeDoors();
	}
	// Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.gearbox.isDoorsClosed() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.gearbox.stopDoors();
		SmartDashboard.putBoolean("DOOR OPEN", Robot.gearbox.isDoorsOpen());
	}
}
