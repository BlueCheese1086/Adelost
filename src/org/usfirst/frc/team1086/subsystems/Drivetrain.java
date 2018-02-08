package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;

public class Drivetrain {
	public TalonSRX left1, front1, left2, right2;
	private InputManager im;
	public EncoderManager em;
	private Gyro gyro;
	
	public PIDController driveStraightController;
	public PIDController turnToAngleController;
	
	/**
	 * Initializer for the Drivetrain class.
	 */
	public Drivetrain() {
		left1 = new TalonSRX(RobotMap.DRIVE_LEFT_1);
		front1 = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
		left2 = new TalonSRX(RobotMap.DRIVE_LEFT_2);
		right2 = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
		left1.setInverted(true);
		left2.setInverted(true);
		left2.set(ControlMode.Follower, RobotMap.DRIVE_LEFT_1);
		right2.set(ControlMode.Follower, RobotMap.DRIVE_RIGHT_2);
	}

	public void init() {
		im = Globals.im;
		em = new EncoderManager();
		gyro = Globals.gyro;
		driveStraightController = new PIDController(Constants.DRIVE_STRAIGHT_KP, Constants.DRIVE_STRAIGHT_KI,
													Constants.DRIVE_STRAIGHT_KD, gyro, d -> {});
		driveStraightController.setAbsoluteTolerance(0);
		driveStraightController.setInputRange(-180, 180);
		driveStraightController.setOutputRange(-1, 1);
		driveStraightController.setContinuous(true);
		
		turnToAngleController = new PIDController(Constants.TURN_TO_ANGLE_KP, Constants.TURN_TO_ANGLE_KI,
												  Constants.TURN_TO_ANGLE_KD, gyro, d -> {});
		turnToAngleController.setAbsoluteTolerance(0);
		turnToAngleController.setInputRange(-180, 180);
		turnToAngleController.setOutputRange(-1, 1);
		turnToAngleController.setContinuous(true);
	}
	
	public void teleopTick(){
		if(im.getSafety()){
			if(im.getEncodersDriveStart()) {
				em.setPosition(50);
			}
			else if(im.getEncodersDriveTick()) {
				//Set position uses Position mode on TalonSRX, so the robot is already moving.
			}
			else if(im.getDriveStraightStart()) {
				driveStraightController.setSetpoint(gyro.getNormalizedAngle());
				driveStraightController.enable();
			}
			else if(im.getDriveStraightTick()) {
				drive(im.getDrive(), driveStraightController.get());
			}
			else if(im.getDriveStraightRelease()) {
				driveStraightController.reset();
				driveStraightController.disable();
			}
			else if(im.getTurnToAngleStart()) {
				turnToAngleController.setSetpoint(Utils.normalizeAngle(gyro.getNormalizedAngle() + 90));
				turnToAngleController.enable();
			}
			else if(im.getTurnToAngleTick()) {
				drive(0, turnToAngleController.get());
			}
			else if(im.getTurnToAngleRelease()) {
				turnToAngleController.reset();
				turnToAngleController.disable();
			}
			else
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
		left1.set(ControlMode.PercentOutput, drive - turn);
		front1.set(ControlMode.PercentOutput, drive + turn);
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
	 * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void driveMP(double left, double right, double turn){
		left1.set(ControlMode.PercentOutput, left + turn);
		front1.set(ControlMode.PercentOutput, right - turn);
	}

	public void logSmartDashboard(){
		em.logSmartDashboard();
		gyro.logSmartDashbard();
	}
}