package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputManager {

    Joystick leftStick;
    Joystick rightStick;
    Joystick auxStick;

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
    public boolean getDriveStraightStart() {
    	return leftStick.getRawButtonPressed(ButtonMap.DRIVE_STRAIGHT);
    }
    public boolean getDriveStraightTick() {
    	return leftStick.getRawButton(ButtonMap.DRIVE_STRAIGHT);
    }
    public boolean getDriveStraightRelease() {
    	return leftStick.getRawButtonReleased(ButtonMap.DRIVE_STRAIGHT);
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
}
