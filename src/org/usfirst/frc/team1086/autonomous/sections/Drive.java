package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class Drive extends AutonomousSection {
    double forward, turn;
    Drivetrain drive;

<<<<<<< HEAD
    public Drive(long duration, double forward, double turn) {
        drive = Drivetrain.getInstance();
=======
    public Drive(long duration, double forward, double turn) {
        drive = Globals.drivetrain;
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7
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
