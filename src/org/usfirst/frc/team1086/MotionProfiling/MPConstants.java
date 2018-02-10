package org.usfirst.frc.team1086.MotionProfiling;

public class MPConstants {
    public static double TURN_KP = 0.04;
    public static double TURN_KI = 0;
    public static double TURN_KD = 0.02;

    public static final double DELTA_TIME = 0.02;
    public static final double MAX_VELOCITY = 120.0;
    public static final double MAX_ACCELERATION = 27.6;
    public static final double MAX_JERK = 2363;

    public static final double MP_KP = 1;
    public static final double MP_KI = 0;
    public static final double MP_KD = 0;
    public static final double MP_KV = 1 / MAX_VELOCITY;
    public static final double MP_KA = 0;

    public static final double WHEELBASE_WIDTH = 27.04;
}
