package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

/**
 * Sets the speed of the drivetrain and waits the specified duration.
 * When the duration is exceeded, the robot does not necessarily stop - it maintains the last power until a new one is specified.
 */
public class Drive extends AutonomousSection {
    double forward, turn;
    Drivetrain drive;

    public Drive(long duration, double forward, double turn){
        drive = Globals.drivetrain;
        this.duration = duration;
        this.forward = forward;
        this.turn = turn;
    }

    @Override public void update() {
        drive.drive(forward, turn);
    }

    @Override public void finish() {

    }
}
