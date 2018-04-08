package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.autonomous.SectionTrigger;
import org.usfirst.frc.team1086.robot.Globals;

import jaci.pathfinder.Waypoint;

/**
 * Runs a specified motion profile.
 * Given a series of waypoints, this will generate a motion profile then have the robot autonomously follow it.
 */
public class MotionProfiler extends AutonomousSection implements SectionTrigger {
    Waypoint[] points;
    MotionProfiling mp;
    int triggerTime;
    /**
     * Motion profiling can serve as a trigger for other events. triggerTime milliseconds before the end of the
     * motion profile, it will trigger.
     * @param points The points to reach
     * @param triggerTime Milliseconds before the end to trigger
     */
    public MotionProfiler(Waypoint[] points, int triggerTime){
        this.duration = -1;
        this.triggerTime = triggerTime;
        this.points = points;
        this.mp = Globals.mp;
    }
    /**
     * Does not trigger
     * @param points The points to reach
     */
    public MotionProfiler(Waypoint[] points){
        this(points, -1);
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
    	return mp.getRemainingDuration() <= triggerTime && mp.getRemainingDuration() != -1;
    }

    @Override public boolean isFinished(){
        return super.isFinished() || mp.isFinished();
    }
}
