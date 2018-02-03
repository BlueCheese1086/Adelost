package org.usfirst.frc.team1086.autonomous;

public class AutonomousStarter {
    private static AutonomousStarter instance;

    static {
        instance = new AutonomousStarter();
    }

    public AutonomousStarter getInstance(){
        return instance;
    }

    /* Declare AutonomousManager routines here */
    AutonomousManager driveForward;

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes(){
    	
    }

    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and calls the appropriate auto mode.
     */
    public AutonomousManager start(){


        return driveForward;
    }
}
