package org.usfirst.frc.team1086.robot;

public class FieldMap {

    /**
     * Coordinates relative to the Left starting position
     */
    public static final int LEFT_SWITCH_SIDE_WALL_START_TURN = 116;
    public static final int LEFT_SWITCH_SIDE_WALL_FORWARD = 166;
    public static final int LEFT_SWITCH_SIDE_WALL_HORIZONTAL = 54;
    public static final int LEFT_SWITCH_BACK_WALL_FORWARD = 197;
    public static final int LEFT_SWITCH_BACK_WALL_HORIZONTAL = 95;
    public static final int LEFT_SCALE_FORWARD = 324;
    public static final int LEFT_SCALE_FORWARD_START_TURN = LEFT_SCALE_FORWARD - 40;
    public static final int LEFT_SCALE_HORIZONTAL = 54;

    /**
     * Coordinates relative to the Center starting position
     */
    public static final int CENTER_SWITCH_WALL_FORWARD = 140;
    public static final int CENTER_SWITCH_WALL_HORIZONTAL = 68;

    /**
     * Coordinates relative to the Right starting position
     */
    public static final int RIGHT_SWITCH_SIDE_WALL_START_TURN = LEFT_SWITCH_SIDE_WALL_START_TURN;
    public static final int RIGHT_SWITCH_SIDE_WALL_FORWARD = LEFT_SWITCH_SIDE_WALL_FORWARD;
    public static final int RIGHT_SWITCH_SIDE_WALL_HORIZONTAL = -LEFT_SWITCH_SIDE_WALL_HORIZONTAL;
    public static final int RIGHT_SWITCH_BACK_WALL_FORWARD = LEFT_SWITCH_BACK_WALL_FORWARD;
    public static final int RIGHT_SWITCH_BACK_WALL_HORIZONTAL = -LEFT_SWITCH_BACK_WALL_HORIZONTAL;
}
