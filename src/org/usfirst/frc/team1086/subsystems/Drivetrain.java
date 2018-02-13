package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Drivetrain implements Tickable {
	public TalonSRX left1, right1, left2, right2;
	private InputManager im;
	public EncoderManager em;
	private Gyro gyro;

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
		left2.setInverted(true);
		left2.set(ControlMode.Follower, RobotMap.DRIVE_LEFT_1);
		right2.set(ControlMode.Follower, RobotMap.DRIVE_RIGHT_1);
	}

	public void init() {
		//This is where all of the NetworkTableEntries are initialized
		NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
		NetworkTable table = tableInstance.getTable("Telemetry");
		Globals.Heading = table.getEntry("Heading");
		Globals.Speed = table.getEntry("Speed");
		Globals.Acceleration = table.getEntry("Acceleration");
		Globals.Left1Output = table.getEntry("Left1Output");
		Globals.Right1Output = table.getEntry("Right1Output");
		Globals.Left2Output = table.getEntry("Left2Output");
		Globals.Right2Output = table.getEntry("Right2Output");
		Globals.ElevatorHeight = table.getEntry("ElevatorHeight");
		Globals.ArmLocation = table.getEntry("ArmLocation");
				
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
		turnToAngleController.setAbsoluteTolerance(2);
		turnToAngleController.setInputRange(-180, 180);
		turnToAngleController.setOutputRange(-1, 1);
		turnToAngleController.setContinuous(true);
		
		ultrasonicController = new PIDController(Constants.ULTRASONIC_KP, Constants.ULTRASONIC_KI,
												 Constants.ULTRASONIC_KD, gyro, d -> {});
		
	}
	
	@Override public void tick(){
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
			else if(im.getUltraSonicStart()) {
				ultrasonicController.setSetpoint(gyro.getNormalizedAngle() /*Value goes here*/);
				ultrasonicController.enable();
			}
			else if(im.getUltraSonicTick()) {
				drive(0 /*pid value of ultrasonic*/,0);
			}
			else if(im.getUltraSonicReleased()) {
				ultrasonicController.reset();
				ultrasonicController.disable();
			}
			else {
				drive(im.getDrive(), im.getTurn());
			}
		}
		else {
			if(!im.getMotionProfileTick())
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
		right1.set(ControlMode.PercentOutput, drive + turn);
		Globals.Left1Output.setDouble(left1.getMotorOutputPercent());
		Globals.Left2Output.setDouble(left2.getMotorOutputPercent());
		Globals.Right1Output.setDouble(right1.getMotorOutputPercent());
		Globals.Right2Output.setDouble(right2.getMotorOutputPercent());
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
	 * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void driveMP(double left, double right, double turn){
		left1.set(ControlMode.PercentOutput, left + turn);
		right1.set(ControlMode.PercentOutput, right - turn);
		Globals.Left1Output.setDouble(left1.getMotorOutputPercent());
		Globals.Left2Output.setDouble(left2.getMotorOutputPercent());
		Globals.Right1Output.setDouble(right1.getMotorOutputPercent());
		Globals.Right2Output.setDouble(right2.getMotorOutputPercent());
	}

	public void logSmartDashboard(){
		em.logSmartDashboard();
		gyro.logSmartDashbard();
	}
	
}