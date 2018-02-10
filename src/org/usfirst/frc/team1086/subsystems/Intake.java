package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
	TalonSRX intake1; 
	TalonSRX intake2;
	
	public Intake() {
		intake1 = new TalonSRX(RobotMap.INTAKE_1); 
		intake2 = new TalonSRX(RobotMap.INTAKE_2);
	}
	
	public void motorIn() {
		intake1.set(ControlMode.PercentOutput, 0.5);
		intake2.set(ControlMode.PercentOutput, 0.5);
	}
	
	public void motorOff() {
		intake1.set(ControlMode.PercentOutput, 0.0);
		intake2.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void motorOut() {
		intake1.set(ControlMode.PercentOutput, -0.5);
		intake2.set(ControlMode.PercentOutput, -0.5);
	}
}
