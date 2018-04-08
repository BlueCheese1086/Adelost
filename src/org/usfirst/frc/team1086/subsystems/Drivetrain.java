package org.usfirst.frc.team1086.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.EncoderManager;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.Gyro;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;
import org.usfirst.frc.team1086.robot.Tickable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Controls the drivetrain of the robot
 */
public class Drivetrain implements Tickable {
	public TalonSRX left1, right1, left2, right2;
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
		right1 = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
		left2 = new TalonSRX(RobotMap.DRIVE_LEFT_2);
		right2 = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
		left1.setInverted(true);
		left1.setInverted(true);

		//Never set the secondary motors... they will not follow the first motor
		left2.set(ControlMode.Follower, RobotMap.DRIVE_LEFT_1);
		right2.set(ControlMode.Follower, RobotMap.DRIVE_RIGHT_1);
	}

	public void init() {
		im = Globals.im;
		em = new EncoderManager();
		gyro = Globals.gyro;

		//This PID is used to make the robot drive perfectly straight.
		driveStraightController = new PIDController(Constants.DRIVE_STRAIGHT_KP, Constants.DRIVE_STRAIGHT_KI,
													Constants.DRIVE_STRAIGHT_KD, gyro, d -> {});
		driveStraightController.setAbsoluteTolerance(0);
		driveStraightController.setInputRange(-180, 180);
		driveStraightController.setOutputRange(-1, 1);
		driveStraightController.setContinuous(true);

		//This is the PID used to make the robot turn to a specified angle
		turnToAngleController = new PIDController(Constants.TURN_TO_ANGLE_KP, Constants.TURN_TO_ANGLE_KI,
												  Constants.TURN_TO_ANGLE_KD, gyro, d -> {});
		turnToAngleController.setAbsoluteTolerance(1);
		turnToAngleController.setInputRange(-180, 180);
		turnToAngleController.setOutputRange(-1, 1);
		turnToAngleController.setContinuous(true);		
	}
	
	@Override public void tick(){
		if(im.getSafety())
			//turn is more complicated, so it was pulled to a separate method
			drive(im.getDrive(), getTurn());
		else
			//Disable the driving straight PID when the safety isn't engaged
			driveStraightController.disable();
	}

	/**
	 * Applies power to the Drivetrain motors to move the robot.
	 * @param drive - the power to send to move forwards and backwards. 1 is full speed forwards, -1 is full speed backwards
	 * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
	 */
	public void drive(double drive, double turn) {
		left1.set(ControlMode.PercentOutput, drive + turn);
		right1.set(ControlMode.PercentOutput, drive - turn);
	}
	public double getTurn(){
		//If safety isn't engage, disable PID and don't turn
		if(!im.getSafety()){
			driveStraightController.disable();
			return 0;
		}

		//If we're turning to a certain angle, stop the driving straight (they'll fight) and turn properly
		else if(turnToAngleController.isEnabled()) {
			driveStraightController.disable();
			return turnToAngleController.get();
		}

		//If the driver is turning, follow the driver's instructions
		else if(Math.abs(im.getTurn()) > 0.01) {
			driveStraightController.disable();
			return im.getTurn();
		}

		//If none of the above is true and the drive straight controller isn't enabled, enable it
		else if(!driveStraightController.isEnabled()){
			driveStraightController.setSetpoint(gyro.getNormalizedAngle());
			driveStraightController.enable();
		}

		//If the driver has overriden drive straight, drive according to the driver's turn. Otherwise, drive straight
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

	public void log(){
		Globals.left1Output.setDouble(left1.getOutputCurrent());
		Globals.left2Output.setDouble(left2.getOutputCurrent());
		Globals.right1Output.setDouble(right1.getOutputCurrent());
		Globals.right2Output.setDouble(right2.getOutputCurrent());
		Globals.logger.print("General", "-----------------DRIVETRAIN-------------------");
		Globals.logger.print("Drive L1 Current", Globals.logger.format(left1.getOutputCurrent()));
		Globals.logger.print("Drive L2 Current", Globals.logger.format(left2.getOutputCurrent()));
		Globals.logger.print("Drive R1 Current", Globals.logger.format(right1.getOutputCurrent()));
		Globals.logger.print("Drive R2 Current", Globals.logger.format(right2.getOutputCurrent()));
	}
}