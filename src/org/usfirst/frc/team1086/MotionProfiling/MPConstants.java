package org.usfirst.frc.team1086.MotionProfiling;

public class MPConstants {
    public static double TURN_KP = 0.02;
    public static double TURN_KI = 0;
    public static double TURN_KD = 0.01;

    public static final double DELTA_TIME = 0.02;
    public static final double MAX_VELOCITY = 46;
    public static final double MAX_ACCELERATION = 350.6;
    public static final double MAX_JERK = 363;

    public static final double MP_KP = 0.05;
    public static final double MP_KI = 0;
    public static final double MP_KD = 0;
    public static final double MP_KV = 1 / MAX_VELOCITY;
    public static final double MP_KA = 0;

    public static final double WHEELBASE_WIDTH = 27.04;
}
