package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.robot.RobotMap;
import org.usfirst.frc.team1086.robot.Tickable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Controls the climber and the kicker.
 * The climber itself consists of two motors that operate the winch.
 * The "kicker" deploys the climber by pulling a pin.
 * "kicking" is when the pin is pulled, and "unkicking" is when the string holding the pin is loosened
 */
public class Climber implements Tickable {
	InputManager inputManager;
    TalonSRX climberMotor1, climberMotor2, kicker;
	public Climber() {
		inputManager = Globals.im;
		climberMotor1 = new TalonSRX(RobotMap.CLIMB_1);
		climberMotor2 = new TalonSRX(RobotMap.CLIMB_2);
		kicker = new TalonSRX(RobotMap.KICKER);
	}

	@Override
	public void tick() {
		if(inputManager.getKick()) {
			kicker.set(ControlMode.PercentOutput, 1);
		} else if (inputManager.unKick()){
			kicker.set(ControlMode.PercentOutput, -1);
		}else {
			kicker.set(ControlMode.PercentOutput, 0);
		}
		if (inputManager.getClimber()) {
			deploy();
		} else if (inputManager.getClimberRelease()){
			release();
		} else {
			stop();
		}
	}
	
	public void deploy() {
		climberMotor1.set(ControlMode.PercentOutput, -1);
		climberMotor2.set(ControlMode.PercentOutput, -1);
	}

	public void release(){
		climberMotor1.set(ControlMode.PercentOutput, .25);
		climberMotor2.set(ControlMode.PercentOutput, .25);
	}
	public void stop(){
		climberMotor1.set(ControlMode.PercentOutput, 0);
		climberMotor2.set(ControlMode.PercentOutput, 0);
	}
}
