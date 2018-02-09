/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousStarter;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.ArrayList;

public class Robot extends TimedRobot {
	Drivetrain drivetrain;
	Elevator elevator;
	Intake intake;
	Arm arm;
	MotionProfiling motionProfiling;
	AutonomousStarter autoStarter;
    ArrayList<Tickable> tickables = new ArrayList<>();
	@Override public void robotInit() {
		Globals.init();
		drivetrain = Globals.drivetrain;
		drivetrain.em.resetEncoders();

		autoStarter = new AutonomousStarter();
		autoStarter.initAutoModes();

		motionProfiling = new MotionProfiling();

		elevator = Globals.elevator;
		arm = Globals.arm;
		intake = Globals.intake;
		tickables.add(elevator);
		tickables.add(motionProfiling);
		tickables.add(drivetrain);
		tickables.add(arm);
	}

	@Override public void autonomousInit() {
		
	    autoStarter.start();
	}
	
	@Override public void autonomousPeriodic() {
		
	}

	@Override public void teleopInit(){
		drivetrain.em.resetEncoders();
	}

	@Override public void teleopPeriodic() {
	    tickables.forEach(Tickable::tick);
		logSmartDashboard();
	}

	@Override public void testPeriodic() {
		teleopPeriodic();
	}

	private void logSmartDashboard(){
		drivetrain.logSmartDashboard();
	}
}
