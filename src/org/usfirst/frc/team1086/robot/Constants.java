package org.usfirst.frc.team1086.robot;

public class Constants {
	public static final int ROBOT_LENGTH = 37;
	public static final int ROBOT_WIDTH = 35;
	public static final int ROBOT_HALF_LENGTH = ROBOT_LENGTH / 2;
	public static final int ROBOT_HALF_WIDTH = ROBOT_WIDTH / 2;
	
    /**
     * Encoders and Drivetrain constants
     */
	public static final double WHEEL_DIAMETER = 5 / 1.04;
	public static final int ALLOWABLE_ERROR = 0;

	public static final double ENCODER_KP = .1;
	public static final double ENCODER_KI = 0;
	public static final double ENCODER_KD = 0;
	public static final double ENCODER_KF = 0;
  
    public static final double DRIVE_STRAIGHT_KP = 0.01;
    public static final double DRIVE_STRAIGHT_KI = 0;
    public static final double DRIVE_STRAIGHT_KD = 0.01;

	public static final double TURN_TO_ANGLE_KP = .022;
	public static final double TURN_TO_ANGLE_KI = 0;
	public static final double TURN_TO_ANGLE_KD = .045;
	
	/**
   * Ultrasonic PID
   */
	
	public static final double ULTRASONIC_KP = 0;
	public static final double ULTRASONIC_KI = 0;
	public static final double ULTRASONIC_KD = 0;

	/**
	 * Elevator Constants
	 */
    public static final double ELEVATOR_HEIGHT = 75;
    public static final double ELEVATOR_GEAR_CIRCUMFERENCE = 1.22 * Math.PI;//FIX THIS
	public static final int ELEVATOR_PEAK_CURRENT = 20; //amps
    public static final double ELEVATOR_KP = 0.25;
    public static final double ELEVATOR_KI = 0;
    public static final double ELEVATOR_KD = 0.01;

	/**
	 * Miscellaneous Constants
	 */
	public static final int INTAKE_PEAK_CURRENT = 8;
	public static final int ARM_PEAK_CURRENT = 10;
	public static final int MAX_ARM_ENC_UNITS = 900;
	public static final double MAX_ARM_ANGLE = 360.0 * MAX_ARM_ENC_UNITS / 4096;

}
