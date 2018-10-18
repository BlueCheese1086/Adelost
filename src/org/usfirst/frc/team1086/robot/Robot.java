package org.usfirst.frc.team1086.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousManager;
import org.usfirst.frc.team1086.autonomous.AutonomousStarter;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Climber;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Main robot class. Very little is done here except for managing everything else.
 */
public class Robot extends TimedRobot {
	Drivetrain drivetrain;
	Elevator elevator;
	Intake intake;
	Arm arm;
	Logger logger;
	Climber climber;
	MotionProfiling motionProfiling;
	Relay lights;
	BalanceChecker balancer;
	AutonomousStarter autoStarter;
	AutonomousManager selectedAuto;
	ArrayList<Tickable> tickables = new ArrayList<>();

	boolean isCompetition = true;
    boolean ranAuto;

	@Override
	public void robotInit() {
		Globals.logger = new Logger("Initialization");
		Globals.init();
		drivetrain = Globals.drivetrain;
		drivetrain.em.resetEncoders();
		logger = Globals.logger;
		autoStarter = new AutonomousStarter();
		autoStarter.initAutoModes();
		lights = new Relay(0);
		lights.set(Value.kForward);
		
		elevator = Globals.elevator;
		arm = Globals.arm;
		intake = Globals.intake;
		motionProfiling = Globals.mp;
		climber = Globals.climber;
		balancer = Globals.balanceChecker;

		tickables.add(elevator);
		tickables.add(drivetrain);
		tickables.add(balancer);
		tickables.add(intake);
		tickables.add(arm);
        tickables.add(climber);
		Globals.logger.print("Event", "Robot Initialized");
	}
    @Override public void startCompetition(){
	    super.startCompetition();
	    isCompetition = true;
    }
	@Override
	public void autonomousInit() {
		System.out.println("Running auto init!");
		Globals.logger = new Logger("Auto");
	    Globals.logger.print("Event", "Autonomous Init");
        ranAuto = true;
        elevator.reset();
		arm.armMotor.setSelectedSensorPosition(0, 0, 0);
		arm.setArmPosition(Constants.MAX_ARM_ANGLE);
		selectedAuto = autoStarter.start();
		selectedAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
		selectedAuto.update();
	}

	@Override
	public void teleopInit() {
		Globals.logger = new Logger("Teleop");
	    Globals.logger.print("Event", "Teleop Init");
	    balancer.reset();
		if(!isCompetition) {
			elevator.reset();
			arm.reset();
			arm.armMotor.setSelectedSensorPosition(Constants.MAX_ARM_ENC_UNITS, 0, 0);
			arm.setArmPosition(0);
			drivetrain.em.resetEncoders();
			elevator.start();
		}
	}

	@Override
	public void teleopPeriodic() {
		if(!Globals.cubeDetector.get())
			lights.set(Value.kForward);
		else lights.set(Value.kOff);
	    if(!balancer.isSaving() || Globals.im.getTipCorrectionOverride()) {
            tickables.forEach(Tickable::tick);
        } else {
	        balancer.tick();
        }
		log();
	}

	@Override
	public void testPeriodic() {
	    teleopPeriodic();
	}

	@Override public void disabledInit() {
		logger.finish();
	}

	private void log() {
	    Globals.logger.print("General", "-----------------NEW TICK-------------------");
		drivetrain.log();
		drivetrain.em.log();
        Globals.gyro.log();
        elevator.log();
        arm.log();
	}
}
