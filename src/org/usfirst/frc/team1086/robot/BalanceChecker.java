package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * This class checks to see if the robot is either falling over or going to fall over soon.
 * If it is, this overrides all other robot actions and stops it.
 */
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
		lastPitch = normalPitch;
	}

	public void reset(){
		normalPitch = navx.getPitch();
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

    /**
     * Checks if the robot needs to be "saved"
     * @return whether or not to engage the anti-tip
     */
    public boolean needsPitchCorrection(){
        //Our VMX-Pi isn't perfectly horizontal, so take an initial read and find the difference each time
	    double pitch = pitchCurrent - normalPitch;

        //Rate of change in the pitch = (pitch - last pitch) / change in time
	    double pitchRate = (-lastPitch + (lastPitch = pitch)) / 0.05;

        //Robot can tip more when the elevator is down... find how much it can
        //Multiplier goes from 1 at highest to 1.75 at lowest
	    double multiplier = 0.75 * (1 - Globals.elevator.get() / Constants.ELEVATOR_HEIGHT) + 1;
        multiplier = Math.max(1.0, Math.min(multiplier, 1.75));

        //If pitch exceeds max or min pitch (times their multiplier) or if pitch in 0.1 seconds will exceed min or max, ENGAGE!!!
	    return (pitch > MAX_PITCH_FORWARD * multiplier ||pitchRate * 0.1 + pitch > MAX_PITCH_FORWARD * multiplier)
        		|| (pitch < MAX_PITCH_BACKWARDS  * multiplier|| pitchRate * 0.1 + pitch < MAX_PITCH_BACKWARDS * multiplier);
    }

    /**
     * Drives in the direction that the robot is falling
     */
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
