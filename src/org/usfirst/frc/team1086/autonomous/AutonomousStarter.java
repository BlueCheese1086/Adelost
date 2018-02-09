package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutonomousStarter {
    // TODO: Create actual auto modes
    public static Side switchSide;
    public static Side scaleSide;
    SendableChooser<String> chooser = new SendableChooser<>();
    
    /* Declare AutonomousManager routines here */
    AutonomousManager driveForward;
    AutonomousManager switchAuto;
    AutonomousManager scale;
    AutonomousManager fly;
    AutonomousManager dig;
    
    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
        chooser.addObject("Switch", "switchAuto");
        chooser.addObject("Scale", "scale");
    }
    
    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and
     * calls the appropriate auto mode.
     */
    public AutonomousManager start() {
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
            
        }
        return driveForward;
    }
    
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
    }
}

enum Side {
    LEFT, RIGHT;
}

enum Strategy {
    SWITCH, SCALE;
}