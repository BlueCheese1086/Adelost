package org.usfirst.frc.team1086.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class EncoderManager {
    Drivetrain drive;
    double leftSetpoint, rightSetpoint;
    public EncoderManager(){
        drive = Globals.drivetrain;
        drive.left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        drive.right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        drive.left1.setSensorPhase(true);
        drive.right1.setSensorPhase(true);

        drive.left1.configNominalOutputForward(0, 0);
        drive.left1.configNominalOutputReverse(0, 0);
        drive.left1.configPeakOutputForward(1, 0);
        drive.left1.configPeakOutputReverse(-1, 0);

        drive.right1.configNominalOutputForward(0, 0);
        drive.right1.configNominalOutputReverse(0, 0);
        drive.right1.configPeakOutputForward(1, 0);
        drive.right1.configPeakOutputReverse(-1, 0);

        drive.left1.configAllowableClosedloopError(0, Constants.ALLOWABLE_ERROR, 0);
        drive.right1.configAllowableClosedloopError(0, Constants.ALLOWABLE_ERROR, 0);

        drive.left1.config_kP(0, Constants.ENCODER_KP, 0);
        drive.left1.config_kI(0, Constants.ENCODER_KI, 0);
        drive.left1.config_kD(0, Constants.ENCODER_KD, 0);
        drive.left1.config_kF(0, Constants.ENCODER_KF, 0);

        drive.right1.config_kP(0, Constants.ENCODER_KP, 0);
        drive.right1.config_kI(0, Constants.ENCODER_KI, 0);
        drive.right1.config_kD(0, Constants.ENCODER_KD, 0);
        drive.right1.config_kF(0, Constants.ENCODER_KF, 0);

        resetEncoders();
    }

    public void resetEncoders(){
        drive.left1.setSelectedSensorPosition(0, 0, 0);
        drive.right1.setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * Sets the desired distance to drive and activates Position control mode
     * @param dist - the desired distance in inches
     */
    public void setPosition(double dist){
        double distNative = dist * 4096.0 / Constants.WHEEL_DIAMETER / Math.PI;
        double leftPosNative = drive.left1.getSelectedSensorPosition(0);
        double rightPosNative = drive.right1.getSelectedSensorPosition(0);
        this.leftSetpoint = leftPosNative + distNative;
        this.rightSetpoint = rightPosNative + distNative;
        drive.left1.set(ControlMode.Position, leftPosNative + distNative);
        drive.right1.set(ControlMode.Position, rightPosNative + distNative);
    }

    public double getLeftDistance(){
        return drive.left1.getSelectedSensorPosition(0) / 4096.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }

    public double getRightDistance(){
        return drive.right1.getSelectedSensorPosition(0) / 4096.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }

    public double getEncDistance(){
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    public boolean reachedSetpoint(double tolerance) {
        return leftError() <= tolerance && rightError() <= tolerance;
    }

    private double leftError(){
    	double leftpoint = leftSetpoint /  4096.0 * Constants.WHEEL_DIAMETER * Math.PI;
    	double error = Math.abs((leftpoint - getLeftDistance()));
    	System.out.println(error);
        return error;
    }

    private double rightError(){
        return Math.abs(rightSetpoint / 4096.0 * Constants.WHEEL_DIAMETER * Math.PI - getRightDistance());
    }

    public void logSmartDashboard(){
    	SmartDashboard.putNumber("Encoder Left", getLeftDistance());
    	SmartDashboard.putNumber("Encoder Right", getRightDistance());
    	SmartDashboard.putNumber("Encoder Distance", getEncDistance());
    }
}
