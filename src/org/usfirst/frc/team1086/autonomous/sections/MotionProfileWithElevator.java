package org.usfirst.frc.team1086.autonomous.sections;

import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.autonomous.CombinedSection;

public class MotionProfileWithElevator extends CombinedSection {
    MotionProfiler profiler;
    ElevatorMover elev;
    public MotionProfileWithElevator(Waypoint[] points, double elevatorHeight){
        duration = -1;
        this.profiler = new MotionProfiler(points, 1500);
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
