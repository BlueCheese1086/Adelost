package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.CameraCalculator.PixyCamera;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class PixySpin extends AutonomousSection{
	Drivetrain drivetrain;
	PixyCamera pixy;
	double originalA;
	public PixySpin() {
		drivetrain=Globals.drivetrain;
		pixy=Globals.pixy;
	}
	public void start() {
		super.start();
		pixy.initializeCamera("");
		drivetrain.drive(0,.2);
		originalA=Globals.gyro.getAngle()-1;
	}
	@Override
	public void update() {
		try {
			pixy.getBestSighting();
			finish();
		} catch (Exception e) {
			if (Math.abs(Globals.gyro.getAngle()-originalA)<.001);
		}
	}

	@Override
	public void finish() {
		drivetrain.drive(0, 0);
	}

}
