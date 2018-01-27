package org.usfirst.frc.team1086.MotionProfiling;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.Gyro;
import org.usfirst.frc.team1086.robot.InputManager;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class MotionProfiling {
    Waypoint[] points;
    EncoderFollower left, right;
    Gyro gyro;

    PIDController turnController;
    double turnOutput = 0;
    double angleDifference = 0;

    InputManager im;
    Drivetrain drivetrain;

    public MotionProfiling() {
        im = InputManager.getInstance();
        gyro = Gyro.getInstance();
        drivetrain = Drivetrain.getInstance();
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

        /* Sets the default path, can be overwritten by setWaypoints() method */
        points = new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(25, 0, 0)
        };
    }

    public void teleopTick(){
        if(im.getMotionProfileStart()){
            init();
        }
        if(im.getMotionProfileTick()){
            tick();
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
            Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
                                                            MPConstants.DELTA_TIME, MPConstants.MAX_VELOCITY, MPConstants.MAX_ACCELERATION, MPConstants.MAX_JERK);
            Trajectory trajectory = Pathfinder.generate(points, config);
            TankModifier modifier = new TankModifier(trajectory).modify(MPConstants.WHEELBASE_WIDTH);

            left = new EncoderFollower(modifier.getLeftTrajectory(), "/home/lvuser/leftPath.csv");
            right = new EncoderFollower(modifier.getRightTrajectory(), "/home/lvuser/rightPath.csv");

            left.configureEncoder(drivetrain.frontLeft.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);
            right.configureEncoder(drivetrain.frontRight.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);

            left.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.TURN_KD, MPConstants.MP_KV, MPConstants.MP_KA);
            right.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.TURN_KD, MPConstants.MP_KV, MPConstants.MP_KA);

            turnController.enable();
        }
        else {
            System.out.println("Waypoints is null when calling init() in Motion Profiling");
        }
    }

    public void tick(){
        if(!left.isFinished() && !right.isFinished()){
            double leftSpeed = left.calculate(drivetrain.frontLeft.getSelectedSensorPosition(0), drivetrain.getLeftDistance());
            double rightSpeed = right.calculate(drivetrain.frontRight.getSelectedSensorPosition(0), drivetrain.getRightDistance());

            double gyroHeading = gyro.getAngle();
            double desiredHeading = Pathfinder.r2d(left.getHeading());
            angleDifference = Pathfinder.boundHalfDegrees(desiredHeading + gyroHeading);

            double turn = turnController.get();
            drivetrain.driveMP(leftSpeed, rightSpeed, turn);
        }
        else {
            System.out.println("MP is finished");
            drivetrain.drive(0,0);
            turnController.disable();
        }
    }
}
