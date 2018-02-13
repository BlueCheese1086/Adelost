package org.usfirst.frc.team1086.subsystems;

import org.usfirst.frc.team1086.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

<<<<<<< HEAD
public class Drivetrain {
    private static Drivetrain instance;

    public TalonSRX frontLeft, frontRight, backLeft, backRight;
    private InputManager im;
    public EncoderManager em;

    static {
        instance = new Drivetrain();
    }

    /**
     * Initializer for the Drivetrain class.
     */
    private Drivetrain() {
        frontLeft = new TalonSRX(RobotMap.DRIVE_FRONT_LEFT);
        frontRight = new TalonSRX(RobotMap.DRIVE_FRONT_RIGHT);
        backLeft = new TalonSRX(RobotMap.DRIVE_BACK_LEFT);
        backRight = new TalonSRX(RobotMap.DRIVE_BACK_RIGHT);
        frontLeft.setInverted(true);
        backLeft.setInverted(true);
        im = InputManager.getInstance();
        em = new EncoderManager();
    }

    public static Drivetrain getInstance() {
        return instance;
    }

    public void teleopTick() {
        if (im.getSafety()) {
            drive(im.getDrive(), im.getTurn());
        } else {
            drive(0, 0);
        }
    }

    /**
     * Applies power to the Drivetrain motors to move the robot.
     * @param drive - the power to send to move forwards and backwards. 1 is full speed forwards, -1 is full speed backwards
     * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
     */
    public void drive(double drive, double turn) {
        frontLeft.set(ControlMode.PercentOutput, drive + turn);
        frontRight.set(ControlMode.PercentOutput, drive - turn);
        backLeft.set(ControlMode.PercentOutput, drive + turn);
        backRight.set(ControlMode.PercentOutput, drive - turn);
    }

    /**
     * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
     * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
     * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
     * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
     */
    public void driveMP(double left, double right, double turn) {
        frontLeft.set(ControlMode.PercentOutput, left + turn);
        backLeft.set(ControlMode.PercentOutput, left + turn);
        frontRight.set(ControlMode.PercentOutput, right - turn);
        backRight.set(ControlMode.PercentOutput, right - turn);
    }

    public void logSmartDashboard() {
        em.logSmartDashboard();
    }
=======
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Drivetrain implements Tickable {
    public TalonSRX left1, right1, left2, right2;
    private InputManager im;
    public EncoderManager em;
    private Gyro gyro;

    public PIDController driveStraightController;
    public PIDController turnToAngleController;
    
    /**
     * Initializer for the Drivetrain class.
     */
    

    public Drivetrain() {
        left1 = new TalonSRX(RobotMap.DRIVE_LEFT_1);
        right1 = new TalonSRX(RobotMap.DRIVE_RIGHT_1);
        left2 = new TalonSRX(RobotMap.DRIVE_LEFT_2);
        right2 = new TalonSRX(RobotMap.DRIVE_RIGHT_2);
        left1.setInverted(true);
        left2.setInverted(true);
        left2.set(ControlMode.Follower, RobotMap.DRIVE_LEFT_1);
        right2.set(ControlMode.Follower, RobotMap.DRIVE_RIGHT_1);
    }

    public void init() {
        //This is where all of the NetworkTableEntries are initialized
        NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
        NetworkTable table = tableInstance.getTable("Telemetry");
        Globals.Heading = table.getEntry("Heading");
        Globals.Speed = table.getEntry("Speed");
        Globals.Acceleration = table.getEntry("Acceleration");
        Globals.Left1Output = table.getEntry("Left1Output");
        Globals.Right1Output = table.getEntry("Right1Output");
        Globals.Left2Output = table.getEntry("Left2Output");
        Globals.Right2Output = table.getEntry("Right2Output");
        Globals.ElevatorHeight = table.getEntry("ElevatorHeight");
        Globals.ArmLocation = table.getEntry("ArmLocation");
                
        im = Globals.im;
        em = new EncoderManager();
        gyro = Globals.gyro;
        driveStraightController = new PIDController(Constants.DRIVE_STRAIGHT_KP, Constants.DRIVE_STRAIGHT_KI,
                                                    Constants.DRIVE_STRAIGHT_KD, gyro, d -> {});
        driveStraightController.setAbsoluteTolerance(0);
        driveStraightController.setInputRange(-180, 180);
        driveStraightController.setOutputRange(-1, 1);
        driveStraightController.setContinuous(true);
        
        turnToAngleController = new PIDController(Constants.TURN_TO_ANGLE_KP, Constants.TURN_TO_ANGLE_KI,
                                                  Constants.TURN_TO_ANGLE_KD, gyro, d -> {});
        turnToAngleController.setAbsoluteTolerance(2);
        turnToAngleController.setInputRange(-180, 180);
        turnToAngleController.setOutputRange(-1, 1);
        turnToAngleController.setContinuous(true);
    }
    
    @Override public void tick() {
        if(im.getSafety()) {
            if(im.getEncodersDriveStart()) {
                em.setPosition(50);
            }
            else if(im.getEncodersDriveTick()) {
                //Set position uses Position mode on TalonSRX, so the robot is already moving.
            }
            else if(im.getDriveStraightStart()) {
                driveStraightController.setSetpoint(gyro.getNormalizedAngle());
                driveStraightController.enable();
            }
            else if(im.getDriveStraightTick()) {
                drive(im.getDrive(), driveStraightController.get());
            }
            else if(im.getDriveStraightRelease()) {
                driveStraightController.reset();
                driveStraightController.disable();
            }
            else if(im.getTurnToAngleStart()) {
                turnToAngleController.setSetpoint(Utils.normalizeAngle(gyro.getNormalizedAngle() + 90));
                turnToAngleController.enable();
            }
            else if(im.getTurnToAngleTick()) {
                drive(0, turnToAngleController.get());
            }
            else if(im.getTurnToAngleRelease()) {
                turnToAngleController.reset();
                turnToAngleController.disable();
            }
            else {
                drive(im.getDrive(), im.getTurn());
            }
        }
        else {
            if(!im.getMotionProfileTick())
                drive(0, 0);
        }
    }

    /**
     * Applies power to the Drivetrain motors to move the robot.
     * @param drive - the power to send to move forwards and backwards. 1 is full speed forwards, -1 is full speed backwards
     * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
     */
    public void drive(double drive, double turn) {
        left1.set(ControlMode.PercentOutput, drive - turn);
        right1.set(ControlMode.PercentOutput, drive + turn);
    }

    /**
     * Applies power to the Drivetrain motors to move the robot in the context of Motion Profiling
     * @param left - the power to send to move the left side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
     * @param right - the power to send to move the right side of the drivetrain. 1 is full speed forwards, -1 is full speed backwards
     * @param turn - the power to send to turn the robot. 1 is full speed to the right, -1 is full speed to the left
     */
    public void driveMP(double left, double right, double turn) {
        left1.set(ControlMode.PercentOutput, left + turn);
        right1.set(ControlMode.PercentOutput, right - turn);
    }

    public void logSmartDashboard() {
        em.logSmartDashboard();
        gyro.logSmartDashbard();
    }
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7
}