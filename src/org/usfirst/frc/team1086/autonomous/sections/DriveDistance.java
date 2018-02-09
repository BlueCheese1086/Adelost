package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class DriveDistance extends AutonomousSection {
    Drivetrain drivetrain;

    public DriveDistance(double distance){
        drivetrain = Globals.drivetrain;
        this.duration = -1;
        drivetrain.em.setPosition(distance);
    }

    @Override public void update() {

    }

    @Override public void finish() {

    }

    @Override public boolean isFinished(){
        return super.isFinished() || drivetrain.em.reachedSetpoint(0.1);
    }
}
