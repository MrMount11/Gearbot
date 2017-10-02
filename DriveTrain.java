package org.usfirst.frc.team4541.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.commands.DriveWithJoystick;
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. This includes four drive motors and a gyro.
 */
public class DriveTrain extends Subsystem {
	private CANTalon frontLeftMotor = new CANTalon(0);
	private CANTalon rearLeftMotor = new CANTalon(1);
	private CANTalon frontRightMotor = new CANTalon(2);
	private CANTalon rearRightMotor = new CANTalon(3);
	private RobotDrive drive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	private AHRS gyro = new AHRS(SPI.Port.kMXP);

	public DriveTrain() {
		super();
	}

	/** When no other command is running let the operator drive around using the joystick*/
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

	/**The log method puts interesting information to the SmartDashboard.*/
	public void log() {
			SmartDashboard.putNumber("Gyro", gyro.getYaw());
	}

	public void drive(double xrate, double yrate, double rrate){
		//for 2017 robot may have to map the values differently//
		double a=yrate;
		yrate = rrate;
		rrate = a;
		drive.mecanumDrive_Cartesian(xrate, yrate, rrate, gyro.getYaw());
	}

	/* Method to get current robot heading*/
	public double getGyroYaw() {
		return gyro.getYaw();
	}

	/**Reset the robots sensors to the zero states.*/
	public void reset() {
		gyro.reset();
	}
}
