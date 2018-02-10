package org.usfirst.frc.team1086.autonomous.sections;

import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

public class MotionProfiler extends AutonomousSection {
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

    @Override public boolean isFinished(){
        return super.isFinished() || return mp.isFinished();
    }
}
