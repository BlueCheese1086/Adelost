package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team1086.autonomous.sections.Drive;
import org.usfirst.frc.team1086.autonomous.sections.DriveDistance;
import org.usfirst.frc.team1086.autonomous.sections.MotionProfiler;
import org.usfirst.frc.team1086.autonomous.sections.TurnToAngleSection;

import java.nio.file.Path;
import java.util.ArrayList;

public class AutonomousStarter {
    // TODO: Create actual auto modes
    public static Side switchSide;
    public static Side scaleSide;
    SendableChooser<Strategy> chooser = new SendableChooser<>();

    AutonomousManager testAuto;
    AutonomousManager centerLeftSwitchEnc;
    AutonomousManager centerLeftSwitchMP;
    AutonomousManager centerRightSwitchEnc;
    AutonomousManager centerRightSwitchMP;
    AutonomousManager leftLeftSwitchSideEnc;
    AutonomousManager leftLeftSwitchSideMP;
    AutonomousManager rightRightSwitchSideEnc;
    AutonomousManager rightRightSwitchSideMP;

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
        chooser.addObject("Switch", Strategy.SWITCH);
        chooser.addObject("Scale",  Strategy.SCALE);
        testAuto = new AutonomousManager();
        testAuto.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(30, 15, Pathfinder.d2r(45)),
                new Waypoint(60, 30, Pathfinder.d2r(0))
        }));
        testAuto.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(20,0, 0),
                new Waypoint(40, -20, Pathfinder.d2r(-90))
        }));
        testAuto.addSection(new Drive(0, 0, 0));

        centerLeftSwitchEnc = new AutonomousManager();
        centerLeftSwitchEnc.addSection(new DriveDistance(50));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerLeftSwitchEnc.addSection(new DriveDistance(68));
        centerLeftSwitchEnc.addSection(new TurnToAngleSection(90));
        centerLeftSwitchEnc.addSection(new DriveDistance(90));
        centerLeftSwitchEnc.addSection(new Drive(0, 0,0 ));

        centerLeftSwitchMP = new AutonomousManager();
        centerLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(70, -34, Pathfinder.d2r(-45)),
                new Waypoint(140, -68, Pathfinder.d2r(0))
        }));
        centerLeftSwitchMP.addSection(new Drive(0, 0,0));

        centerRightSwitchEnc = new AutonomousManager();
        centerRightSwitchEnc.addSection(new DriveDistance(50));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(90));
        centerRightSwitchEnc.addSection(new DriveDistance(68));
        centerRightSwitchEnc.addSection(new TurnToAngleSection(-90));
        centerRightSwitchEnc.addSection(new DriveDistance(90));
        centerRightSwitchEnc.addSection(new Drive(0, 0,0 ));

        centerRightSwitchMP = new AutonomousManager();
        centerRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(70, 34, Pathfinder.d2r(45)),
                new Waypoint(140, 68, Pathfinder.d2r(0))
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
    }
    
    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and
     * calls the appropriate auto mode.
     */
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
            return decideAuto();
            
        } */
        return leftLeftSwitchSideMP;
    }

    /*
    public AutonomousManager decideAuto() {
        if (switchSide == Side.LEFT) {
            if (scaleSide == Side.LEFT) {
                return scale; //left,left
            } else {
                return switchAuto; //left,right
            }
        } else {
            if (scaleSide == Side.LEFT) {
                return scale; //right,left
            } else {
                return switchAuto; //right,right
            }
        }
    } */
}

enum Side {
    LEFT, RIGHT;
}

enum Strategy {
    DRIVEFORWARD, SWITCH, SCALE;
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