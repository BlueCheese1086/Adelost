package org.usfirst.frc.team1086.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class EncoderManager {
    Drivetrain drive;
    
    public EncoderManager(){
        drive = Globals.drivetrain;
        drive.frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        drive.frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        drive.frontLeft.setSensorPhase(true);
        drive.frontRight.setSensorPhase(true);

        drive.frontLeft.configNominalOutputForward(0, 0);
        drive.frontLeft.configNominalOutputReverse(0, 0);
        drive.frontLeft.configPeakOutputForward(1, 0);
        drive.frontLeft.configPeakOutputReverse(-1, 0);

        drive.frontRight.configNominalOutputForward(0, 0);
        drive.frontRight.configNominalOutputReverse(0, 0);
        drive.frontRight.configPeakOutputForward(1, 0);
        drive.frontRight.configPeakOutputReverse(-1, 0);

        drive.frontLeft.configAllowableClosedloopError(0, Constants.ALLOWABLE_ERROR, 0);
        drive.frontRight.configAllowableClosedloopError(0, Constants.ALLOWABLE_ERROR, 0);

        drive.frontLeft.config_kP(0, Constants.ENCODER_KP, 0);
        drive.frontLeft.config_kI(0, Constants.ENCODER_KI, 0);
        drive.frontLeft.config_kD(0, Constants.ENCODER_KD, 0);
        drive.frontLeft.config_kF(0, Constants.ENCODER_KF, 0);

        drive.frontRight.config_kP(0, Constants.ENCODER_KP, 0);
        drive.frontRight.config_kI(0, Constants.ENCODER_KI, 0);
        drive.frontRight.config_kD(0, Constants.ENCODER_KD, 0);
        drive.frontRight.config_kF(0, Constants.ENCODER_KF, 0);

        resetEncoders();
    }

    public void resetEncoders(){
        drive.frontLeft.setSelectedSensorPosition(0, 0, 0);
        drive.frontRight.setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * Sets the desired distance to drive and activates Position control mode
     * @param dist - the desired distance in inches
     */
    public void setPosition(double dist){
        double distNative = dist * 4096.0 / Constants.WHEEL_DIAMETER / Math.PI;
        double currentPosNative = getEncDistance() * 4096.0 / Constants.WHEEL_DIAMETER / Math.PI;
        drive.frontLeft.set(ControlMode.Position, currentPosNative + distNative);
        drive.frontRight.set(ControlMode.Position, currentPosNative + distNative);
    }

    public double getLeftDistance(){
        return drive.frontLeft.getSelectedSensorPosition(0) / 4096.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }

    public double getRightDistance(){
        return drive.frontRight.getSelectedSensorPosition(0) / 4096.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }

    public double getEncDistance(){
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    public void logSmartDashboard(){
    	SmartDashboard.putNumber("Encoder Left", getLeftDistance());
    	SmartDashboard.putNumber("Encoder Right", getRightDistance());
    	SmartDashboard.putNumber("Encoder Distance", getEncDistance());
    }
}
