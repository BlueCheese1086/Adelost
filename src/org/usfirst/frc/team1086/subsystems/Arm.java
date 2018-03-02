package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1086.robot.*;

public class Arm implements Tickable {
    InputManager inputManager;
    public TalonSRX armMotor;
    ArmPosition position;
    public Arm() {
        inputManager = Globals.im;
        position = ArmPosition.UP;
        armMotor = new TalonSRX(RobotMap.ARM);
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        armMotor.configNominalOutputForward(0, 0);
        armMotor.configNominalOutputReverse(0, 0);
        armMotor.configPeakOutputForward(1, 0);
        armMotor.configPeakOutputReverse(-1, 0);
        armMotor.setSelectedSensorPosition(0, 0, 0);
        armMotor.config_kP(0, 3, 0);
        armMotor.config_kI(0, 0, 0);
        armMotor.config_kD(0, 2.5, 0);
        armMotor.config_kF(0, 0, 0);
        armMotor.setInverted(true);
    }
    @Override public void tick(){
    	SmartDashboard.putNumber("Arm enc units", armMotor.getSelectedSensorPosition(0));
    	System.out.println(Globals.im.getArmPosition() * 879);
        SmartDashboard.putNumber("Arm Output", armMotor.getMotorOutputPercent());
        armMotor.set(ControlMode.Position, Globals.im.getArmPosition() * 879);
    	if(Math.random() < 2)
    		return;
        if(Globals.im.getArm90Degree()){
            position = ArmPosition.UP;
            Globals.armLocation.setString("Up");
        } else if(Globals.im.getArm45Degree()){
            position = ArmPosition.MID;
            Globals.armLocation.setString("Middle");
        } else if(Globals.im.getArm0Degree()){
            position = ArmPosition.DOWN;
            Globals.armLocation.setString("Down");
        }
    }
    public double getArmPosition() {
		return position.angle;
    	
    }
}
enum ArmPosition {
    UP(0),
    DOWN(0),
    MID(879);
    public double angle;
    int encoderUnits;
    ArmPosition(double angle){
        this.angle = angle * Math.PI / 180.0;
        this.encoderUnits = (int)(Math.round(angle / 360 * 4096));
    }
}