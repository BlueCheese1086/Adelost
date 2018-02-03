package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class Drive extends AutonomousSection {
    double forward, turn;
    Drivetrain drive;

    public Drive(long duration, double forward, double turn) {
        drive = Drivetrain.getInstance();
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
