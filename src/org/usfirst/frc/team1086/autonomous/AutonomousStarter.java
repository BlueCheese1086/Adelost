package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.team1086.autonomous.sections.Drive;
import org.usfirst.frc.team1086.autonomous.sections.DriveDistance;
import org.usfirst.frc.team1086.autonomous.sections.TurnToAngleSection;

import java.util.ArrayList;

public class AutonomousStarter {
    // TODO: Create actual auto modes
    public static Side switchSide;
    public static Side scaleSide;
    SendableChooser<Strategy> chooser = new SendableChooser<>();
    
    /* Declare AutonomousManager routines here */
    AutonomousManager driveForward;
    AutonomousManager switchAuto;
    AutonomousManager scale;
    AutonomousManager fly;
    AutonomousManager dig;

    AutonomousManager testAuto;

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
        chooser.addObject("Switch", Strategy.SWITCH);
        chooser.addObject("Scale",  Strategy.SCALE);
        testAuto = new AutonomousManager();
        testAuto.addSection(new DriveDistance(24));
        testAuto.addSection(new TurnToAngleSection(90));
        testAuto.addSection(new DriveDistance(24));
        testAuto.addSection(new TurnToAngleSection(90));
        testAuto.addSection(new DriveDistance(24));
        testAuto.addSection(new TurnToAngleSection(90));
        testAuto.addSection(new DriveDistance(24));
        testAuto.addSection(new TurnToAngleSection(90)); 
        testAuto.addSection(new Drive(0, 0, 0));
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
        return testAuto;
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