package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

/**
 * Drive forwards or backwards a specified distance using a PID on the TalonSRXs.
 * If no time is specified, it runs until the specified diestance is reached (absolute error < 2 inches).
 * if a time is specified, the section will end when it times out. This does not mean that the robot will
 * stop driving to the specified distance, just that the section will not wait for it.
 */
public class DriveDistance extends AutonomousSection {
    Drivetrain drivetrain;
    double distance;
    public DriveDistance(double distance, int time){
        drivetrain = Globals.drivetrain;
        this.duration = time;
        this.distance = distance;
    }
    public DriveDistance(double distance){
        this(distance, -1);
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
