package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.commands.AutoPlaceGearCenter;
import org.usfirst.frc.team4541.robot.commands.AutoPlaceGearLeft;
import org.usfirst.frc.team4541.robot.commands.AutoPlaceGearRight;
import org.usfirst.frc.team4541.robot.subsystems.Climber;
import org.usfirst.frc.team4541.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4541.robot.subsystems.GearBox;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Command autonomousCommand;
	SendableChooser<CommandGroup> autochooser;

	public static DriveTrain drivetrain;
	public static GearBox gearbox;
	public static Climber climber;
	public static OI oi;  //must be instantiated last of subsystems

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize all subsystems
		drivetrain = new DriveTrain();
		gearbox = new GearBox();
		climber = new Climber();
		// OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it
		oi = new OI();
		autochooser = new SendableChooser<CommandGroup>();
		autochooser.addObject("Place Gear Auto Left", new AutoPlaceGearLeft());
		autochooser.addDefault("Place Gear Auto Center", new AutoPlaceGearCenter());
		autochooser.addObject("Place Gear Auto Right", new AutoPlaceGearRight());
		SmartDashboard.putData("Auto Modes", autochooser);
		
		//  Setup Camera Server for axis cameras
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);

		// instantiate the command used for the autonomous period
		//autonomousCommand = new Autonomous();

		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(gearbox);
		SmartDashboard.putData(climber);
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) autochooser.getSelected();
		autonomousCommand.start(); // schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		gearbox.log();
		climber.log();
		drivetrain.log();
	}
}
