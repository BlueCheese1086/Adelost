package org.usfirst.frc.team1086.MotionProfiling;
import jaci.pathfinder.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EncoderFollower extends jaci.pathfinder.followers.EncoderFollower {
    int encoder_offset, encoder_tick_count;
    double wheel_circumference;

    double kp, ki, kd, kv, ka;

    double last_error, heading;

    int segment;
    Trajectory trajectory;
    File file;
    BufferedWriter bw;

    public EncoderFollower(Trajectory traj, String filePath) {
        super(traj);
        file = new File(filePath);
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("Distance, Position, Velocity, Acceleration, Enc_Velocity, Output");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate the desired output for the motors, based on the amount of ticks the encoder has gone through.
     * This does not account for heading of the robot. To account for heading, add some extra terms in your control
     * loop for realignment based on gyroscope input and the desired heading given by this object.
     * @param encoder_tick The amount of ticks the encoder has currently measured.
     * @param vel The current velocity of the encoder
     * @return             The desired output for your motor controller
     */
    public double calculate(int encoder_tick, double vel) {
        Trajectory.Segment seg = trajectory.get(segment);
        double calculate_value = super.calculate(encoder_tick);
        double distance_covered = ((double)(encoder_tick - encoder_offset) / encoder_tick_count)
                * wheel_circumference;
        try {
            String line = distance_covered + ", " + seg.position + ", " + seg.velocity + ", " + seg.acceleration + ", " + vel + ", " + calculate_value +  "\n";
            bw.write(line);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calculate_value;
    }

    public void closeFile(){
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
