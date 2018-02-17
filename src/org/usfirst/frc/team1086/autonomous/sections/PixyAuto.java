package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.CameraCalculator.PixyCamera;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PixyAuto extends AutonomousSection {
	static final double 
			DRIVE_P = 0,
			DRIVE_I = 0, 
			DRIVE_D = 0,
			
			TURN_P = 0,
			TURN_I = 0, 
			TURN_D = 0;
	
	Drivetrain drivetrain;
	PixyCamera pixy;
	double angleDiff, area;
	PIDController turnPID, drivePID;

	

	public PixyAuto() {
		drivetrain = Globals.drivetrain;
		this.duration = -1;
		pixy=Globals.pixy;
		turnPID = new PIDController(TURN_P, TURN_I, TURN_D, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType source) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				try {
					return pixy.getBestSighting().centerX;

				} catch (Exception e) {
					return 160;
				}
			}
		}, (o) -> {
			angleDiff = o;
		});
		drivePID = new PIDController(DRIVE_P, DRIVE_I, DRIVE_D, new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType source) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				try {
					return pixy.getBestSighting().area;

				} catch (Exception e) {
					return 160;
				}
			}
		}, (o) -> {
			area= o;
		});
		drivePID.setInputRange(1, 6400);
		drivePID.setOutputRange(-1, 1);
		drivePID.setContinuous(false);
		drivePID.setSetpoint(6400);
		drivePID.enable(); //setting the various attributes of the PIDControllers
		turnPID.setInputRange(0, 320);
		turnPID.setOutputRange(-1, 1);
		turnPID.setContinuous(false);
		turnPID.setSetpoint(160);
		turnPID.enable();

	}

	@Override
	public void update() {
		drivetrain.drive(drivePID.get(), turnPID.get());
	}

	@Override
	public void finish() {
		turnPID.disable();
		drivePID.disable();
		
	}

}
