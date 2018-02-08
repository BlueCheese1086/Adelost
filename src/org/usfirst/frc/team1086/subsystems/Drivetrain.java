package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.EncoderManager;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.Gyro;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;
import org.usfirst.frc.team1086.robot.Utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;

public class Drivetrain {
	public TalonSRX frontLeft, frontRight, backLeft, backRight;
	private InputManager im;
	public EncoderManager em;
	private Gyro gyro;
	
	public PIDController driveStraightController;
	public PIDController turnToAngleController;
	
	/**
	 * Initializer for the Drivetrain class.
	 */
	public Drivetrain() {
		frontLeft = new TalonSRX(RobotMap.DRIVE_LEFT_1);
		frontRight = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
		backLeft = new TalonSRX(RobotMap.DRIVE_LEFT_2);
		backRight = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);	
	}

	public void init() {
		im = Globals.im;
		em = new EncoderManager();
		gyro = Globals.gyro;
		driveStraightController = new PIDController(0.02, 0, 0.075, gyro, d -> {}); 		
		driveStraightController.setAbsoluteTolerance(0);
		driveStraightController.setInputRange(-180, 180);
		driveStraightController.setOutputRange(-1, 1);
		driveStraightController.setContinuous(true);
		
		turnToAngleController = new PIDController(0.03, 0, 0.06, gyro, d -> {});
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
		frontLeft.set(ControlMode.PercentOutput, drive - turn);
		frontRight.set(ControlMode.PercentOutput, drive + turn);
		backLeft.set(ControlMode.PercentOutput, drive - turn);
		backRight.set(ControlMode.PercentOutput, drive + turn);
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
		gyro.logSmartDashbard();
	}
}