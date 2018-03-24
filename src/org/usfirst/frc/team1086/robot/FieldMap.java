package org.usfirst.frc.team1086.robot;

public class FieldMap {
    //Width of the field along the driver station wall. The field is bent in. The robot is assumed to be set up
    //on the corner of the bend.
    public static final int DRIVER_STATION_FIELD_WIDTH = 264;

    /**
     * Coordinates relative to the Center starting position
     */
    public static final int CENTER_SWITCH_WALL_FORWARD = 140;
    public static final int CENTER_SWITCH_WALL_HORIZONTAL = 64;

    /**
     * Coordinates relative to the Left starting position
     */
    public static final int LEFT_SWITCH_SIDE_WALL_START_TURN = 100;
    public static final int LEFT_SWITCH_SIDE_WALL_FORWARD = 166;
    public static final int LEFT_SWITCH_SIDE_WALL_HORIZONTAL = 54;
    public static final int LEFT_SWITCH_BACK_WALL_FORWARD = 197;
    public static final int LEFT_SWITCH_BACK_WALL_HORIZONTAL = 95;
    public static final int LEFT_SCALE_FORWARD = 300; //Margin of error
    public static final int LEFT_SCALE_FORWARD_START_TURN = 100;
    public static final int LEFT_SCALE_HORIZONTAL = 50;
    public static final int LEFT_FAR_SWITCH_FRONT_FORWARD = CENTER_SWITCH_WALL_FORWARD;
    public static final int LEFT_FAR_SWITCH_FRONT_HORIZONTAL = DRIVER_STATION_FIELD_WIDTH / 2 + CENTER_SWITCH_WALL_HORIZONTAL;
    public static final int LEFT_FAR_SWITCH_BACK_FORWARD = LEFT_SWITCH_BACK_WALL_FORWARD;
    public static final int LEFT_FAR_SWITCH_BACK_HORIZONTAL = DRIVER_STATION_FIELD_WIDTH / 2 + CENTER_SWITCH_WALL_HORIZONTAL;

    /**
     * Coordinates relative to the Right starting position
     */
    public static final int RIGHT_SWITCH_SIDE_WALL_START_TURN = LEFT_SWITCH_SIDE_WALL_START_TURN;
    public static final int RIGHT_SWITCH_SIDE_WALL_FORWARD = LEFT_SWITCH_SIDE_WALL_FORWARD;
    public static final int RIGHT_SWITCH_SIDE_WALL_HORIZONTAL = -LEFT_SWITCH_SIDE_WALL_HORIZONTAL;
    public static final int RIGHT_SWITCH_BACK_WALL_FORWARD = LEFT_SWITCH_BACK_WALL_FORWARD;
    public static final int RIGHT_SWITCH_BACK_WALL_HORIZONTAL = -LEFT_SWITCH_BACK_WALL_HORIZONTAL;
    public static final int RIGHT_SCALE_FORWARD = LEFT_SCALE_FORWARD;
    public static final int RIGHT_SCALE_FORWARD_START_TURN = LEFT_SCALE_FORWARD_START_TURN;
    public static final int RIGHT_SCALE_HORIZONTAL = -LEFT_SCALE_HORIZONTAL;
    public static final int RIGHT_FAR_SWITCH_FRONT_FORWARD = LEFT_FAR_SWITCH_FRONT_FORWARD;
    public static final int RIGHT_FAR_SWITCH_FRONT_HORIZONTAL = -LEFT_FAR_SWITCH_FRONT_HORIZONTAL;
    public static final int RIGHT_FAR_SWITCH_BACK_FORWARD = LEFT_FAR_SWITCH_BACK_FORWARD;
    public static final int RIGHT_FAR_SWITCH_BACK_HORIZONTAL = -LEFT_FAR_SWITCH_BACK_HORIZONTAL;
}
