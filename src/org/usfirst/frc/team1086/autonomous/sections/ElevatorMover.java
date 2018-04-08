package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

/**
 * Moves the elevator to a specified height.
 * If a duration is not specified, it will run until it reaches the specified height (absolute error < 1 inch).
 * If a duration is specified, it will run until either it times out or the height is reached.
 * If it times out, it will continue to move the elevator, but it will no longer wait for it.
 */
public class ElevatorMover extends AutonomousSection {
	double targetHeight;
	public ElevatorMover(double targetHeight) {
		this(targetHeight, -1);
	}
	public ElevatorMover(double targetHeight, int duration) {
		this.duration = duration;
		this.targetHeight = targetHeight;
	}
	@Override public void update() {
		Globals.elevator.set(targetHeight);
	}
	@Override public void finish(){}
	@Override public boolean isFinished() {
		return super.isFinished() || Math.abs(Globals.elevator.get() - targetHeight) < 1;
	}
}
