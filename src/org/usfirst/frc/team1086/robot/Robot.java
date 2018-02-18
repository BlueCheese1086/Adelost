/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1086.robot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousManager;
import org.usfirst.frc.team1086.autonomous.AutonomousStarter;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Climber;
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
	Logger logger;
	Ultrasonic ultrasonic;
	Climber climber;
	MotionProfiling motionProfiling;
	AutonomousStarter autoStarter;
	AutonomousManager selectedAuto;
	ArrayList<Tickable> tickables = new ArrayList<>();
	ScheduledExecutorService teleopLoop, autoLoop;

	@Override
	public void robotInit() {
		Globals.init();
		drivetrain = Globals.drivetrain;
		drivetrain.em.resetEncoders();
		logger = Globals.logger;
		autoStarter = new AutonomousStarter();
		autoStarter.initAutoModes();

		elevator = Globals.elevator;
		arm = Globals.arm;
		intake = Globals.intake;
		motionProfiling = Globals.mp;
		climber = Globals.climber;
		
		tickables.add(logger);
		tickables.add(elevator);
		tickables.add(motionProfiling);
		tickables.add(drivetrain);
		//tickables.add(new BalanceChecker());
		// tickables.add(arm);
		// tickables.add(climber);
		ultrasonic = Globals.ultrasonic;
	}

	@Override
	public void autonomousInit() {
		selectedAuto = autoStarter.start();
		selectedAuto.start();
        autoLoop = Executors.newScheduledThreadPool(1);
        autoLoop.scheduleAtFixedRate(() -> selectedAuto.update(),0, 5, TimeUnit.MILLISECONDS);
	}

	@Override
	public void autonomousPeriodic() {
		//selectedAuto.update();
	}

	@Override
	public void teleopInit() {
		drivetrain.em.resetEncoders();
		elevator.start();
		teleopLoop = Executors.newScheduledThreadPool(1);;
		teleopLoop.scheduleAtFixedRate(() -> tickables.forEach(Tickable::tick),0,5, TimeUnit.MILLISECONDS);
	}

	@Override
	public void teleopPeriodic() {
		//tickables.forEach(Tickable::tick);
		logSmartDashboard();
	}
    @Override public void robotPeriodic(){
        if(!this.isOperatorControl() && teleopLoop != null && !teleopLoop.isShutdown()){
            teleopLoop.shutdown();
        }
        if(!this.isAutonomous() && autoLoop != null && !autoLoop.isShutdown()){
            autoLoop.shutdown();
        }
    }
	@Override
	public void testPeriodic() {
		teleopPeriodic();
	}

	private void logSmartDashboard() {
		drivetrain.logSmartDashboard();
		ultrasonic.logSmartDashboard();
	}
}
