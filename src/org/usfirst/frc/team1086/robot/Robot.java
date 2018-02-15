/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1086.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousManager;
import org.usfirst.frc.team1086.autonomous.AutonomousStarter;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;
import org.usfirst.frc.team1086.subsystems.Ultrasonic;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
	Drivetrain drivetrain;
	Elevator elevator;
	Intake intake;
	Arm arm;
	Ultrasonic ultrasonic;
	MotionProfiling motionProfiling;
	AutonomousStarter autoStarter;
	AutonomousManager selectedAuto;
    ArrayList<Tickable> tickables = new ArrayList<>();
    
    
	@Override public void robotInit() {
		Globals.init();
		drivetrain = Globals.drivetrain;
		drivetrain.em.resetEncoders();

		autoStarter = new AutonomousStarter();
		autoStarter.initAutoModes();

		elevator = Globals.elevator;
		arm = Globals.arm;
		intake = Globals.intake;
		motionProfiling = Globals.mp;

		//tickables.add(elevator);
		tickables.add(motionProfiling);
		tickables.add(drivetrain);
		//tickables.add(arm);
		ultrasonic = Globals.ultrasonic;
	}

	@Override public void autonomousInit() {
		
	    selectedAuto = autoStarter.start();
	    selectedAuto.start();
	}
	
	@Override public void autonomousPeriodic() {
        selectedAuto.update();
	}

	@Override public void teleopInit(){
		drivetrain.em.resetEncoders();
	}

	@Override public void teleopPeriodic() {
	    tickables.forEach(Tickable::tick);
		logSmartDashboard();
	}

	@Override public void testPeriodic() {
		//teleopPeriodic();
	}

	private void logSmartDashboard(){
		drivetrain.logSmartDashboard();
		ultrasonic.logSmartDashboard();
	}
}
