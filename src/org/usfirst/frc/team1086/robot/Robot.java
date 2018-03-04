package org.usfirst.frc.team1086.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	DigitalInput dio;
	Climber climber;
	MotionProfiling motionProfiling;
	BalanceChecker balancer;
	AutonomousStarter autoStarter;
	AutonomousManager selectedAuto;
	ArrayList<Tickable> tickables = new ArrayList<>();

	@Override
	public void robotInit() {
		Globals.init();
		drivetrain = Globals.drivetrain;
		dio = new DigitalInput(0);
		drivetrain.em.resetEncoders();
		logger = Globals.logger;
		autoStarter = new AutonomousStarter();
		autoStarter.initAutoModes();

		elevator = Globals.elevator;
		arm = Globals.arm;
		intake = Globals.intake;
		motionProfiling = Globals.mp;
		climber = Globals.climber;
		balancer = Globals.balanceChecker;

		tickables.add(logger);
		tickables.add(elevator);
		tickables.add(motionProfiling);
		tickables.add(drivetrain);
		tickables.add(balancer);
		tickables.add(intake);
		tickables.add(arm);
        tickables.add(climber);
		ultrasonic = Globals.ultrasonic;
	}

	@Override
	public void autonomousInit() {
		arm.armMotor.setSelectedSensorPosition(0, 0, 0);
		selectedAuto = autoStarter.start();
        System.out.println(selectedAuto);
		selectedAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
		selectedAuto.update();
	}

	@Override
	public void teleopInit() {
		arm.armMotor.setSelectedSensorPosition(900, 0, 0);
		drivetrain.em.resetEncoders();
		elevator.start();
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putBoolean("HMMMM", dio.get());
	    if(!balancer.isSaving()) {
            tickables.forEach(Tickable::tick);
            System.out.println("Your life doesn't matter to me right now.");
        } else {
	        balancer.tick();
            System.out.println("I'm saving your life. trust me.");
        }
		logSmartDashboard();
	}

	@Override
	public void testPeriodic() {
	    //teleopPeriodic();
	}

	private void logSmartDashboard() {
		drivetrain.logSmartDashboard();
		ultrasonic.logSmartDashboard();
	}
}
