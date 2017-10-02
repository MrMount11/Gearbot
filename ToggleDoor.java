package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;

public class ToggleDoor extends Command {
	boolean isOpening;	
	public ToggleDoor() {
		requires(Robot.gearbox);
		setTimeout(2);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if(Robot.gearbox.isDoorsClosed()){
			Robot.gearbox.openDoors();
			isOpening = true;
		}
		else if (Robot.gearbox.isDoorsOpen()){
			Robot.gearbox.closeDoors();
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
			return Robot.gearbox.isDoorsOpen() || isTimedOut();
		}
		else return Robot.gearbox.isDoorsClosed() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.gearbox.stopDoors();
		SmartDashboard.putBoolean("DOOR OPEN", Robot.gearbox.isDoorsOpen());
	}
}
