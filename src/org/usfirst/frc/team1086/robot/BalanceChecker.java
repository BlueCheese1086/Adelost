package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

public  class BalanceChecker implements Tickable {
	Drivetrain drivetrain;
	Gyro navx;
	double normalPitch;
	double normalRoll;
	double normalYaw;
	double rollCurrent;
	double pitchCurrent;
	Elevator elevator;
	Arm arm;

	public BalanceChecker() {
		drivetrain = Globals.drivetrain;
		navx = Globals.gyro;
		arm = Globals.arm;
		elevator = Globals.elevator;
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
		if (Math.abs(rollCurrent - normalRoll) > Math
				.atan(13.5 / 9.921413831539647 + 18.12367710411075 * elevator.getElevatorHeight() / 75
						- 1.259343264446456 * Math.sin(0.1582261234411514 - arm.getArmPosition()))) {
			rollSave();
		}
	}

	private void checkPitchMax() {
		if (pitchCurrent
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
						+ 0.004363323129985824) {
			pitchSave();
		}
	}

	private void pitchSave() {
		System.out.println("PITCH!!!");

		drivetrain.drive(Math.signum(pitchCurrent), 0);
		elevator.elevatorMotor.set(ControlMode.MotionMagic,0);
		arm.armMotor.set(ControlMode.Position, 0);

	}

	private void rollSave() {
		drivetrain.drive((Math.abs(navx.getAngle() - normalYaw) > 90 ? 1 : -1), 0);// drive the way face
		elevator.elevatorMotor.set(ControlMode.MotionMagic,0);
		arm.armMotor.set(ControlMode.Position, 0);
	}

}
