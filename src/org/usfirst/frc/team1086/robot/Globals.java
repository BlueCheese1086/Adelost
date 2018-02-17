package org.usfirst.frc.team1086.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.usfirst.frc.team1086.MotionProfiling.MotionProfiling;
import org.usfirst.frc.team1086.subsystems.Arm;
import org.usfirst.frc.team1086.subsystems.Drivetrain;
import org.usfirst.frc.team1086.subsystems.Elevator;
import org.usfirst.frc.team1086.subsystems.Intake;
import org.usfirst.frc.team1086.subsystems.Ultrasonic;

import edu.wpi.first.networktables.NetworkTableEntry;

public class Globals {
	public static Drivetrain drivetrain;
	public static Elevator elevator;
	public static Arm arm;
	public static Intake intake;
	public static Ultrasonic ultrasonic;
	public static Gyro gyro;
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
