package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The main autonomous command to drive to center pin eject gear and back away.
 */
public class AutoPlaceGearCenter extends CommandGroup {
	public AutoPlaceGearCenter() {
		boolean i=true;
		addSequential(new DriveForTime(0,0.7,0,4.8));
		addSequential(new DriveToTarget(0,12,0));
		addSequential(new DriveToTarget(0,5,0));
		if (i){
			Scheduler.getInstance().removeAll();
			addSequential(new DriveToTarget(0,5,0));	
		}
		addSequential(new OpenDoors());
		addSequential(new BackAway());
	}
}



