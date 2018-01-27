package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drivetrain {
	private TalonSRX frontLeft, frontRight, backLeft, backRight;
	public static final char frontLeftInverted = 1 << 0, frontRightInverted = 1 << 1, backLeftInverted = 1 << 2, backRightInverted = 1 << 3;
	/**
	 * Initializer for the Drivetrain class.
	 * @param	frontLeftID		the CAN ID of the front left talon
	 * @param	frontRightID	the CAN ID of the front right talon
	 * @param	backLeftID		the CAN ID of the back left talon
	 * @param	backRightID		the CAN ID of the back right talon
	 * @param	invertedFlags	the inverted talons' flags OR'd together
	 */
	public Drivetrain(int frontLeftID, int frontRightID, int backLeftID, int backRightID, char invertedFlags) {
		frontLeft = new TalonSRX(frontLeftID);
		frontRight = new TalonSRX(frontRightID);
		backLeft = new TalonSRX(backLeftID);
		backRight = new TalonSRX(backRightID);
		frontLeft.setInverted((invertedFlags & frontLeftInverted) != 0);
		frontRight.setInverted((invertedFlags & frontRightInverted) != 0);
		backLeft.setInverted((invertedFlags & backLeftInverted) != 0);
		backRight.setInverted((invertedFlags & backRightInverted) != 0);
	}
	
	public void UserControl(double leftY, double rightX) {
		frontLeft.set(ControlMode.PercentOutput, -leftY - rightX);
		frontRight.set(ControlMode.PercentOutput, -leftY + rightX);
		backLeft.set(ControlMode.PercentOutput, leftY - rightX);
		backRight.set(ControlMode.PercentOutput, leftY + rightX);
	}
}