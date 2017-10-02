package org.usfirst.frc.team4541.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command Group to back away from the peg and close the gearbox doors
 */
public class BackAway extends CommandGroup {
	public BackAway() {
		addSequential(new DriveForTime(0,-.7,0,1.2));
		addSequential(new CloseDoors());
	}
}