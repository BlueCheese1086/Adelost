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

/**
 * Motion profiling is where a series of points containing position, velocity, acceleration, and jerk are generated for each
 * time increment along the way of a specific motion. This allows for very fast moving to a certain position as well as
 * following a path. This class is for following a path.
 */
public class MotionProfiling {
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

        //This is used to ensure that the robot is at the correct angle each step along the way
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

        //A sample path to follow. This path has the robot moving two meters forward and two meters to the side.
        //Note that waypoints are independent of units, but whatever unit you used MUST be consistent.
        //We chose inches, but it doesn't matter too much.
        points = new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(39.4 * 2, 39.4 * 2, 0)
        };
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
            //Initial heading is our current angle...
            startHeading = gyro.getAngle();

            //Use a cubic fit (other option is quintic), and configure with our change in time
            //between each iteration, max velocity, and max acceleration.
            Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
                                                            MPConstants.DELTA_TIME, MPConstants.MAX_VELOCITY, MPConstants.MAX_ACCELERATION, MPConstants.MAX_JERK);

            //Generate the path containing the individual iteration positions
            Trajectory trajectory = Pathfinder.generate(points, config);

            //Create paths for each set of wheels rather than just one for the whole robot.
            //Wheelbase width should be experimentally determined... Make the robot spin in place for 10 rotations.
            //Find the number of inches the encoders recorded moving, then divide by 20 * pi
            TankModifier modifier = new TankModifier(trajectory).modify(MPConstants.WHEELBASE_WIDTH);

            //Make encoder followers for each wheel. These basically take the current position and run that through a PIDVA
            //loop to find the output speeds. We modified this class to log each iteration
            left = new EncoderFollower(modifier.getLeftTrajectory(), "/home/lvuser/leftPath.csv");
            right = new EncoderFollower(modifier.getRightTrajectory(), "/home/lvuser/rightPath.csv");

            //Configure the encoder in the encoderfollower. The first parameter is the starting position, the next is the
            //number of ticks per revolution. For mag encoders, that's 4096. The last is wheel diameter.
            //This should also be experimentally determined. Drive the robot forward 100 inches, divide encoder units
            //by number of ticks per revolution, divide 100 by (number of revolutions * pi), and that is your wheel diameter
            //If you don't use inches, simply convert to whatevver unit you want. Make sure you are consistent with units!!
            left.configureEncoder(drivetrain.left1.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);
            right.configureEncoder(drivetrain.right1.getSelectedSensorPosition(0), 4096, Constants.WHEEL_DIAMETER);

            //Configure the PIDVA
            left.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.MP_KD, MPConstants.MP_KV, MPConstants.MP_KA);
            right.configurePIDVA(MPConstants.MP_KP, MPConstants.MP_KI, MPConstants.MP_KD, MPConstants.MP_KV, MPConstants.MP_KA);

            //Enable the turn controller
            turnController.enable();
        }
        else {
            System.out.println("Waypoints is null when calling init() in Motion Profiling");
        }
    }
    public void run(){
        if(!isFinished()){
            //Calculate left and right output from the encoder followers
            double leftSpeed = left.calculate(drivetrain.left1.getSelectedSensorPosition(0), drivetrain.left1.getSelectedSensorVelocity(0) / 4096.0 * 5 * Math.PI);
            double rightSpeed = right.calculate(drivetrain.right1.getSelectedSensorPosition(0), drivetrain.right1.getSelectedSensorVelocity(0) /4096.0 * 5 * Math.PI);

            //Find the error in angle
            double gyroHeading = gyro.getAngle() - startHeading;
            double desiredHeading = Pathfinder.r2d(left.getHeading());
            angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);

            //Calculate turn speed
            double turn = turnController.get();

            //Run!!
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

    public double getRemainingDuration(){
        return left == null || right == null ? -1 : Math.max(left.getRemainingDuration(), right.getRemainingDuration());
    }
}
