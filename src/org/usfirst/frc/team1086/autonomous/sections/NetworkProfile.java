package org.usfirst.frc.team1086.autonomous.sections;

import edu.wpi.first.networktables.NetworkTableInstance;
import jaci.pathfinder.Waypoint;

public class NetworkProfile extends MotionProfiler {
	public NetworkProfile() {
		super(null);
	}
	@Override public void start(){
		double[] xs = NetworkTableInstance.getDefault().getTable("GeneratedAuto").getEntry("x coordinates").getDoubleArray(new double[]{});
		double[] ys = NetworkTableInstance.getDefault().getTable("GeneratedAuto").getEntry("y coordinates").getDoubleArray(new double[]{});
		double[] angles = NetworkTableInstance.getDefault().getTable("GeneratedAuto").getEntry("angle").getDoubleArray(new double[]{});
		points = new Waypoint[xs.length];
		/*if(xs.length > 0) {
			for(int i = xs.length - 1; i >= 0; i--) {
				xs[i] -= xs[0];
				ys[i] -= ys[0];
				angles[i] -= angles[0];
			}
		}*/
		for(int i = 0; i < xs.length; i++) {
			points[i] = new Waypoint(xs[i], ys[i], angles[i]);
		}
		super.start();
	}
}