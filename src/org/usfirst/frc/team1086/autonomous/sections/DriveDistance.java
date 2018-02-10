package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class DriveDistance extends AutonomousSection {
    Drivetrain drivetrain;
    double distance;
    public DriveDistance(double distance){
        drivetrain = Globals.drivetrain;
        this.duration = -1;
        this.distance = distance;
    }

    @Override public void start(){
    	super.start();
        System.out.println("DriveSection start");
        drivetrain.em.setPosition(distance);
    }

    @Override public void update() {
        System.out.println("DriveSection update");
    }

    @Override public void finish() {
        System.out.println("Drive Section finished");
    }

    @Override public boolean isFinished(){
        return super.isFinished() || drivetrain.em.reachedSetpoint(2);
    }
}
