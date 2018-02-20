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
    AnalogPotentiometer stringPotentiometer;
    public Elevator(){
        inputManager = Globals.im;
        elevatorMotor = new TalonSRX(RobotMap.ELEVATOR_1);
        elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        elevatorMotor.setSensorPhase(true);
        elevatorMotor.configNominalOutputForward(0, 0);
        elevatorMotor.configNominalOutputReverse(0, 0);
        elevatorMotor.configPeakOutputForward(1, 0);
        elevatorMotor.configPeakOutputReverse(-1, 0);
        elevatorMotor.configMotionCruiseVelocity(1000, 0);
        elevatorMotor.configMotionAcceleration(2000, 0);
        elevatorMotor.setSelectedSensorPosition(0, 0, 0);
        elevatorMotor.config_kP(0, Constants.ELEVATOR_KP, 0);
        elevatorMotor.config_kI(0, Constants.ELEVATOR_KI, 0);
        elevatorMotor.config_kD(0, Constants.ELEVATOR_KD, 0);
        elevatorFollower = new TalonSRX(RobotMap.ELEVATOR_2);
        elevatorFollower.set(ControlMode.Follower, RobotMap.ELEVATOR_1);
        //elevatorMotor.configPeakCurrentLimit(Constants.ELEVATOR_PEAK_CURRENT, 0);
        //stringPotentiometer = new AnalogPotentiometer(RobotMap.POTENTIOMETER,
            //    3.0 / 2.0 * Constants.POTENTIOMETER_STRING_LENGTH, Constants.POTENTIOMETER_STRING_OFFSET * 2.0 / 3);
    }
    public void start(){
        elevatorMotor.setSelectedSensorPosition(0, 0, 0);
    }
    @Override public void tick(){
        double targetHeight = inputManager.getElevator() * Constants.ELEVATOR_HEIGHT;
        /*double currentHeightEnc = elevatorMotor.getSelectedSensorPosition(0) / 4096.0 * 4 * Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
        double currentHeightPot = stringPotentiometer.get();
        Globals.ElevatorHeight.setDouble(currentHeightPot);
        if(Math.abs(currentHeightEnc - currentHeightPot) > 5){
            elevatorMotor.setSelectedSensorPosition((int)(currentHeightPot * 4096 / 3 / Constants.ELEVATOR_GEAR_CIRCUMFERENCE),
                    0, 0);
        }*/
        Globals.ElevatorHeight.setDouble(encToInches(elevatorMotor.getSelectedSensorPosition(0)));
        SmartDashboard.putNumber("Target Height", targetHeight);
        SmartDashboard.putNumber("Current", elevatorMotor.getOutputCurrent());
        if(inputManager.getElevatorSafety())
            if(inputManager.getElevator5())
                elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(5));
            else if(inputManager.getElevator70())
                elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(70));
            else
                elevatorMotor.set(ControlMode.MotionMagic, inchesToEnc(targetHeight));
        else elevatorMotor.set(ControlMode.PercentOutput, 0);
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
}
