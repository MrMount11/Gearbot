/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team4541.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The climber subsystem is a simple system with two motors driving the climber thru
 * a single gear box.
 * * If using stronger motors, you should probably use a sensor so that the motors
 * don't stall.
 */
public class Climber extends Subsystem {
	private CANTalon climberMotor1 = new CANTalon(4);
	private CANTalon climberMotor2 = new CANTalon(5);
	private boolean isClimbing = false; 

	public Climber() {
		super();

		// Let's show everything on the LiveWindow
		LiveWindow.addActuator("ClimberSys", "ClimbMotor #1", (CANTalon) climberMotor1);
		// Let's show system status in log file
		System.out.println("Climber System is Alive");
	}

	@Override
	public void initDefaultCommand() {
	}

	public void log() {
		SmartDashboard.putBoolean("Climber Running", isClimbing);
	}

	/**
	 * Starts the climber motors.
	 */
	public void startClimb() {
		climberMotor1.set(-1);
		climberMotor2.set(-1);
		isClimbing = true;
	}

	/**
	 * Stop the climber motors.
	 */
	public void stopClimb() {
		climberMotor1.set(0);
		climberMotor2.set(0);
		isClimbing = false;
	}
}
