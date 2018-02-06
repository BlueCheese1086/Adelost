package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class Globals {
	public static Drivetrain drivetrain;
	public static Gyro gyro;
	public static InputManager im;
		
	public static void init() {
		drivetrain = new Drivetrain();
		gyro = new Gyro();
		im = new InputManager();
		drivetrain.init();
	}
}
