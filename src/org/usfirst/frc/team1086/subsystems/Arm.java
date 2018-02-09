package org.usfirst.frc.team1086.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.usfirst.frc.team1086.robot.*;

public class Arm implements Tickable {
    InputManager inputManager;
    TalonSRX armMotor;
    ArmPosition position;
    public Arm() {
        inputManager = Globals.im;
        armMotor = new TalonSRX(RobotMap.ARM);
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        armMotor.setSensorPhase(true);
        armMotor.configNominalOutputForward(0, 0);
        armMotor.configNominalOutputReverse(0, 0);
        armMotor.configPeakOutputForward(1, 0);
        armMotor.configPeakOutputReverse(-1, 0);
        armMotor.setSelectedSensorPosition(0, 0, 0);
        armMotor.config_kP(0, 0, 0);
        armMotor.config_kI(0, 0, 0);
        armMotor.config_kD(0, 0, 0);
        armMotor.config_kF(0, 0, 0);
    }
    @Override public void tick(){
        if(Globals.im.getArm90Degree()){
            position = ArmPosition.UP;
        } else if(Globals.im.getArm45Degree()){
            position = ArmPosition.MID;
        } else if(Globals.im.getArm0Degree()){
            position = ArmPosition.DOWN;
        }
        armMotor.set(ControlMode.Position, position.encoderUnits);
    }
}
enum ArmPosition {
    UP(90),
    DOWN(0),
    MID(45);
    double angle;
    int encoderUnits;
    ArmPosition(double angle){
        this.angle = angle * Math.PI / 180.0;
        this.encoderUnits = (int)(Math.round(angle / 360 * 4096));
    }
}