package org.usfirst.frc.team1086.robot;

public class Utils {
    /**
     * Normalizes an angle to between -180 and 180
     * @param angle the input angle
     * @return the output angle. This will always be modularly congruent to the input (mod 360)
     */
    public static double normalizeAngle(double angle){
        double ang = (angle % 360 + 360 % 360);
        return ang > 180 ? ang - 360 : ang;
    }
}
