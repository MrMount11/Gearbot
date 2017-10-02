package org.usfirst.frc.team4541.robot.commands;

import org.usfirst.frc.team4541.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class AutoPlaceGearRight extends CommandGroup {
	public AutoPlaceGearRight() {
		// add drive straigtdrive.straightDrive(0, 0, -0.7, gyro, 0);
		addSequential(new DriveForTime(0,0.7,0,4.8));
		addParallel(new DriveRotateToAngle(-60));
		addSequential(new DriveToTarget(0,12,0));
		if (Robot.gearbox.isDoorsClosed())
			
		addSequential(new ToggleDoor());
		addSequential(new BackAway());
		
	}
}



