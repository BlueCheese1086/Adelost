package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.autonomous.sections.Drive;
import org.usfirst.frc.team1086.autonomous.sections.DriveDistance;
import org.usfirst.frc.team1086.autonomous.sections.MotionProfiler;
import org.usfirst.frc.team1086.autonomous.sections.TurnToAngleSection;
import org.usfirst.frc.team1086.robot.FieldMap;

import java.util.ArrayList;

<<<<<<< HEAD
    public AutonomousStarter getInstance() {
        return instance;
    }
=======
public class AutonomousStarter {
    Side switchSide;
    Side scaleSide;
    Side robotStartSide;
    Strategy selectedStrategy;
    SendableChooser<Strategy> strategyChooser = new SendableChooser<>();
    SendableChooser<Side> sideChooser = new SendableChooser<>();
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7

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

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
<<<<<<< HEAD
=======
        sideChooser.addObject("Left", Side.LEFT);
        sideChooser.addObject("Center", Side.CENTER);
        sideChooser.addObject("Right", Side.RIGHT);
        SmartDashboard.putData(sideChooser);
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7

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
                new Waypoint(39.4, 39.4 / 2, Pathfinder.d2r(45)),
                new Waypoint(39.4 * 2, 39.4, Pathfinder.d2r(0))
        }));
        testAuto.addSection(new Drive(0, 0, 0));

        centerLeftSwitchEnc = new AutonomousManager();
        centerLeftSwitchEnc.addSection(new DriveDistance(50));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerLeftSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_HORIZONTAL));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(90));
        centerLeftSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_FORWARD - 50));
        centerLeftSwitchEnc.addSection(new Drive(0, 0,0 ));

        centerLeftSwitchMP = new AutonomousManager();
        centerLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD / 2, -FieldMap.CENTER_SWITCH_WALL_HORIZONTAL / 2, Pathfinder.d2r(-45)),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD, -FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }));
        centerLeftSwitchMP.addSection(new Drive(0, 0,0));

        centerRightSwitchEnc = new AutonomousManager();
        centerRightSwitchEnc.addSection(new DriveDistance(50));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(90));
        centerRightSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_HORIZONTAL));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerRightSwitchEnc.addSection(new DriveDistance(FieldMap.CENTER_SWITCH_WALL_FORWARD - 50));
        centerRightSwitchEnc.addSection(new Drive(0, 0,0 ));

        centerRightSwitchMP = new AutonomousManager();
        centerRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD / 2, FieldMap.CENTER_SWITCH_WALL_HORIZONTAL / 2, Pathfinder.d2r(45)),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD, FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }));
        centerRightSwitchMP.addSection(new Drive(0, 0,0));

        leftLeftSwitchSideEnc = new AutonomousManager();
        leftLeftSwitchSideEnc.addSection(new DriveDistance(166 - 32 / 2));
        leftLeftSwitchSideEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchSideEnc.addSection(new DriveDistance(54 - 28 / 2));
        leftLeftSwitchSideEnc.addSection(new Drive(0, 0, 0));

        leftLeftSwitchSideMP = new AutonomousManager();
        leftLeftSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(100, 0, 0),
                new Waypoint(166 - 32 / 2, 39, Pathfinder.d2r(90))
        }));
        leftLeftSwitchSideMP.addSection(new Drive(0, 0, 0));

        rightRightSwitchSideEnc = new AutonomousManager();
        rightRightSwitchSideEnc.addSection(new DriveDistance(166 - 32 / 2));
        rightRightSwitchSideEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchSideEnc.addSection(new DriveDistance(54 - 28 / 2));
        rightRightSwitchSideEnc.addSection(new Drive(0, 0, 0));

        rightRightSwitchSideMP = new AutonomousManager();
        rightRightSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(100, 0, 0),
                new Waypoint(166 - 32 / 2, -39, Pathfinder.d2r(-90))
        }));
        rightRightSwitchSideMP.addSection(new Drive(0, 0, 0));

        leftLeftSwitchBackEnc = new AutonomousManager();
        leftLeftSwitchBackEnc.addSection(new DriveDistance(194- 32 / 2 + 5));
        leftLeftSwitchBackEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackEnc.addSection(new DriveDistance(95 - 28 / 2));
        leftLeftSwitchBackEnc.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackEnc.addSection(new Drive(0, 0, 0));

        leftLeftSwitchBackMP = new AutonomousManager();
        leftLeftSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(166 - 32 / 2, 0, 0),
                new Waypoint(194 + 32 / 2, 54 - 28 / 2, Pathfinder.d2r(90)),
                new Waypoint(194 + 32 / 2, 95 - 28 / 2, Pathfinder.d2r(90))
        }));
        leftLeftSwitchBackMP.addSection(new TurnToAngleSection(90));
        leftLeftSwitchBackMP.addSection(new Drive(0, 0, 0));

        rightRightSwitchBackEnc = new AutonomousManager();
        rightRightSwitchBackEnc.addSection(new DriveDistance(194 - 32 / 2 + 5));
        rightRightSwitchBackEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackEnc.addSection(new DriveDistance(95 - 28 / 2));
        rightRightSwitchBackEnc.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackEnc.addSection(new Drive(0, 0, 0));

        rightRightSwitchBackMP = new AutonomousManager();
        rightRightSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(166 - 32 / 2, 0, 0),
                new Waypoint(194 + 32 / 2, -54 + 28 / 2, Pathfinder.d2r(90)),
                new Waypoint(194 + 32 / 2, -95 + 28 / 2, Pathfinder.d2r(90))
        }));
        rightRightSwitchBackMP.addSection(new TurnToAngleSection(-90));
        rightRightSwitchBackMP.addSection(new Drive(0, 0, 0));
    }
    
    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and
     * calls the appropriate auto mode.
     */
    public AutonomousManager start() {
<<<<<<< HEAD
=======
        //remove the comments for actual competition
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7

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

        return testAuto;
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