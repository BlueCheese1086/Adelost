package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.autonomous.SectionTrigger;
import org.usfirst.frc.team1086.robot.Globals;

import jaci.pathfinder.Waypoint;

public class MotionProfiler extends AutonomousSection implements SectionTrigger {
    Waypoint[] points;
    MotionProfiling mp;
    public MotionProfiler(Waypoint[] points){
        this.duration = -1;
        this.points = points;
        this.mp = Globals.mp;
    }

    @Override public void start(){
    	super.start();
        mp.setWaypoints(points);
        mp.init();
    }

    @Override public void update() {
        mp.run();
    }

    @Override public void finish() {

    }
    @Override public boolean trigger() {
    	return true;
    }

    @Override public boolean isFinished(){
        return super.isFinished() || mp.isFinished();
    }
}
