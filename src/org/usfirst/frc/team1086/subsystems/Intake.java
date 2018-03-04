package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.*;

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
        intake1.configPeakCurrentLimit(Constants.INTAKE_PEAK_CURRENT, 0);
        intake2.configPeakCurrentLimit(Constants.INTAKE_PEAK_CURRENT, 0);
		im = Globals.im;
	}
	
	@Override public void tick() {
		if(im.getIntake())
			motorIn();
		else if(im.getEvict())
			motorOut();
		else
			motorOff();
		Globals.intakeCurrent.setNumber(intake1.getOutputCurrent() / 2 + intake2.getOutputCurrent() / 2);
	}
	
	public void motorIn() {
		run(-0.75);
	}
		
	public void motorOff() {
		run(-0.2);
	}
	
	public void motorOut() {
		run(0.8 - 0.6 * Globals.elevator.getElevatorHeight() / Constants.ELEVATOR_HEIGHT);
	}
	public void run(double power){
        intake1.set(ControlMode.PercentOutput, power);
        intake2.set(ControlMode.PercentOutput, power);
    }
}
