package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.autonomous.sections.Drive;
import org.usfirst.frc.team1086.autonomous.sections.DriveDistance;
import org.usfirst.frc.team1086.autonomous.sections.MotionProfiler;
import org.usfirst.frc.team1086.autonomous.sections.TurnToAngleSection;
import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.FieldMap;

import java.util.ArrayList;

public class AutonomousStarter {
    Side switchSide;
    Side scaleSide;
    Side robotStartSide;
    Strategy selectedStrategy;
    SendableChooser<Strategy> strategyChooser = new SendableChooser<>();
    SendableChooser<Side> sideChooser = new SendableChooser<>();

    AutonomousManager testAuto;
    AutonomousManager centerLeftSwitchEnc;
    AutonomousManager centerLeftSwitchMP;
    AutonomousManager centerRightSwitchEnc;
    AutonomousManager centerRightSwitchMP;
    AutonomousManager leftLeftSwitchSideEnc;
    AutonomousManager leftLeftSwitchSideMP;
    AutonomousManager rightRightSwitchSideEnc;
    AutonomousManager rightRightSwitchSideMP;
    AutonomousManager leftLeftSwitchBackEnc;
    AutonomousManager leftLeftSwitchBackMP;
    AutonomousManager rightRightSwitchBackEnc;
    AutonomousManager rightRightSwitchBackMP;
    AutonomousManager leftLeftScaleMP;
    AutonomousManager rightRightScaleMP;
    AutonomousManager leftRightSwitchFrontMP;
    AutonomousManager rightLeftSwitchFrontMP;
    AutonomousManager leftRightSwitchBackMP;
    AutonomousManager rightLeftSwitchBackMP;

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
        sideChooser.addObject("Left", Side.LEFT);
        sideChooser.addObject("Center", Side.CENTER);
        sideChooser.addObject("Right", Side.RIGHT);
        SmartDashboard.putData(sideChooser);

        strategyChooser.addObject("Drive Forward", Strategy.DRIVEFORWARD);
        strategyChooser.addObject("Switch Only If Correct Side", Strategy.SWITCH_SAME_SIDE);
        strategyChooser.addObject("Switch Regardless of Side", Strategy.SWITCH_ALWAYS);
        strategyChooser.addObject("Scale Only If Correct Side", Strategy.SCALE_SAME_SIDE);
        strategyChooser.addObject("Scale Regardless of Side", Strategy.SCALE_ALWAYS);
        strategyChooser.addDefault("Switch, Scale, or Both, whichever is on the correct side", Strategy.SWITCH_OR_SCALE_SAME_SIDE);
        strategyChooser.addObject("Switch then Scale", Strategy.SWITCH_THEN_SCALE);
        SmartDashboard.putData(strategyChooser);

