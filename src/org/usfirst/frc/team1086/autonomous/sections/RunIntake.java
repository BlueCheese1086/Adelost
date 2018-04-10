package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

public class RunIntake extends AutonomousSection {
    double power;
    boolean shortcut;
    public RunIntake(double power, int duration, boolean shortcut){
        this.duration = duration;
        this.power = power;
        this.shortcut = shortcut;
    }
    public RunIntake(double power, int duration){
        this(power, duration, false);
    }
    public RunIntake(double power){
        this(power, -1, true);
    }
    @Override public void update(){
        Globals.intake.run(power);
    }
    @Override public void finish(){

    }
    @Override public boolean isFinished(){
        return super.isFinished() || (shortcut && !Globals.cubeDetector.get());
    }
}
