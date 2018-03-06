package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;
import org.usfirst.frc.team1086.robot.Tickable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator implements Tickable {
	InputManager inputManager;
	public TalonSRX elevatorMotor;
	TalonSRX elevatorFollower;
	double targetHeight;

	public Elevator() {
		inputManager = Globals.im;
		elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_1);
		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		elevatorMotor.setSensorPhase(true);
		elevatorMotor.configNominalOutputForward(0, 0);
		elevatorMotor.configNominalOutputReverse(0, 0);
		elevatorMotor.configPeakOutputForward(1, 0);
		elevatorMotor.configPeakOutputReverse(-1, 0);
		elevatorMotor.configMotionCruiseVelocity(3800, 0);
		elevatorMotor.configMotionAcceleration(3500, 0);
		elevatorMotor.setSelectedSensorPosition(0, 0, 0);
		elevatorMotor.config_kP(0, Constants.ELEVATOR_KP, 0);
		elevatorMotor.config_kI(0, Constants.ELEVATOR_KI, 0);
		elevatorMotor.config_kD(0, Constants.ELEVATOR_KD, 0);
		elevatorMotor.configPeakCurrentLimit(20, 0);
		elevatorFollower = new TalonSRX(RobotMap.ELEVATOR_2);
		elevatorFollower.set(ControlMode.Follower, RobotMap.ELEVATOR_1);
		elevatorFollower.configPeakCurrentLimit(Constants.ELEVATOR_PEAK_CURRENT, 0);
	}

	public void start() {
		elevatorMotor.setSelectedSensorPosition(0, 0, 0);
	}

	public void reset() {
		targetHeight = 0;
	}

	@Override
	public void tick() {
		Globals.elevatorHeight.setDouble(encToInches(elevatorMotor.getSelectedSensorPosition(0)));
		Globals.elevatorCurrent
				.setNumber(elevatorMotor.getOutputCurrent() / 2 + elevatorFollower.getOutputCurrent() / 2);
		SmartDashboard.putNumber("Target Height", targetHeight);
		SmartDashboard.putNumber("Current 1", elevatorMotor.getOutputCurrent());
		SmartDashboard.putNumber("Current 2", elevatorFollower.getOutputCurrent());
		if (!inputManager.getPixy()) {
			if (inputManager.getElevatorOverride()) {
				if (inputManager.getElevatorSafety()) {
					elevatorMotor.set(ControlMode.PercentOutput, inputManager.getElevator());
				}
			} else {
				if (inputManager.getElevatorSafety()) {
					if (inputManager.getElevator5())
						targetHeight = 5;
					else if (inputManager.getElevator70())
						targetHeight = 70;
					else
						targetHeight += inputManager.getElevator() * Constants.ELEVATOR_HEIGHT / 50;
				}
			}
			targetHeight = Math.max(Math.min(targetHeight, Constants.ELEVATOR_HEIGHT), 0);
			elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(targetHeight));
		}
	}

	public void set(double inches) {
		elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(inches));
	}

	public double get() {
		return encToInches(elevatorMotor.getSelectedSensorPosition(0));
	}

	public double inchesToEnc(double inches) {
		return inches * 4096 / 3.0 / Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
	}

	public double encToInches(double enc) {
		return enc / 4096.0 * 3 * Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
	}

	public double getElevatorHeight() {
		return encToInches(elevatorMotor.getSelectedSensorPosition(0));
	}
}
