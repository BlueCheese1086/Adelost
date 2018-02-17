package org.usfirst.frc.team1086.robot;

import org.usfirst.frc.team1086.CameraCalculator.PixyCamera;
import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Climber;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;
import org.usfirst.frc.team1086.subsystems.Ultrasonic;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Globals {
	public static Drivetrain drivetrain;
	public static Elevator elevator;
	public static Arm arm;
	public static Intake intake;
	public static Ultrasonic ultrasonic;
	public static Gyro gyro;
	public static Climber climber;
	public static PixyCamera pixy;
	public static InputManager im;
	public static NetworkTableEntry Heading, Speed, Acceleration, Left1Output, Right1Output, Left2Output, Right2Output, ElevatorHeight, ArmLocation;
	/*NetworkTableEntries (Put more if needed)
	* Entries are initialized in Drivetrain.java
	*/
	public static MotionProfiling mp;
	public static void init() {
        im = new InputManager();
		drivetrain = new Drivetrain();
		gyro = new Gyro();
		arm = new Arm();
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
		mp = new MotionProfiling();
		ultrasonic = new Ultrasonic(RobotMap.ULTRASONIC);
		drivetrain.init();

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
	}
}
