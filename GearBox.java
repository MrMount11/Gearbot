package org.usfirst.frc.team4541.robot.subsystems;
import org.usfirst.frc.team4541.robot.Robot;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The gearbox system uses two motors to open and close the flap and main doors.  Limit switches provide the state of both doors systems 
 * If using stronger motors, you should probably use a sensor so that the motors don't stall.*/
public class GearBox extends Subsystem {
	
	private CANTalon flapMotor;
	private CANTalon doorMotor;

	public GearBox() {
		super();
		flapMotor = new CANTalon(9);
		flapMotor.clearStickyFaults();
		flapMotor.enableLimitSwitch(true,true);
		flapMotor.ConfigFwdLimitSwitchNormallyOpen(false);
		flapMotor.ConfigRevLimitSwitchNormallyOpen(false);

		doorMotor = new CANTalon(8);    
		doorMotor.clearStickyFaults();
		doorMotor.enableLimitSwitch(true,true);
		doorMotor.ConfigFwdLimitSwitchNormallyOpen(false);
		doorMotor.ConfigRevLimitSwitchNormallyOpen(false);
		
		// Let's show everything on the LiveWindow and write some stuff to log file
		LiveWindow.addActuator("Gear System", "Flap Motor", (CANTalon) flapMotor);
		LiveWindow.addActuator("Gear System", "Door Motor", (CANTalon) doorMotor);
		//LiveWindow.addSensor("Gear System" , "Flap", flapMotor.isFwdLimitSwitchClosed());
		System.out.println("Gear System is Alive");
		
	}

	@Override
	public void initDefaultCommand() {
	}

	public void log() {
		SmartDashboard.putBoolean("FLAP OPEN", isFlapOpen());
		SmartDashboard.putBoolean("DOORS OPEN", isDoorsOpen());
	}

	public void openFlap() { // Open the flap door
		flapMotor.set(.5);
	}
	public void closeFlap() {  //Close the flap door
		flapMotor.set(-.5);
	}
	public void stopFlap() {  //Stop the flap door motor
		flapMotor.set(0);
	}
	public void openDoors() {  // Open the main doors
		doorMotor.set(-1);
	}
	public void closeDoors() {  //Close the main doors
		doorMotor.set(1);
	}
	public void stopDoors() {  //Stops te main door motor
		doorMotor.set(0);
	}

	/**
	 * Return true when the associated limit switches are closed.
	 */
	public boolean isFlapOpen() {
		if (flapMotor.isRevLimitSwitchClosed()){
			return true;
		}
		return false;
	}
	public boolean isFlapClosed() {
		return flapMotor.isFwdLimitSwitchClosed();
	}
	public boolean isDoorsOpen() {
		return flapMotor.isFwdLimitSwitchClosed();
	}
	public boolean isDoorsClosed() {
		return flapMotor.isRevLimitSwitchClosed();
	}
}
