package org.usfirst.frc.team1086.MotionProfiling;

import org.usfirst.frc.team1086.robot.Globals;

public class MPConstants {
    public static double TURN_KP = 0.03;
    public static double TURN_KI = 0;
    public static double TURN_KD = Globals.isFinal ? 0.08 : 0.06;

    public static final double DELTA_TIME = 0.02;
    public static final double MAX_VELOCITY = 80;
    public static final double MAX_ACCELERATION = 120.0;
    public static final double MAX_JERK = 363;

    public static final double MP_KP = Globals.isFinal ? 0.12 : 0.08;
    public static final double MP_KI = 0;
    public static final double MP_KD = Globals.isFinal ? 0.0 : 0;
    public static final double MP_KV = 1.0 / MAX_VELOCITY;
    public static final double MP_KA = Globals.isFinal ? 0.35 / MAX_ACCELERATION : 0.75 / MAX_ACCELERATION;

    public static final double WHEELBASE_WIDTH = Globals.isFinal ? -23.78 : -22.57;
}
