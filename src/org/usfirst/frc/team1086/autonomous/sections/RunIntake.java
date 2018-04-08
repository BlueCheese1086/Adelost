package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

/**
 * Runs the intake at a specified power.
 * Note that intake does not stop spinning when duration ends.
 */
public class RunIntake extends AutonomousSection {
    double power;
    public RunIntake(double power, int duration){
        this.duration = duration;
        this.power = power;
    }
    @Override public void update(){
        Globals.intake.run(power);
    }
    @Override public void finish(){

    }
}
