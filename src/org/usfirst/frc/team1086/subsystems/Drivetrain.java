package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.usfirst.frc.team1086.robot.RobotMap;

public class Drivetrain {
	private TalonSRX frontLeft, frontRight, backLeft, backRight;

	/**
	 * Initializer for the Drivetrain class.
	 */
	public Drivetrain() {
		frontLeft = new TalonSRX(RobotMap.DRIVE_FRONT_LEFT);
		frontRight = new TalonSRX(RobotMap.DRIVE_FRONT_RIGHT);
		backLeft = new TalonSRX(RobotMap.DRIVE_BACK_LEFT);
		backRight = new TalonSRX(RobotMap.DRIVE_BACK_RIGHT);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot.
	 * @param drive - the power to send to move forwards and backwards. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void drive(double drive, double turn) {
		frontLeft.set(ControlMode.PercentOutput, drive + turn);
		frontRight.set(ControlMode.PercentOutput, drive - turn);
		backLeft.set(ControlMode.PercentOutput, drive + turn);
		backRight.set(ControlMode.PercentOutput, drive - turn);
	}
}