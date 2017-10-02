package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4541.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick joy = new Joystick(0);


	public OI() {
		// Put Some buttons on the SmartDashboard
		
		SmartDashboard.putData("Drive to Pin", new DriveToTarget(0, 23, 0));
		SmartDashboard.putData("Toggle Straigt Drive", new DriveStraight(0));
		SmartDashboard.putData("Toggle Doors", new ToggleDoor());
		SmartDashboard.putData("Toggle Flap", new ToggleFlap());
		SmartDashboard.putData("Reset Gyro", new ResetGyro());
		SmartDashboard.putData("Test Vision", new TestVision());

		// Create some buttons
		//JoystickButton button_1 = new JoystickButton(joy, 1);
		JoystickButton button_2 = new JoystickButton(joy, 2);
		JoystickButton button_3 = new JoystickButton(joy, 3);
		JoystickButton button_4 = new JoystickButton(joy, 4);
		JoystickButton Button_5 = new JoystickButton(joy, 5);
		JoystickButton button_6 = new JoystickButton(joy, 6);
		JoystickButton button_7 = new JoystickButton(joy, 7);
		//JoystickButton button_8 = new JoystickButton(joy, 8);

		// Connect the buttons to commands
		//button_1.whenPressed(new DriveToTarget(0,12,0));
		button_2.whileHeld(new RunClimber());
		button_3.whenPressed(new ToggleFlap());
		button_4.whenPressed(new ToggleDoor());
		Button_5.whenPressed(new ResetGyro());
		button_6.toggleWhenPressed(new DriveStraight(0));
		button_7.toggleWhenPressed(new TestVision());
	}

	public Joystick getJoystick() {
		return joy;
	}
	
	// modifies the input of a joystick axis by adding dead zones and squaring
	// the inputs, intended to be used with XBOX controllers or other
	// controllers with many predefined axes
	public double modStickIn(int num) {
		double joyIn = joy.getRawAxis(num);
		if (joyIn <= .05 && joyIn >= 0)
			joyIn = 0;
		else if (joyIn >= -.05 && joyIn <= 0)
			joyIn = 0;
		else if (joyIn < 0)
			joyIn = -Math.pow(joyIn, 2);
		else
			joyIn = Math.pow(joyIn, 2);
		return joyIn;
	}
}
