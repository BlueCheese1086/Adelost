package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;

public class Globals {
	public static Drivetrain drivetrain;
	public static Elevator elevator;
	public static Arm arm;
	public static Intake intake;
	public static Gyro gyro;
	public static InputManager im;
	public static MotionProfiling mp;
	public static void init() {
        im = new InputManager();
		drivetrain = new Drivetrain();
		gyro = new Gyro();
		arm = new Arm();
		intake = new Intake();
		elevator = new Elevator();
		mp = new MotionProfiling();
		drivetrain.init();
	}
}
