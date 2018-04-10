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

/**
 * Controls the elevator for the robot.
 * Elevator always moves towards its setpoint.
 * Uses motion magic to generate path to setpoint
 */
public class Elevator implements Tickable {
    InputManager inputManager;
    public TalonSRX elevatorMotor;
	TalonSRX elevatorFollower;
	double targetHeight;
    public Elevator(){
        inputManager = Globals.im;
        elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_1);

        //Configure the encoder
        elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        elevatorMotor.setSensorPhase(true);
        elevatorMotor.setSelectedSensorPosition(0, 0, 0);

        //Set output maximums
        elevatorMotor.configNominalOutputForward(0, 0);
        elevatorMotor.configNominalOutputReverse(0, 0);
        elevatorMotor.configPeakOutputForward(1, 0);
        elevatorMotor.configPeakOutputReverse(-1, 0);

        //Set maximum velocity and acceleration. Though our elevator motors are capable of it,
        //Never accelerate faster than gravity!!! This will cause the belts to slacken and possibly fall off their guides
        elevatorMotor.configMotionCruiseVelocity(3800, 0);
        elevatorMotor.configMotionAcceleration(4500, 0);

        //Configure the PID
        elevatorMotor.config_kP(0, Constants.ELEVATOR_KP, 0);
        elevatorMotor.config_kI(0, Constants.ELEVATOR_KI, 0);
        elevatorMotor.config_kD(0, Constants.ELEVATOR_KD, 0);
      
        //Set up the current limits
        elevatorMotor.configPeakCurrentDuration(1000, 0);
        elevatorMotor.configPeakCurrentLimit(Constants.ELEVATOR_PEAK_CURRENT, 0);
        elevatorMotor.configContinuousCurrentLimit(Constants.ELEVATOR_CONTINUOUS_CURRENT, 0);

        //Set up the other motor to follow the first
        elevatorFollower = new TalonSRX(RobotMap.ELEVATOR_2);
        elevatorFollower.set(ControlMode.Follower, RobotMap.ELEVATOR_1);
        elevatorFollower.configPeakCurrentDuration(1000, 0);
        elevatorFollower.configPeakCurrentLimit(Constants.ELEVATOR_PEAK_CURRENT, 0);
        elevatorFollower.configContinuousCurrentLimit(Constants.ELEVATOR_CONTINUOUS_CURRENT, 0);
    }
    public void start(){
        elevatorMotor.setSelectedSensorPosition(0, 0, 0);
    }
    public void reset(){
        targetHeight = 0;
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }
    @Override public void tick(){
        if(inputManager.getElevatorOverride()) {
            if (inputManager.getElevatorSafety()) {
                //If the driver has overriden the motion profiling and the safety is pressed, just move it with the driver's speed
                elevatorMotor.set(ControlMode.PercentOutput, inputManager.getElevator());
            }
        } else {
            if (inputManager.getElevatorSafety()) {
                //Update the setpoints if safety is enabled...
                if (inputManager.getElevatorGround())
                    targetHeight = 1;
                else if (inputManager.getElevatorScale())
                    targetHeight = 70;
                else if (inputManager.getElevatorSwitch())
                    targetHeight = 15;
                else
                    targetHeight += inputManager.getElevator() * Constants.ELEVATOR_HEIGHT / 50;
            }

            //Cap the target height
            targetHeight = Math.max(Math.min(targetHeight, Constants.ELEVATOR_HEIGHT), 0);

            //Move to the target height
            elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(targetHeight));
        }
    }
    public void set(double inches) {
    	elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(inches));
    }
    public double get() {
    	return encToInches(elevatorMotor.getSelectedSensorPosition(0));
    }
    public double inchesToEnc(double inches){
        return inches * 4096 / 3.0 / Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
    }
    public double encToInches(double enc){
        return enc / 4096.0 * 3 * Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
    }
    public double getElevatorHeight(){
        return encToInches(elevatorMotor.getSelectedSensorPosition(0));
    }
    public void log(){
        Globals.elevatorHeight.setDouble(encToInches(elevatorMotor.getSelectedSensorPosition(0)));
        Globals.elevatorCurrent.setNumber(elevatorMotor.getOutputCurrent() / 2 + elevatorFollower.getOutputCurrent() / 2);
        SmartDashboard.putNumber("Target Height", targetHeight);
        Globals.logger.print("General", "------------------ELEVATOR----------------");
        Globals.logger.print("Elevator Height", Globals.logger.format(encToInches(elevatorMotor.getSelectedSensorPosition(0))));
        Globals.logger.print("Elevator Target Height", Globals.logger.format(targetHeight));
        Globals.logger.print("Elevator Average Current", Globals.logger.format(elevatorMotor.getOutputCurrent() / 2 + elevatorFollower.getOutputCurrent() / 2));
    }
}
