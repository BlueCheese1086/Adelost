package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import org.usfirst.frc.team1086.robot.*;

public class Elevator implements Tickable {
    InputManager inputManager;
    TalonSRX elevatorMotor, elevatorFollower;
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
        elevatorMotor.configMotionCruiseVelocity(15000, 0);
        elevatorMotor.configMotionAcceleration(6000, 0);
        elevatorMotor.setSelectedSensorPosition(0, 0, 0);

        elevatorFollower = new TalonSRX(RobotMap.ELEVATOR_2);
        elevatorFollower.set(ControlMode.Follower, RobotMap.ELEVATOR_1);

        stringPotentiometer = new AnalogPotentiometer(RobotMap.POTENTIOMETER,
                3.0 / 2.0 * Constants.POTENTIOMETER_STRING_LENGTH, Constants.POTENTIOMETER_STRING_OFFSET * 2.0 / 3);
    }
    @Override public void tick(){
        double targetHeight = inputManager.getElevator() * Constants.ELEVATOR_HEIGHT;
        double currentHeightEnc = elevatorMotor.getSelectedSensorPosition(0) / 4096.0 * 3 * Constants.ELEVATOR_GEAR_CIRCUMFERENCE;
        double currentHeightPot = stringPotentiometer.get();
        if(Math.abs(currentHeightEnc - currentHeightPot) > 5){
            elevatorMotor.setSelectedSensorPosition((int)(currentHeightPot * 4096 / 3 / Constants.ELEVATOR_GEAR_CIRCUMFERENCE),
                    0, 0);
        }
        elevatorMotor.set(ControlMode.MotionMagic, targetHeight);
    }
}
