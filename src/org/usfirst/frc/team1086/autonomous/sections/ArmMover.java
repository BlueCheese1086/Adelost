package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

/**
 * Moves the arm pivot to the specified angle.
 * If no time is specified, it will run until the arm has reached the target angle (absolute error of <2 degrees)
 * If a time is specified, it will run for the specified time. Note that this does not mean the arm will stop moving.
 * The arm pivot will continue to move until the angle has been reached.
 */
public class ArmMover extends AutonomousSection {
	double targetAngle;
	public ArmMover(double targetAngle) {
		this(targetAngle, -1);
	}
	public ArmMover(double targetHeight, int duration) {
		this.duration = duration;
		this.targetAngle = targetHeight;
	}
	@Override public void update() {
		Globals.arm.setArmPosition(targetAngle);
	}
	@Override public void finish(){}
	@Override public boolean isFinished() {
		return super.isFinished() || Math.abs(Globals.arm.getArmPosition() - targetAngle) < 2;
	}
}
