package org.usfirst.frc.team1086.robot;

public class Constants {  
	
    /**
     * Encoders and Drivetrain constants
     */
	public static final double WHEEL_DIAMETER = 5;
	public static final int ALLOWABLE_ERROR = 0;

	public static final double ENCODER_KP = .1;
	public static final double ENCODER_KI = 0;
	public static final double ENCODER_KD = 0;
	public static final double ENCODER_KF = 0;
  
    public static final double DRIVE_STRAIGHT_KP = .02;
    public static final double DRIVE_STRAIGHT_KI = 0;
    public static final double DRIVE_STRAIGHT_KD = .075;

	public static final double TURN_TO_ANGLE_KP = .02;
	public static final double TURN_TO_ANGLE_KI = 0;
	public static final double TURN_TO_ANGLE_KD = .045;

	/**
	 * Other constants
	 */
	public static final double POTENTIOMETER_STRING_LENGTH = 50;
	public static final double POTENTIOMETER_STRING_OFFSET = 2;
  public static final double ELEVATOR_HEIGHT = 90 - 9;
  public static final double ELEVATOR_GEAR_CIRCUMFERENCE = 4 * Math.PI;//FIX THIS    
}
