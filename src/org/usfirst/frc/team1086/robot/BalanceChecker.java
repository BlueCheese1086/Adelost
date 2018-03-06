package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

public  class BalanceChecker implements Tickable {
	Drivetrain drivetrain;
	Gyro navx;
	double normalPitch;
	double pitchCurrent;
	double lastPitch;
	Elevator elevator;
	Arm arm;
	boolean saving = false;
	public static double maxPitch;

	public BalanceChecker() {
		drivetrain = Globals.drivetrain;
		navx = Globals.gyro;
		arm = Globals.arm;
		elevator = Globals.elevator;
		normalPitch = navx.getPitch();
		normalPitch = 0;
		lastPitch = normalPitch;
	}

	@Override
	public void tick() {
		pitchCurrent = navx.getPitch();
		if(!Globals.im.getTipCorrectionOverride()) {
        	saving = needsPitchCorrection();
        	if(saving)
				pitchSave();
		}
	}
    public boolean needsPitchCorrection(){
	    double pitch = pitchCurrent - normalPitch;
	    System.out.println(pitch);
	    double pitchRate = (-lastPitch + (lastPitch = pitch)) / 0.05;
	    double a = arm.getArmPosition();
	    double e= elevator.getElevatorHeight()/75;
	    maxPitch=(61.725-34.027*e)-a*(4.325-2.342*e)+23*(e)*(e-1);
	     			//max and min     //arm shift       //adds shape to graph (similar to a taylor series)
        return (Math.abs(pitch) > maxPitch || Math.abs(pitchRate * 0.1 + pitch) > maxPitch);
    }

	

	private void pitchSave() {
		System.out.println("PITCH!!!");

		drivetrain.drive(Math.signum(pitchCurrent) * .3, 0);
		//elevator.elevatorMotor.set(ControlMode.MotionMagic,0);
		//arm.armMotor.set(ControlMode.Position, 0);

	}

	public boolean isSaving(){
		return saving;
	}
}
