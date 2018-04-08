package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.robot.*;

/**
 * The arm pivot.
 */
public class Arm implements Tickable {
    InputManager inputManager;
    public TalonSRX armMotor;
    double armPos = 0;
    public Arm() {
        inputManager = Globals.im;
        armMotor = new TalonSRX(RobotMap.ARM);

        //Inform it of its encoder
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        //Set outputs
        armMotor.configNominalOutputForward(0, 0);
        armMotor.configNominalOutputReverse(0, 0);
        armMotor.configPeakOutputForward(1, 0);
        armMotor.configPeakOutputReverse(-1, 0);

        //Reset encoder
        armMotor.setSelectedSensorPosition(0, 0, 0);

        //Configure PIDF
        armMotor.config_kP(0, 4, 0);
        armMotor.config_kI(0, 0, 0);
        armMotor.config_kD(0, 0, 0);
        armMotor.config_kF(0, 0, 0);

        //Set maximum velocity and acceleration
        armMotor.configMotionCruiseVelocity(500, 0);
        armMotor.configMotionAcceleration(500, 0);

        //Set a current limit
        armMotor.configPeakCurrentLimit(Constants.ARM_PEAK_CURRENT,0);
        armMotor.configContinuousCurrentLimit(Constants.ARM_PEAK_CURRENT,0);

        //Reverse it (moving down increases encoder units)
        armMotor.setInverted(true);
    }
    public void reset(){
        armPos = 0;
    }
    @Override public void tick() {
        if (inputManager.manualArm() != 0){
            //Manually move the arm down and set its new location to the bottom
            armMotor.set(ControlMode.PercentOutput, inputManager.manualArm());
            armMotor.setSelectedSensorPosition(0, 0, 0);
            armPos = Constants.MAX_ARM_ENC_UNITS;
            Globals.logger.print("Arm Manual", Double.toString(inputManager.manualArm()));
        }
        else {
            //Update the target arm position
            armPos += inputManager.getDeltaArm() * 2;

            //Cap it at the ends of its range
            armPos = Math.max(Math.min(armPos, Constants.MAX_ARM_ANGLE), 0);

            //Move to the encoder unit specified by the target angle using motion magic (Generate a motion profile for me)
            armMotor.set(ControlMode.MotionMagic, (1 - armPos / Constants.MAX_ARM_ANGLE) * Constants.MAX_ARM_ENC_UNITS);
        }
    }
    public void setArmPosition(double angle) {
        //Sets the target angle and moves the arm to that angle.
        armMotor.set(ControlMode.MotionMagic, (1 - angle / Constants.MAX_ARM_ANGLE) * Constants.MAX_ARM_ENC_UNITS);
        armPos = angle;
    }
    public double getArmPosition() {
        //Gets the current arm angle
		return Constants.MAX_ARM_ANGLE * (1 - (double)armMotor.getSelectedSensorPosition(0) / Constants.MAX_ARM_ENC_UNITS);
    }
    public void log(){
        SmartDashboard.putNumber("Arm Pos", armPos);
        SmartDashboard.putNumber("Arm Raw Encoder", armMotor.getSelectedSensorPosition(0));
        Globals.armLocation.setNumber(getArmPosition());
        Globals.armCurrent.setNumber(armMotor.getOutputCurrent());
        Globals.logger.print("General", "-------------------ARM------------------");
        Globals.logger.print("Arm Motor Set", Globals.logger.format((1 - armPos / Constants.MAX_ARM_ANGLE) * Constants.MAX_ARM_ENC_UNITS));
        Globals.logger.print("Arm Raw Encoder", Globals.logger.format(armMotor.getSelectedSensorPosition(0)));
        Globals.logger.print("Arm Current", Globals.logger.format(armMotor.getOutputCurrent()));
    }
}