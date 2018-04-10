package org.usfirst.frc.team1086.autonomous.sections;

import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.autonomous.CombinedSection;

/**
 * Runs a motion profile and moves the elevator to a specified height at the end (simultaneously)
 */
public class MotionProfileWithElevator extends CombinedSection {
    MotionProfiler profiler;
    ElevatorMover elev;
    public MotionProfileWithElevator(Waypoint[] points, double elevatorHeight){
        this(points, elevatorHeight, 1500);
    }
    public MotionProfileWithElevator(Waypoint[] points, double elevatorHeight, int startTime){
        duration = -1;
        this.profiler = new MotionProfiler(points, startTime);
        this.elev = new ElevatorMover(elevatorHeight);
        addSection(profiler, elev);
        addSection(() -> true, profiler);
    }
    @Override public void start(){
        super.start();
        profiler.start();
    }
    @Override public boolean isFinished(){
        return super.isFinished() || profiler.isFinished();
    }
}
