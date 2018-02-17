package org.usfirst.frc.team1086.robot;

import java.util.Stack;

import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class BalanceChecker implements Tickable {
	Drivetrain drivetrain;
	Gyro navx;
	double normalPitch;
	double normalRoll;
	double normalYaw;
	double rollCurrent;
	double pitchCurrent;

	public BalanceChecker() {
		drivetrain = Globals.drivetrain;
		navx = Globals.gyro;
		normalPitch = navx.getPitch();
		normalRoll = navx.getRoll();
		normalYaw = navx.getAngle();
	}

	@Override
	public void tick() {
		rollCurrent = navx.getRoll();
		pitchCurrent = navx.getPitch();
		checkRollMax();
		checkPitchMax();
	}

	/*
	 * returns the maximum angle at which we need to be to flip
	 */
	private void checkRollMax() {
		if (rollCurrent-normalRoll>Math.atan(13.5/9.921413831539647 + 18.12367710411075 u - 1.259343264446456 Math.sin(0.1582261234411514 - i))) {
			
		}
	}

	private void checkPitchMax() {
		if (pitchCurrent-normalPitch>Math.atan((11.62462197 + (0.3887131055434038 + 1.259343264446456 *Math.cos(0.1582261234411514 - intake)))/((9.921413831539647 + (18.12367710411075 elevator - 1.259343264446456* Math.sin(0.1582261234411514 - intake)))-2.59375000)) - 0.004363323129985824
				|| pitchCurrent-normalPitch<-ArcTan((11.62462197 + (0.3887131055434038 + 1.259343264446456 Math.cos(0.1582261234411514 - i)))/((9.921413831539647 + (18.12367710411075 u - 1.259343264446456 Math.sin(0.1582261234411514 - i)))-2.59375000)) + 0.004363323129985824) {
			pitchSave();
		} 
	}

	private void pitchSave() {
		System.out.println("Save Me!!!");
		/*
		 * drivetrain.drive(Math.signum(change),0); 
		 * Elevator.down(lol);
		 * Intake.down(lol);
		 */
	}

	private void rollSave() {
		drivetrain.drive(0, 0);
	}

}
