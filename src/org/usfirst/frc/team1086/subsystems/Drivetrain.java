package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;

public class Drivetrain {
	private static Drivetrain instance;

	public TalonSRX frontLeft, frontRight, backLeft, backRight;
	private InputManager im;

	static {
		instance = new Drivetrain();
	}

	/**
	 * Initializer for the Drivetrain class.
	 */
	private Drivetrain() {
		frontLeft = new TalonSRX(RobotMap.DRIVE_FRONT_LEFT);
		frontRight = new TalonSRX(RobotMap.DRIVE_FRONT_RIGHT);
		backLeft = new TalonSRX(RobotMap.DRIVE_BACK_LEFT);
		backRight = new TalonSRX(RobotMap.DRIVE_BACK_RIGHT);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		im = InputManager.getInstance();
	}

	public static Drivetrain getInstance(){
		return instance;
	}

	public void teleopTick(){
		if(im.getSafety()){
			drive(im.getDrive(), im.getTurn());
		}
		else {
			drive(0, 0);
		}
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

	public void driveMP(double left, double right, double turn){
		frontLeft.set(ControlMode.PercentOutput, left + turn);
		backLeft.set(ControlMode.PercentOutput, left + turn);
		frontRight.set(ControlMode.PercentOutput, right - turn);
		backRight.set(ControlMode.PercentOutput, right - turn);
	}

	public void resetEncoders(){
		frontLeft.setSelectedSensorPosition(0, 0, 0);
		frontRight.setSelectedSensorPosition(0, 0, 0);
	}

	public double getLeftDistance(){
		return frontLeft.getSelectedSensorPosition(0) / 4096 * Constants.WHEEL_DIAMETER;
	}

	public double getRightDistance(){
		return frontRight.getSelectedSensorPosition(0) / 4096 * Constants.WHEEL_DIAMETER;
	}

	public double getEncDistance(){
		return (getLeftDistance() + getRightDistance()) / 2.0;
	}

	public void logSmartDashboard(){
		SmartDashboard.putNumber("Encoder Left Distance", getLeftDistance());
		SmartDashboard.putNumber("Encoder Right Distance", getRightDistance());
		SmartDashboard.putNumber("Encoder Distance", getEncDistance());
	}
}