package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class XYRController implements PIDOutput, PIDSource {
	/*  Establish a PID controller for axis of motion*/
	public PIDController driveController;
	public double motorSpeed;
	public double locInput;
	double P1,I1,D1,FF,P2,I2,D2;

	static final double kTolerance = 1.0f;
	protected PIDSourceType m_pidSource = PIDSourceType.kDisplacement;

	public XYRController(double p1, double i1, double d1, double ff, double p2, double i2, double d2) {

		/* Set passed PID coefficients for controllers*/
		P1=p1; I1=i1; D1=d1; FF=ff;
		P2=p2; I2=i2; D2=d2;

		/* Set output range, input range and tolerance for controllers*/
		driveController = new PIDController(P1, I1, D1, FF, this, this);
		driveController.setOutputRange(-1.0, 1.0);
		driveController.setAbsoluteTolerance(kTolerance);
		driveController.setContinuous(false);
	}

	public void setMode(int mode) {
		//this method changes mode between coarse and fine
		if (mode == 0)
			this.driveController.setPID(P1, I1, D1, FF);
		if (mode == 1)
			this.driveController.setPID(P2,I2,D2,FF);
		if (!this.driveController.isEnabled())
			this.driveController.enable();
		return;		
	}
	/*public void updatePID(double p1 double) {
		P1 = SmartDashboard.getNumber("P1: ", turnController.getP());
		I1 = SmartDashboard.getNumber("I1: ", turnController.getI());
		D1 = SmartDashboard.getNumber("D1: ", turnController.getD());
		
		P2 = SmartDashboard.getNumber("P2: ", turnController.getP());
		I2 = SmartDashboard.getNumber("I2: ", turnController.getI());
		D2 = SmartDashboard.getNumber("D2: ", turnController.getD());
	}*/
	
	
	public void setInput(double source){
		// This method is called to pass the current robot X,Y or R location to the specific PID controller
		locInput = source;
	}

	@Override
	//This method is called periodically by the PID controller to set the output
	public void pidWrite(double output) {
		motorSpeed = output;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_pidSource = pidSource;			
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return m_pidSource;
	}

	@Override
	// Method is called periodically by the PID controller to get input
	public double pidGet() {
		return locInput;
	}
}