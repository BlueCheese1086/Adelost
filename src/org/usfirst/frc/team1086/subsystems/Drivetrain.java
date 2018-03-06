package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;

public class Drivetrain implements Tickable {
	public TalonSRX left1, right1, left2, right2;
	private InputManager im;
	public EncoderManager em;
	private Gyro gyro;
	private Ultrasonic ultrasonic;
	public PIDController driveStraightController;
	public PIDController turnToAngleController;
	public PIDController ultrasonicController;
	
	/**
	 * Initializer for the Drivetrain class.
	 */
	public Drivetrain() {
		left1 = new TalonSRX(RobotMap.DRIVE_LEFT_1);
		right1 = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
		left2 = new TalonSRX(RobotMap.DRIVE_LEFT_2);
		right2 = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
		left1.setInverted(true);
		left1.setInverted(true);
		left2.set(ControlMode.Follower, RobotMap.DRIVE_LEFT_1);
		right2.set(ControlMode.Follower, RobotMap.DRIVE_RIGHT_1);
	}

	public void init() {
		im = Globals.im;
		em = new EncoderManager();
		gyro = Globals.gyro;
		ultrasonic = Globals.ultrasonic;
		driveStraightController = new PIDController(Constants.DRIVE_STRAIGHT_KP, Constants.DRIVE_STRAIGHT_KI,
													Constants.DRIVE_STRAIGHT_KD, gyro, d -> {});
		driveStraightController.setAbsoluteTolerance(0);
		driveStraightController.setInputRange(-180, 180);
		driveStraightController.setOutputRange(-1, 1);
		driveStraightController.setContinuous(true);
		
		turnToAngleController = new PIDController(Constants.TURN_TO_ANGLE_KP, Constants.TURN_TO_ANGLE_KI,
												  Constants.TURN_TO_ANGLE_KD, gyro, d -> {});
		turnToAngleController.setAbsoluteTolerance(1);
		turnToAngleController.setInputRange(-180, 180);
		turnToAngleController.setOutputRange(-1, 1);
		turnToAngleController.setContinuous(true);
		
		ultrasonicController = new PIDController(Constants.ULTRASONIC_KP, Constants.ULTRASONIC_KI,
												 Constants.ULTRASONIC_KD, ultrasonic, d -> {});
		
		ultrasonicController.setAbsoluteTolerance(1);
		ultrasonicController.setInputRange(0, 100);
		ultrasonicController.setOutputRange(0, 1);
		
	}
	
	@Override public void tick(){
		if(im.getSafety())
			drive(im.getDrive(), getTurn());
		else
			driveStraightController.disable();
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot.
	 * @param drive - the power to send to move forwards and backwards. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void drive(double drive, double turn) {
		if (!im.getPixy())
		left1.set(ControlMode.PercentOutput, drive + turn);
		right1.set(ControlMode.PercentOutput, drive - turn);
	}
	public double getTurn(){
		if(!im.getSafety()){
			driveStraightController.disable();
			return 0;
		}
		else if(turnToAngleController.isEnabled()) {
			driveStraightController.disable();
			return turnToAngleController.get();
		}
		else if(Math.abs(im.getTurn()) > 0.01) {
			driveStraightController.disable();
			return im.getTurn();
		}
		else if(!driveStraightController.isEnabled()){
			driveStraightController.setSetpoint(gyro.getNormalizedAngle());
			driveStraightController.enable();
		}
		return im.getDriveStraightOverride() ? im.getTurn() : driveStraightController.get();
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
	 * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void driveMP(double left, double right, double turn){
		left1.set(ControlMode.PercentOutput, left - turn);
		right1.set(ControlMode.PercentOutput, right + turn);
	}

	public void logSmartDashboard(){
		em.logSmartDashboard();
		gyro.logSmartDashbard();
		Globals.left1Output.setDouble(left1.getMotorOutputPercent());
		Globals.left2Output.setDouble(left2.getMotorOutputPercent());
		Globals.right1Output.setDouble(right1.getMotorOutputPercent());
		Globals.right2Output.setDouble(right2.getMotorOutputPercent());
	}
	
}