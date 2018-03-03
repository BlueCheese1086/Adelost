package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.robot.*;

public class Arm implements Tickable {
    InputManager inputManager;
    public TalonSRX armMotor;
    public Arm() {
        inputManager = Globals.im;
        armMotor = new TalonSRX(RobotMap.ARM);
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        armMotor.configNominalOutputForward(0, 0);
        armMotor.configNominalOutputReverse(0, 0);
        armMotor.configPeakOutputForward(1, 0);
        armMotor.configPeakOutputReverse(-1, 0);
        armMotor.setSelectedSensorPosition(0, 0, 0);
        armMotor.config_kP(0, 0.5, 0);
        armMotor.config_kI(0, 0, 0);
        armMotor.config_kD(0, 0, 0);
        armMotor.config_kF(0, 0, 0);
        armMotor.configMotionCruiseVelocity(1000, 0);
        armMotor.configMotionAcceleration(2000, 0);
        armMotor.setInverted(true);
    }
    @Override public void tick(){
        armMotor.set(ControlMode.MotionMagic, Globals.im.getArmPosition() * 900);
        Globals.armLocation.setNumber(getArmPosition());
        Globals.armCurrent.setNumber(armMotor.getOutputCurrent());
    }
    public void setArmPosition(double angle) {
        armMotor.set(ControlMode.MotionMagic, (1 - angle / (360.0 * Constants.MAX_ARM_ENC_UNITS / 4096)) * Constants.MAX_ARM_ENC_UNITS);
    }
    public double getArmPosition() {
		return 360.0 * Constants.MAX_ARM_ENC_UNITS / 4096 * (1 - (double)armMotor.getSelectedSensorPosition(0) / Constants.MAX_ARM_ENC_UNITS);
    }
}