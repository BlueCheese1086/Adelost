package org.usfirst.frc.team1086.robot;

public class Utils {
    public static double normalizeAngle(double angle) {
        double ang = (angle % 360 + 360 % 360);
        return ang > 180 ? ang - 360 : ang;
    }
}
