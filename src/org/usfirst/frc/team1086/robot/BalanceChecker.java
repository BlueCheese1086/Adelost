package org.usfirst.frc.team1086.robot;

import java.util.Stack;

import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class BalanceChecker implements Tickable {
	Drivetrain drivetrain;
	static final double MAXIMUM_TOLERANCE=180;
	static final double SAVE_CONSTANT=1;
	Gyro navx;
	double last = 0;
	double change=0;
	public BalanceChecker() {
		drivetrain = Globals.drivetrain;
		navx = Globals.gyro;
	}

	@Override
	public void tick() {
		change = navx.getPitch() - last;
		last = navx.getPitch();
		if (Math.abs(change)>MAXIMUM_TOLERANCE) {
			save();
		}
	}
	private void save() {
		System.out.println("Save Me!!!");
		 //drivetrain.drive(Math.signum(change)*SAVE_CONSTANT*Math.log(Math.abs(change)),0);
	}

}
