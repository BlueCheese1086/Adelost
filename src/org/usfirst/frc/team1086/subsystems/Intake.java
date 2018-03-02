package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;
import org.usfirst.frc.team1086.robot.Tickable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake implements Tickable {
	TalonSRX intake1; 
	TalonSRX intake2;
	InputManager im;
	public Intake() {
		intake1 = new TalonSRX(RobotMap.INTAKE_1); 
		intake2 = new TalonSRX(RobotMap.INTAKE_2);
		intake1.setInverted(true);
		im = Globals.im;
	}
	
	@Override public void tick() {
		if(im.getIntake())
			motorIn();
		else if(im.getEvict())
			motorOut();
		else
			motorOff();
	}
	
	public void motorIn() {
		intake1.set(ControlMode.PercentOutput, -0.75);
		intake2.set(ControlMode.PercentOutput, -0.75);
	}
		
	public void motorOff() {
		intake1.set(ControlMode.PercentOutput, 0.0);
		intake2.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void motorOut() {
		intake1.set(ControlMode.PercentOutput, 0.75);
		intake2.set(ControlMode.PercentOutput, 0.75);
	}
}
