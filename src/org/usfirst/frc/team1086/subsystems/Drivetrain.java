package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.EncoderManager;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;

public class Drivetrain {
	private static Drivetrain instance;

	public TalonSRX frontLeft, frontRight, backLeft, backRight;
	private InputManager im;
	public EncoderManager em;

	static {
		instance = new Drivetrain();
	}

	/**
	 * Initializer for the Drivetrain class.
	 */
	private Drivetrain() {
		frontLeft = new TalonSRX(RobotMap.DRIVE_LEFT_1);
		frontRight = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
		backLeft = new TalonSRX(RobotMap.DRIVE_LEFT_2);
		backRight = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		im = InputManager.getInstance();
		em = new EncoderManager();
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

	/**
	 * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
	 * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void driveMP(double left, double right, double turn){
		frontLeft.set(ControlMode.PercentOutput, left + turn);
		backLeft.set(ControlMode.PercentOutput, left + turn);
		frontRight.set(ControlMode.PercentOutput, right - turn);
		backRight.set(ControlMode.PercentOutput, right - turn);
	}

	public void logSmartDashboard(){
		em.logSmartDashboard();
	}
}