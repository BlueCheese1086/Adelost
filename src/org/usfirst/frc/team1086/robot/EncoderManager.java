package org.usfirst.frc.team1086.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class EncoderManager {
    Drivetrain drive;
    public EncoderManager(){
        drive = Drivetrain.getInstance();
        drive.frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        drive.frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    }

    public void resetEncoders(){
        drive.frontLeft.setSelectedSensorPosition(0, 0, 0);
        drive.frontRight.setSelectedSensorPosition(0, 0, 0);
    }

    public double getLeftDistance(){
        return drive.frontLeft.getSelectedSensorPosition(0) / 4096 * Constants.WHEEL_DIAMETER;
    }

    public double getRightDistance(){
        return drive.frontRight.getSelectedSensorPosition(0) / 4096 * Constants.WHEEL_DIAMETER;
    }

    public double getEncDistance(){
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    public void logSmartDashboard(){

    }
}
