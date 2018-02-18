package org.usfirst.frc.team1086.MotionProfiling;

import org.usfirst.frc.team1086.robot.*;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.nio.file.Path;

public class MotionProfiling implements Tickable {
    Waypoint[] points;
    EncoderFollower left, right;
    Gyro gyro;

    PIDController turnController;
    double startHeading = 0;
    double turnOutput = 0;
    double angleDifference = 0;

    InputManager im;
    Drivetrain drivetrain;

    public MotionProfiling() {
        im = Globals.im;
        gyro = Globals.gyro;
        drivetrain = Globals.drivetrain;
        turnController = new PIDController(MPConstants.TURN_KP, MPConstants.TURN_KI, MPConstants.TURN_KD, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return angleDifference;
            }
        }, output -> turnOutput = output);

        turnController.setInputRange(-180, 180);
        turnController.setOutputRange(-1, 1);
        turnController.setContinuous(true);


        /* Sets the default path, can be overwritten by setWaypoints() method */


        points = new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(39.4, 39.4, Pathfinder.d2r(45))
        };
        /*
        points = new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(39.4, 39.4, Pathfinder.d2r(90)),
                new Waypoint(0, 2 * 39.4, Pathfinder.d2r(-180)),
                new Waypoint(-39.4, 39.4, Pathfinder.d2r(-90)),
                new Waypoint(0, 0, Pathfinder.d2r(0))
        }; */
        /*
        points = new Waypoint[] {
                new Waypoint(0, 0, 0),
                new Waypoint(6 * 39.4, 0, 0),
                new Waypoint(7 * 39.4, 39.4, Pathfinder.d2r(90)),
                new Waypoint(6 * 39.4, 2 * 39.4, Pathfinder.d2r(180)),
                new Waypoint(0, 2 * 39.4, Pathfinder.d2r(180)),
                new Waypoint(-1 * 39.4, 39.4, Pathfinder.d2r(270)),
                new Waypoint(0, 0, Pathfinder.d2r(0))
        }; */
    }

    @Override public void tick(){
        if(im.getMotionProfileStart()){
            init();
        }
        if(im.getMotionProfileTick()){
            run();
        }
    }

    /**
     * Sets the waypoints
     * @param points - the desired Waypoints to generate a trajectory
     */
    public void setWaypoints(Waypoint[] points){
        this.points = points;
    }

    /**
     * Initializes Motion Profiling and prepares a path to be followed
     */
    public void init(){
        if(points != null){
            startHeading = gyro.getAngle();
            Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
                                                            MPConstants.DELTA_TIME, MPConstants.MAX_VELOCITY, MPConstants.MAX_ACCELERATION, MPConstants.MAX_JERK);
            Trajectory trajectory = Pathfinder.generate(points, config);
            TankModifier modifier = new TankModifier(trajectory).modify(MPConstants.WHEELBASE_WIDTH);

            left = new EncoderFollower(modifier.getLeftTrajectory(), "/home/lvuser/leftPath.csv");
            right = new EncoderFollower(modifier.getRightTrajectory(), "/home/lvuser/rightPath.csv");

            left.configureEncoder(drivetrain.left1.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);
            right.configureEncoder(drivetrain.right1.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);

            left.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.MP_KD, MPConstants.MP_KV, MPConstants.MP_KA);
            right.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.MP_KD, MPConstants.MP_KV, MPConstants.MP_KA);

            turnController.enable();
        }
        else {
            System.out.println("Waypoints is null when calling init() in Motion Profiling");
        }
    }
    public void run(){
        if(!isFinished()){
            double leftSpeed = left.calculate(drivetrain.left1.getSelectedSensorPosition(0), drivetrain.em.getLeftDistance());
            double rightSpeed = right.calculate(drivetrain.right1.getSelectedSensorPosition(0), drivetrain.em.getRightDistance());

            double gyroHeading = gyro.getAngle() - startHeading;
            double desiredHeading = Pathfinder.r2d(left.getHeading());
            angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
            double turn = turnController.get();
            drivetrain.driveMP(leftSpeed, rightSpeed, turn);
        }
        else {
            left.closeFile();
            right.closeFile();
            drivetrain.drive(0,0);
            turnController.disable();
        }
    }

    public boolean isFinished(){
        return left.isFinished() && right.isFinished();
    }
}
