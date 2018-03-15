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
	public static final double MAX_PITCH_FORWARD = 20;
	public static final double MAX_PITCH_BACKWARDS = -20;
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
	    double pitchRate = (-lastPitch + (lastPitch = pitch)) / 0.05;
	    double multiplier = 0.5 * (1 - Globals.elevator.get() / Constants.ELEVATOR_HEIGHT) + 1;
        multiplier = Math.max(1.0, Math.min(multiplier, 1.5));
	    return (pitch > MAX_PITCH_FORWARD * multiplier || pitchRate * 0.1 + pitch > MAX_PITCH_FORWARD * multiplier)
        		|| (pitch < MAX_PITCH_BACKWARDS  * multiplier|| pitchRate * 0.1 + pitch < MAX_PITCH_BACKWARDS * multiplier);
    }

	private boolean checkPitchMax() {
		return (pitchCurrent
				- normalPitch > Math.atan((11.62462197 + (0.3887131055434038
						+ 1.259343264446456 * Math.cos(0.1582261234411514 - arm.getArmPosition())))
						/ ((9.921413831539647 + (18.12367710411075 * elevator.getElevatorHeight() / 75
								- 1.259343264446456 * Math.sin(0.1582261234411514 - arm.getArmPosition())))
								- 2.59375000))
						- 0.004363323129985824
				|| pitchCurrent - normalPitch < -Math.atan((11.62462197 + (0.3887131055434038
						+ 1.259343264446456 * Math.cos(0.1582261234411514 - arm.getArmPosition())))
						/ ((9.921413831539647 + (18.12367710411075 * elevator.getElevatorHeight() / 75
								- 1.259343264446456 * Math.sin(0.1582261234411514 - arm.getArmPosition())))
								- 2.59375000))
						+ 0.004363323129985824);

	}

	private void pitchSave() {
        log();
		drivetrain.drive(Math.signum(pitchCurrent) * .3, 0);
	}

	public boolean isSaving(){
		return saving;
	}

	public void log(){
	    Globals.logger.print("Event", "BALANCE CHECKER TAKEOVER");
	    Globals.logger.print("Balance Checker Pitch", Double.toString(pitchCurrent - normalPitch));
    }
}
