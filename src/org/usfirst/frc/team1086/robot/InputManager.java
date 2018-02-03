package org.usfirst.frc.team1086.robot;

import edu.wpi.first.wpilibj.Joystick;

public class InputManager {
    private static InputManager instance;

    Joystick leftStick;
    Joystick rightStick;
    Joystick auxStick;

    static {
        instance = new InputManager();
    }

    private InputManager() {
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        auxStick = new Joystick(5);
        instance = this;
    }

    public static InputManager getInstance() {
        return instance;
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
    public boolean getIntake() {
        return auxStick.getRawButton(ButtonMap.INTAKE);
    }
    public boolean getEvict() {
        return auxStick.getRawButton(ButtonMap.EVICT);
    }
    public boolean getMotionProfileStart() {
        return auxStick.getRawButtonPressed(ButtonMap.MOTION_PROFILE);
    }
    public boolean getMotionProfileTick() {
        return auxStick.getRawButton(ButtonMap.MOTION_PROFILE);
    }
}
