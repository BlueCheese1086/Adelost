package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

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
		//Globals.elevator.set(targetHeight);
	}
	@Override public void finish(){}
	@Override public boolean isFinished() {
		return super.isFinished() || Math.abs(Globals.elevator.get() - targetHeight) < 1;
	}
}
