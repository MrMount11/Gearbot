package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.Robot;

/* Note that this command is executed using the whileheld button option.  It is possible tat tis will not
 * operate as advertised.  If we have trouble with this theother way to do it is to create a second command to stop
 * the climber which gets executed when te the button is released.
 * button.whenpressed starts runclimer
 * button.when released starts stopclimber
 */


public class RunClimber extends Command {	
	public RunClimber() {
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.climber.startClimb();
		SmartDashboard.putBoolean("CLIMBER", true);
	}
	// Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {// This will cause it to run indefinitely until it is interrupted StopClimber Command
			return false;
	}
	protected void interrupted(){
		Robot.climber.stopClimb();
		end();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		SmartDashboard.putBoolean("CLIMBER", false);
	}
}
