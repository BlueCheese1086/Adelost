package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.robot.*;

public class Arm implements Tickable {
    InputManager inputManager;
    public TalonSRX armMotor;
    double armPos = 0;
    public Arm() {
        inputManager = Globals.im;
        armMotor = new TalonSRX(RobotMap.ARM);
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        armMotor.configNominalOutputForward(0, 0);
        armMotor.configNominalOutputReverse(0, 0);
        armMotor.configPeakOutputForward(1, 0);
        armMotor.configPeakOutputReverse(-1, 0);
        armMotor.setSelectedSensorPosition(0, 0, 0);
        armMotor.config_kP(0, 3, 0);
        armMotor.config_kI(0, 0, 0);
        armMotor.config_kD(0, 1, 0);
        armMotor.config_kF(0, 0, 0);
        armMotor.configMotionCruiseVelocity(1000, 0);
        armMotor.configMotionAcceleration(2000, 0);
        armMotor.configPeakCurrentLimit(Constants.ARM_PEAK_CURRENT,0);
        armMotor.setInverted(true);
    }
    public void reset(){
        armPos = 0;
    }
    @Override public void tick(){
        if(inputManager.manualArm() != 0)
            armMotor.set(ControlMode.PercentOutput, inputManager.manualArm());
        else {
            armPos += inputManager.getDeltaArm() * 2;
            armPos = Math.max(Math.min(armPos, Constants.MAX_ARM_ANGLE), 0);
            System.out.println(armPos);
            armMotor.set(ControlMode.MotionMagic, (1 - armPos / Constants.MAX_ARM_ANGLE) * Constants.MAX_ARM_ENC_UNITS);
        }

        Globals.armLocation.setNumber(getArmPosition());
        SmartDashboard.putNumber("Raw Arm Encoder", armMotor.getSelectedSensorPosition(0));
        Globals.armCurrent.setNumber(armMotor.getOutputCurrent());
    }
    public void setArmPosition(double angle) {
        armMotor.set(ControlMode.MotionMagic, (1 - angle / Constants.MAX_ARM_ANGLE) * Constants.MAX_ARM_ENC_UNITS);
        armPos = angle;
    }
    public double getArmPosition() {
		return Constants.MAX_ARM_ANGLE * (1 - (double)armMotor.getSelectedSensorPosition(0) / Constants.MAX_ARM_ENC_UNITS);
    }
}