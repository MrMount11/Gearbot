package org.usfirst.frc.team4541.robot;

import edu.wpi.first.wpilibj.I2C;

/* This class handles the I2C communication between arduino and rio
 * It has several utilities for processing the ultrasonic data
 * The register map in the Ardunio is:
 * 
 *   Address     |    Register Description
 *  
 *  0x00        |   Status  
 *  0x01        |   Ultrasonic Sensor #1 Time Pulse Width (uS) - MSB
 *  0x02        |   Ultrasonic Sensor #1 Time Pulse Width (uS)
 *  0x03        |   Ultrasonic Sensor #1 Time Pulse Width (uS) 
 *  0x04        |   Ultrasonic Sensor #1 Time Pulse Width (uS) - LSB
 *  0x05        |   Ultrasonic Sensor #1 Time Pulse Width (uS) - MSB
 *  0x06        |   Ultrasonic Sensor #1 Time Pulse Width (uS) 
 *  0x07        |   Ultrasonic Sensor #1 Time Pulse Width (uS) 
 *  0x08        |   Ultrasonic Sensor #1 Time Pulse Width (uS) - LSB 
 *  0x09        |   Mode // not used
 *  0x0A        |   Configuration  //not used in this sketch
 *  0x0B        |   Device Identification = 0x0D
*/
public class UltraSonicUtiliy {
	private static I2C i2c;
	
	public static void init() {
		i2c = new I2C(I2C.Port.kOnboard, 4);
	}
	
	public static double getUltrasonic(SIDE side) {
		try{
			byte[] sendData = {getSensorReg(side)};
			byte[] recievedData = {0x00, 0x00, 0x00, 0x00};
			if (!i2c.transaction(sendData, sendData.length, recievedData, 4)){
				//	Timer.delay(.01);
				long distance = (recievedData[0] & 0xFF) << 24;
				distance = distance | ((recievedData[1] & 0xFF) << 16 );
				distance = distance | ((recievedData[2] & 0xFF) << 8 );
				distance = distance | ((recievedData[3] & 0xFF));
				double distanceFloat = (double) (distance);
				distanceFloat = Math.round(distance/149/10);
				return distanceFloat;
			}
			else return 0;
		}
		catch(NullPointerException e){
			System.out.println(e.getMessage());
			return -1;
		}
	}
	public static boolean isDueOnline() {
		byte[] byteArr = {(byte) 5};
		byte[] recievedData = {0x00, 0x00};
		i2c.transaction(byteArr, byteArr.length, recievedData, recievedData.length);
		System.out.println(recievedData[0]);
		return recievedData[0] == -1 && recievedData[1] == 0;
	}


	//	public static void resetDue() {	
	//		int portHandle = RelayJNI.getPort((byte) 0);
	//		int forwardHandle = RelayJNI.initializeRelayPort(portHandle, true);
	//
	//		if (isDueOnline()) {
	//			RelayJNI.setRelay(forwardHandle, true);
	//		} else {
	//			RelayJNI.setRelay(forwardHandle, false);
	//			Timer.delay(0.1);
	//			RelayJNI.setRelay(forwardHandle, true);
	//		}
	
	
	public static byte getSensorReg(SIDE side) {
		switch (side) {
		case FRONT: 
			return 0x01;
		case BACK: 
			return 0x05;
		case LEFT: 
			return 0x00;
		case RIGHT: 
			return 0x00;
		default: 
			return -1;
		}
	}
	public static int getSensorNum(SIDE side) {
		switch (side) {
		case FRONT: 
			return 0;
		case BACK: 
			return 2;
		case LEFT: 
			return 3;
		case RIGHT: 
			return 1;
		default: 
			return -1;
		}
	}
}

