package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Utility;

import java.awt.*;

public class InputManager {

    Joystick leftStick;
    Joystick rightStick;
    Joystick auxStick;

    boolean elevatorOverrideToggle;
    boolean turningOverrideToggle;
    boolean tippingOverrideToggle;

    public InputManager(){
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        auxStick = new Joystick(5);
    }

    public boolean getSafety() {
        return leftStick.getRawButton(ButtonMap.SAFETY);
    }
    public double getDrive() {
        return -leftStick.getY() * Math.abs(leftStick.getY());
    }
    public double getTurn() {
        return rightStick.getX() * Math.abs(rightStick.getX());
    }
    public boolean getIntake(){
        return auxStick.getRawButton(ButtonMap.INTAKE);
    }
    public boolean getEvict() {
        return auxStick.getRawButton(ButtonMap.EVICT);
    }
    public boolean getElevatorSafety() { return auxStick.getRawButton(ButtonMap.SAFETY); }
    public double getElevator(){
        return getElevatorSafety() ? (auxStick.getY() + 1) / 2 : 0;
    }
    public boolean getElevator5() { return auxStick.getRawButton(ButtonMap.ELEVATOR_5);}
    public boolean getElevator70() { return auxStick.getRawButton(ButtonMap.ELEVATOR_70); }
    public boolean getMotionProfileStart(){
        return auxStick.getRawButtonPressed(ButtonMap.MOTION_PROFILE);
    }
    public boolean getMotionProfileTick(){
        return auxStick.getRawButton(ButtonMap.MOTION_PROFILE);
    }
    public boolean getEncodersDriveStart() {
    	return auxStick.getRawButtonPressed(ButtonMap.ENCODER_DRIVE);
    }
    public boolean getEncodersDriveTick() {
    	return auxStick.getRawButton(ButtonMap.ENCODER_DRIVE);
    }
    public boolean getTurnToAngleStart() {
    	return rightStick.getRawButtonPressed(ButtonMap.TURN_TO_ANGLE);
    }
    public boolean getTurnToAngleTick() {
    	return rightStick.getRawButton(ButtonMap.TURN_TO_ANGLE);
    }
    public boolean getTurnToAngleRelease() {
    	return rightStick.getRawButtonReleased(ButtonMap.TURN_TO_ANGLE);
    }
    public boolean getArm90Degree(){
        return auxStick.getRawButtonPressed(ButtonMap.ARM_UP);
    }
    public boolean getArm45Degree(){
        return auxStick.getRawButtonPressed(ButtonMap.ARM_MID);
    }
    public boolean getArm0Degree(){
        return auxStick.getRawButtonPressed(ButtonMap.ARM_DOWN);
    }
    public boolean getUltraSonicStart() {
    	return auxStick.getRawButtonPressed(ButtonMap.ULTRASONIC);
    }
    public boolean getUltraSonicTick() {
    	return auxStick.getRawButton(ButtonMap.ULTRASONIC);
    }
    public boolean getUltraSonicReleased() {
    	return auxStick.getRawButtonReleased(ButtonMap.ULTRASONIC);
    }
    public boolean getClimber() {
    	return auxStick.getRawButton(ButtonMap.CLIMBER);
    }
    public boolean getKick() {
    	return rightStick.getRawButton(11);
    }
    public boolean unKick() {
    	return auxStick.getRawButton(-1);
    }
    public boolean getClimberRelease(){
        return Utility.getUserButton();
    }
    public double getArmPosition() {
    	return (auxStick.getZ() + 1) / 2;
    }
    public boolean getElevatorOverride(){
        return auxStick.getRawButtonPressed (ButtonMap.ELEVATOR_OVERRIDE) ? elevatorOverrideToggle = !elevatorOverrideToggle : elevatorOverrideToggle;
    }
    public boolean getDriveStraightOverride(){
        return leftStick.getRawButtonPressed (ButtonMap.DRIVE_STRAIGHT_OVERRIDE) ? turningOverrideToggle = !turningOverrideToggle: turningOverrideToggle;
    }
    public boolean getTipCorrectionOverride(){
        return leftStick.getRawButtonPressed (ButtonMap.TIP_CORRECTION_OVERRIDE) ? tippingOverrideToggle = !tippingOverrideToggle: tippingOverrideToggle;
    }
}