        testAuto = new AutonomousManager();
        testAuto.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(50, 0, 0)
        }));
        testAuto.addSection(new Drive(20, 0, 0));

        centerLeftSwitchEnc = new AutonomousManager();
        centerLeftSwitchEnc.addSection(new DriveDistance(50));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerLeftSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_HORIZONTAL));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(90));
        centerLeftSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_FORWARD - 50));
        centerLeftSwitchEnc.addSection(new Drive(20, 0,0 ));

        centerLeftSwitchMP = new AutonomousManager();
        centerLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD - Constants.ROBOT_LENGTH, -FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }));
        centerLeftSwitchMP.addSection(new Drive(20, 0,0));

        centerRightSwitchEnc = new AutonomousManager();
        centerRightSwitchEnc.addSection(new DriveDistance(50));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(90));
        centerRightSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_HORIZONTAL));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerRightSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_FORWARD - 50 - Constants.ROBOT_LENGTH));
        centerRightSwitchEnc.addSection(new Drive(20, 0,0 ));

        centerRightSwitchMP = new AutonomousManager();
        centerRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD - Constants.ROBOT_LENGTH, FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }));
        centerRightSwitchMP.addSection(new Drive(20, 0,0));

        leftLeftSwitchSideEnc = new AutonomousManager();
        leftLeftSwitchSideEnc.addSection(new DriveDistance(FieldMap.LEFT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH));
        leftLeftSwitchSideEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchSideEnc.addSection(new DriveDistance(FieldMap.LEFT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH));
        leftLeftSwitchSideEnc.addSection(new Drive(0, 0, 0));

        leftLeftSwitchSideMP = new AutonomousManager();
        leftLeftSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_SIDE_WALL_START_TURN - Constants.ROBOT_HALF_WIDTH, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_WIDTH, FieldMap.LEFT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(90))
        }));
        leftLeftSwitchSideMP.addSection(new Drive(0, 0, 0));

        rightRightSwitchSideEnc = new AutonomousManager();
        rightRightSwitchSideEnc.addSection(new DriveDistance(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH));
        rightRightSwitchSideEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchSideEnc.addSection(new DriveDistance(FieldMap.RIGHT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH));
        rightRightSwitchSideEnc.addSection(new Drive(20, 0, 0));

        rightRightSwitchSideMP = new AutonomousManager();
        rightRightSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_START_TURN - Constants.ROBOT_HALF_WIDTH, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_SWITCH_SIDE_WALL_HORIZONTAL + Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90))
        }));
        rightRightSwitchSideMP.addSection(new Drive(20, 0, 0));

        leftLeftSwitchBackEnc = new AutonomousManager();
        leftLeftSwitchBackEnc.addSection(new DriveDistance(FieldMap.LEFT_SWITCH_BACK_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH));
        leftLeftSwitchBackEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackEnc.addSection(new DriveDistance(FieldMap.LEFT_SWITCH_BACK_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH));
        leftLeftSwitchBackEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackEnc.addSection(new Drive(20, 0, 0));

        leftLeftSwitchBackMP = new AutonomousManager();
        leftLeftSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.LEFT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH, Pathfinder.d2r(90)),
                new Waypoint(FieldMap.LEFT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.LEFT_SWITCH_BACK_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH, Pathfinder.d2r(90))
        }));
        leftLeftSwitchBackMP.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackMP.addSection(new Drive(20, 0, 0));

        rightRightSwitchBackEnc = new AutonomousManager();
        rightRightSwitchBackEnc.addSection(new DriveDistance(FieldMap.RIGHT_SWITCH_BACK_WALL_FORWARD));
        rightRightSwitchBackEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackEnc.addSection(new DriveDistance(FieldMap.RIGHT_SWITCH_BACK_WALL_HORIZONTAL - Constants.ROBOT_HALF_WIDTH));
        rightRightSwitchBackEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackEnc.addSection(new Drive(20, 0, 0));

        rightRightSwitchBackMP = new AutonomousManager();
        rightRightSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.RIGHT_SWITCH_SIDE_WALL_HORIZONTAL + Constants.ROBOT_HALF_WIDTH, Pathfinder.d2r(-90)),
                new Waypoint(FieldMap.RIGHT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.RIGHT_SWITCH_BACK_WALL_HORIZONTAL + Constants.ROBOT_HALF_WIDTH, Pathfinder.d2r(-90))
        }));
        rightRightSwitchBackMP.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackMP.addSection(new Drive(0, 0, 0));

        leftLeftScaleMP = new AutonomousManager();
        leftLeftScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD_START_TURN - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD - Constants.ROBOT_HALF_WIDTH, FieldMap.LEFT_SCALE_HORIZONTAL - Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(90))
        }));
        leftLeftScaleMP.addSection(new Drive(20, 0, 0));

        rightRightScaleMP = new AutonomousManager();
        rightRightScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD_START_TURN - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD - Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_SCALE_HORIZONTAL - Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90))
        }));
        rightRightScaleMP.addSection(new Drive(20, 0, 0));

        leftRightSwitchFrontMP = new AutonomousManager();
        leftRightSwitchFrontMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_FAR_SWITCH_FRONT_FORWARD - Constants.ROBOT_LENGTH, FieldMap.LEFT_FAR_SWITCH_FRONT_HORIZONTAL - Constants.ROBOT_HALF_WIDTH, 0)
        }));
        leftRightSwitchFrontMP.addSection(new Drive(20, 0, 0));

        rightLeftSwitchFrontMP = new AutonomousManager();
        rightLeftSwitchFrontMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_FRONT_FORWARD - Constants.ROBOT_LENGTH, FieldMap.RIGHT_FAR_SWITCH_FRONT_HORIZONTAL + Constants.ROBOT_HALF_WIDTH, 0)
        }));
        rightLeftSwitchFrontMP.addSection(new Drive(20, 0, 0));

        leftRightSwitchBackMP = new AutonomousManager();
        leftRightSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.LEFT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.LEFT_SWITCH_BACK_WALL_HORIZONTAL - Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(90)),
                new Waypoint(FieldMap.LEFT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.LEFT_FAR_SWITCH_BACK_HORIZONTAL - Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(90))
        }));
        leftRightSwitchBackMP.addSection(new TurnToAngleSection(90));
        leftRightSwitchBackMP.addSection(new Drive(20, 0, 0));

        rightLeftSwitchBackMP = new AutonomousManager();
        rightLeftSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_SWITCH_BACK_WALL_HORIZONTAL + Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90)),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_FAR_SWITCH_BACK_HORIZONTAL + Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90))
        }));
        rightLeftSwitchBackMP.addSection(new TurnToAngleSection(-90));
        rightLeftSwitchBackMP.addSection(new Drive(20, 0, 0));
    }
    
    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and
     * calls the appropriate auto mode.
 1    */
    public AutonomousManager start() {
        //remove the comments for actual competition

        /*
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'L') {
                switchSide = Side.LEFT;
            } else {
                switchSide = Side.RIGHT;
            }
            if (gameData.charAt(1) == 'L') {
                scaleSide = Side.LEFT;
            } else {
                scaleSide = Side.RIGHT;
            }
        }
        robotStartSide = sideChooser.getSelected();
        selectedStrategy = strategyChooser.getSelected();
        AutonomousManager auto = selectedStrategy.getAutoModeToRun();
        return auto; */

        return leftLeftSwitchSideMP;
    }
}

enum Side {
    LEFT, CENTER, RIGHT;
}

enum Strategy {
    DRIVEFORWARD, SWITCH_SAME_SIDE, SWITCH_ALWAYS, SCALE_SAME_SIDE, SCALE_ALWAYS, SWITCH_OR_SCALE_SAME_SIDE, SWITCH_THEN_SCALE;
    ArrayList<AutonomousManager> autoModes = new ArrayList<>();
    Strategy(){

    }

    public void addAutoMode(AutonomousManager autoMode){
        autoModes.add(autoMode);
    }

    public AutonomousManager getAutoModeToRun(){
        // Add logic later

        return autoModes.get(0);
    }
}