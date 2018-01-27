/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
	Drivetrain drivetrain;
	MotionProfiling motionProfiling;

	@Override public void robotInit() {
		drivetrain = Drivetrain.getInstance();
	}

	@Override public void autonomousInit() {
		
	}
	
	@Override public void autonomousPeriodic() {
		
	}

	@Override public void teleopInit(){

	}

	@Override public void teleopPeriodic() {
		drivetrain.teleopTick();
		motionProfiling.teleopTick();
		logSmartDashboard();
	}

	@Override public void testPeriodic() {
		teleopPeriodic();
	}

	private void logSmartDashboard(){
		drivetrain.logSmartDashboard();
	}
}
