package org.usfirst.frc.team4541.robot;


import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotLocation {
	// variable definitions
	private static double ylocation_field; //location relative to the field (distance to the wall)
	private static double xlocation_field; //location relative to the field (horizontal displacement from the peg)
	public static double ylocation_robot; //location displacement relative to robot (distance travel distance for robot)
	public static double xlocation_robot; //location displacement relative to robot (x travel distance for robot)
	public static double rlocation_robot; //angular displacement relative to the robot (r rotation for robot)
	
	// define constants used in calculations
	static final boolean isCameraInverted = false;  // TRUE if target camera is inverted
	static final double targetwidth=8.25;  //Target Center to Center Distance from FRC field configuration
	static final double cva = 68.5;  //Camera viewing angle from camera specifications
	static final double pix_range = 680;  // Pixel count of camera viewing field
	static final int[] stationdeg = {-60, -90, -120};
	
	private static double relativeStationAngle = 0; //current robot yaw angle to wall
	private static double gyroinput;  // place for gyro yaw angle
	private static double pix_centerx1 = 0;  // Pixel value of target 1 center
	private static double pix_centerx2= 0;  //Pixel value of target 2 center
	
	private static NetworkTable gripTable;
	private static int peg= 0;
	
	//Constructor Method
	public RobotLocation() {
	}			
	
	public static boolean getVision() { 
		gripTable=NetworkTable.getTable("GRIP/myContoursReport");
		double[] filler = new double[]{0,0,0};
		double[] centerxs = gripTable.getNumberArray("centerX", filler);
		
		
		//  Do some error checks and validation of the data before returning 
		
		if(centerxs.length > 2 || centerxs.length < 2 ){ //determine if proper number of targets are being seen
			return false;
		}
		if (centerxs[0] == 0 || centerxs[1] == 0){ //determine if null targets have been returned
			return false;
		}
		if (centerxs[0] == centerxs[1]){ //determine if targets ??
			return false;
		}
		if (isCameraInverted){
			pix_centerx1 = pix_range - centerxs[0];
			pix_centerx2 = pix_range - centerxs[1];
		}
		else {
			pix_centerx1 = centerxs[0]; //write values for the first targets center x
			pix_centerx2 = centerxs[1]; //write values for second target center x
		}
		SmartDashboard.putNumber("Target 1 Center", pix_centerx1);
		SmartDashboard.putNumber("Target 2 Center", pix_centerx2);
		return true;
		}
	
	public static boolean getRobotLocation(int station) {
		SmartDashboard.putBoolean("GetVision Working", false);
		if (getVision()){
			SmartDashboard.putBoolean("GetVision Working", true);
			gyroinput= Robot.drivetrain.getGyroYaw();  // positive angles = clockwise
			if (station == 0)
				station = getStation(gyroinput);
			relativeStationAngle = gyroinput - stationdeg[station-1] -90 ;

			double ax1 = Math.atan(Math.tan(((cva/2)*Math.PI)/180)/(pix_range/2)*(pix_centerx1-pix_range/2))*180/Math.PI;
			double ax2 = Math.atan(Math.tan((cva/2)*Math.PI/180)/(pix_range/2)*(pix_centerx2-pix_range/2))*180/Math.PI;
			ylocation_field=targetwidth/(Math.tan((ax2+relativeStationAngle)*Math.PI/180)-Math.tan((ax1+relativeStationAngle)*Math.PI/180));
			xlocation_field=ylocation_field*Math.tan((ax2+relativeStationAngle)*Math.PI/180)-targetwidth/2;
			double axc = Math.atan(xlocation_field/ylocation_field)*180/Math.PI-relativeStationAngle;
			xlocation_robot = Math.sin(axc*Math.PI/180)* Math.sqrt(Math.pow(xlocation_field, 2) + Math.pow(ylocation_field, 2));
			ylocation_robot = Math.cos(axc*Math.PI/180)* Math.sqrt(Math.pow(xlocation_field, 2) + Math.pow(ylocation_field, 2));
			rlocation_robot = relativeStationAngle;
			return true;
		}
		else return false;
	}
	
	static int getStation(double angle)
	// positive angles clockwise
	{
		if(angle > 30)
			peg = 1;
		else if(angle < -30)
			peg = 3;
		else peg =2;
		//peg = (int) SmartDashboard.getNumber("Station",peg);
		return peg;
	}
}