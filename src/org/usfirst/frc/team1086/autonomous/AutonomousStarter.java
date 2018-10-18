package org.usfirst.frc.team1086.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team1086.autonomous.sections.*;
import org.usfirst.frc.team1086.robot.Constants;
import org.usfirst.frc.team1086.robot.FieldMap;
import org.usfirst.frc.team1086.robot.Globals;

public class AutonomousStarter {
    Strategy selectedStrategy;
    Side startSide;
    Side switchSide;
    Side scaleSide;
    SendableChooser<Strategy> strategyChooser = new SendableChooser<>();
    SendableChooser<Side> sideChooser = new SendableChooser<>();
    SendableChooser<Side> switchSideChooser = new SendableChooser<>();
    SendableChooser<Side> scaleSideChooser = new SendableChooser<>();

    AutonomousManager testAuto;
    AutonomousManager driveForward;
    AutonomousManager centerLeftSwitchMP;
    AutonomousManager centerRightSwitchMP;
    AutonomousManager leftLeftSwitchSideMP;
    AutonomousManager rightRightSwitchSideMP;
    AutonomousManager leftLeftScaleMP;
    AutonomousManager rightRightScaleMP;
    AutonomousManager leftRightSwitchFrontMP;
    AutonomousManager rightLeftSwitchFrontMP;
    AutonomousManager leftRightSwitchBackMP;
    AutonomousManager rightLeftSwitchBackMP;
    AutonomousManager leftLeftScaleLeftSwitchMP;
    AutonomousManager rightRightScaleRightSwitchMP;
    AutonomousManager leftRightScaleMP;
    AutonomousManager rightLeftScaleMP;

    /**
     * Initializes the sections of all the auto modes.
     */
    public void initAutoModes() {
        sideChooser.addObject("Robot Left", Side.LEFT);
        sideChooser.addObject("Robot Center", Side.CENTER);
        sideChooser.addObject("Robot Right", Side.RIGHT);
        SmartDashboard.putData("Robot side chooser", sideChooser);
        
        switchSideChooser.addObject("Switch Left", Side.LEFT);
        switchSideChooser.addObject("Switch Right", Side.RIGHT);
        switchSideChooser.addDefault("Read from FMS", null);
        SmartDashboard.putData("Switch side chooser", switchSideChooser);

        scaleSideChooser.addObject("Scale Left", Side.LEFT);
        scaleSideChooser.addObject("Scale Right", Side.RIGHT);
        scaleSideChooser.addDefault("Read from FMS", null);
        SmartDashboard.putData("Scale side chooser", scaleSideChooser);

        strategyChooser.addObject("Drive Forward", Strategy.DRIVE_FORWARD);
        strategyChooser.addObject("Switch Only If Correct Side", Strategy.SWITCH_SAME_SIDE);
        strategyChooser.addObject("Switch Regardless of Side", Strategy.SWITCH_ALWAYS);
        strategyChooser.addObject("Scale Only If Correct Side", Strategy.SCALE_SAME_SIDE);
        strategyChooser.addObject("Scale Regardless of Side (Do not run)", Strategy.SCALE_ALWAYS);
        strategyChooser.addDefault("Switch or Scale (prioritizing switch)", Strategy.SWITCH_OR_SCALE_SAME_SIDE);
        strategyChooser.addObject("Switch or Scale (prioritizing scale)", Strategy.SCALE_OR_SWITCH_SAME_SIDE);
        strategyChooser.addObject("Scale, Switch, or Both (On same side, ideal side auto)", Strategy.SWITCH_AND_SCALE_SAME_SIDE);
        strategyChooser.addObject("Scale, Switch, or Both (Both on same side, only cross on scale (experimental) )", Strategy.SWITCH_AND_SCALE);
        strategyChooser.addObject("Switch then Scale (Do not run)", Strategy.SWITCH_THEN_SCALE);
        strategyChooser.addObject("Network Tables Profile", Strategy.NETWORK_PROFILE);
        strategyChooser.addObject("Test Auto", Strategy.EXPERIMENTAL_AUTO);
        SmartDashboard.putData("Strategy picker", strategyChooser);

        testAuto = new AutonomousManager();
        testAuto.addSection(new NetworkProfile());
        testAuto.addSection(new Drive(20, 0, 0));

        driveForward = new AutonomousManager();
        driveForward.addSection(new Drive(4000, 0.4, 0));
        driveForward.addSection(new Drive(20, 0, 0));

        centerLeftSwitchMP = new AutonomousManager();
        centerLeftSwitchMP.addSection(new RunIntake(-0.25, 20));
        centerLeftSwitchMP.addSection(new ArmMover(45, 20));
        centerLeftSwitchMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD - Constants.ROBOT_LENGTH, -FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }, 15));
        centerLeftSwitchMP.addSection(new Drive(20, 0,0));
        centerLeftSwitchMP.addSection(new RunIntake(0.5, 300));
        centerLeftSwitchMP.addSection(new DriveDistance(-15, 2000));
        centerLeftSwitchMP.addSection(new ArmMover(0, 20));
        centerLeftSwitchMP.addSection(new ElevatorMover(0, 20));
        centerLeftSwitchMP.addSection(new Drive(20, 0,0));
        /*centerLeftSwitchMP.addSection(new TurnToAngleSection(180, 1000));
        centerLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(90, -68, 0)
        }));
        centerLeftSwitchMP.addSection(new TurnToAngleSection(180, 1000));
        centerLeftSwitchMP.addSection(new RunIntake(-.7, 40));
        centerLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(60, 0, 0)
        }));
        centerLeftSwitchMP.addSection(new Drive(20, 0.2, 0));
        centerLeftSwitchMP.addSection(new RunIntake(-0.7, 1500, true));
        centerLeftSwitchMP.addSection(new Drive(20, 0, 0));
        centerLeftSwitchMP.addSection(new RunIntake(-.25, 20));*/

        centerRightSwitchMP = new AutonomousManager();
        centerRightSwitchMP.addSection(new RunIntake(-0.25, 20));
        centerRightSwitchMP.addSection(new ArmMover(45, 20));
        centerRightSwitchMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.CENTER_SWITCH_WALL_FORWARD - Constants.ROBOT_LENGTH, FieldMap.CENTER_SWITCH_WALL_HORIZONTAL, Pathfinder.d2r(0))
        }, 15));
        centerRightSwitchMP.addSection(new Drive(20, 0,0));
        centerRightSwitchMP.addSection(new RunIntake(0.5, 300));
        centerRightSwitchMP.addSection(new DriveDistance(-15, 2000));
        centerRightSwitchMP.addSection(new ArmMover(0, 20));
        centerRightSwitchMP.addSection(new ElevatorMover(0, 20));
        centerRightSwitchMP.addSection(new Drive(20, 0,0));
        /*centerRightSwitchMP.addSection(new TurnToAngleSection(180, 1000));
        centerRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(90, 68, 0)
        }));
        centerRightSwitchMP.addSection(new TurnToAngleSection(180, 1000));
        centerRightSwitchMP.addSection(new RunIntake(-.7, 40));
        centerRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(60, 0, 0)
        }));
        centerRightSwitchMP.addSection(new Drive(20, 0.2, 0));
        centerRightSwitchMP.addSection(new RunIntake(-0.7, 1500, true));
        centerRightSwitchMP.addSection(new Drive(20, 0, 0));
        centerRightSwitchMP.addSection(new RunIntake(-.25, 20));/*/

        leftLeftSwitchSideMP = new AutonomousManager();
        leftLeftSwitchSideMP.addSection(new ArmMover(45, 20));
        leftLeftSwitchSideMP.addSection(new RunIntake(-0.25, 20));
        leftLeftSwitchSideMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, FieldMap.LEFT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_WIDTH - 6, Pathfinder.d2r(0))
        }, 25));
        leftLeftSwitchSideMP.addSection(new TurnToAngleSection(90, 1000));
        leftLeftSwitchSideMP.addSection(new Drive(20, 0,0));
        leftLeftSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(8, 0, 0)
        }));
        leftLeftSwitchSideMP.addSection(new Drive(20, 0, 0));
        leftLeftSwitchSideMP.addSection(new RunIntake(0.5, 300));
        leftLeftSwitchSideMP.addSection(new DriveDistance(-15, 2000));
        leftLeftSwitchSideMP.addSection(new ArmMover(0, 20));
        leftLeftSwitchSideMP.addSection(new ElevatorMover(0, 20));
        leftLeftSwitchSideMP.addSection(new Drive(20, 0, 0));

        rightRightSwitchSideMP = new AutonomousManager();
        rightRightSwitchSideMP.addSection(new ArmMover(45, 20));
        rightRightSwitchSideMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, FieldMap.RIGHT_SWITCH_SIDE_WALL_HORIZONTAL + Constants.ROBOT_WIDTH + 6, Pathfinder.d2r(0))
        }, 25));
        rightRightSwitchSideMP.addSection(new TurnToAngleSection(-90, 1000));
        rightRightSwitchSideMP.addSection(new Drive(20, 0,0));
        rightRightSwitchSideMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(8, 0, 0)
        }));
        rightRightSwitchSideMP.addSection(new Drive(20, 0, 0));
        rightRightSwitchSideMP.addSection(new RunIntake(0.5, 300));
        rightRightSwitchSideMP.addSection(new DriveDistance(-15, 2000));
        rightRightSwitchSideMP.addSection(new ArmMover(0));
        rightRightSwitchSideMP.addSection(new ElevatorMover(0));
        rightRightSwitchSideMP.addSection(new Drive(20, 0, 0));

        leftLeftScaleMP = new AutonomousManager();
        leftLeftScaleMP.addSection(new RunIntake(-0.25, 20));
        leftLeftScaleMP.addSection(new ArmMover(45, 20));
        leftLeftScaleMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD_START_TURN, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD - Constants.ROBOT_LENGTH, FieldMap.LEFT_SCALE_HORIZONTAL - Constants.ROBOT_HALF_WIDTH + 12, 0)
        }, 70, 2000));
        leftLeftScaleMP.addSection(new Drive(20, 0, 0));
        leftLeftScaleMP.addSection(new RunIntake(0.5, 200));
        leftLeftScaleMP.addSection(new ArmMover(60, 20));
        leftLeftScaleMP.addSection(new ElevatorMover(0, 20));
        leftLeftScaleMP.addSection(new DriveDistance(-12));
        leftLeftScaleMP.addSection(new TurnToAngleSection(180, 1000));
        leftLeftScaleMP.addSection(new RunIntake(-.8, 40));
        leftLeftScaleMP.addSection(new ArmMover(0, 40));
        leftLeftScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, -10, 0)
        }));
        leftLeftScaleMP.addSection(new Drive(20, 0.2, 0));
        leftLeftScaleMP.addSection(new RunIntake(-.35, 1400, true));
        leftLeftScaleMP.addSection(new ArmMover(45, 20));
        leftLeftScaleMP.addSection(new DriveDistance(-10, 1000));
        leftLeftScaleMP.addSection(new TurnToAngleSection(180, 1000));
        leftLeftScaleMP.addSection(new Drive(20, 0, 0));
        /*
        leftLeftScaleMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, -5, 0)
        }, 70));
        leftLeftScaleMP.addSection(new Drive(20, 0, 0));
        leftLeftScaleMP.addSection(new RunIntake(0.5, 500)); */


        rightRightScaleMP = new AutonomousManager();
        rightRightScaleMP.addSection(new RunIntake(-0.25, 20));
        rightRightScaleMP.addSection(new ArmMover(45, 20));
        rightRightScaleMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD_START_TURN, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD - Constants.ROBOT_LENGTH, FieldMap.RIGHT_SCALE_HORIZONTAL + Constants.ROBOT_HALF_WIDTH - 12, 0)
        }, 70, 2000));
        rightRightScaleMP.addSection(new Drive(20, 0, 0));
        rightRightScaleMP.addSection(new RunIntake(0.5, 500));
        rightRightScaleMP.addSection(new ArmMover(60, 20));
        rightRightScaleMP.addSection(new ElevatorMover(0, 20));
        rightRightScaleMP.addSection(new DriveDistance(-12));
        rightRightScaleMP.addSection(new TurnToAngleSection(180, 1000));
        rightRightScaleMP.addSection(new RunIntake(-.8, 40));
        rightRightScaleMP.addSection(new ArmMover(0, 40));
        rightRightScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, 10, 0)
        }));
        rightRightScaleMP.addSection(new Drive(20, 0.2, 0));
        rightRightScaleMP.addSection(new RunIntake(-.35, 1400, true));
        rightRightScaleMP.addSection(new ArmMover(45, 20));
        rightRightScaleMP.addSection(new DriveDistance(-10, 1000));
        rightRightScaleMP.addSection(new TurnToAngleSection(180, 1000));
        /*
        rightRightScaleMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, 5, 0)
        }, 70));
        rightRightScaleMP.addSection(new Drive(20, 0, 0));
        rightRightScaleMP.addSection(new RunIntake(0.5, 200));
        rightRightScaleMP.addSection(new DriveDistance(-10));
        rightRightScaleMP.addSection(new ArmMover(55, 20));
        rightRightScaleMP.addSection(new ElevatorMover(0, 20)); */
        rightRightScaleMP.addSection(new Drive(20, 0, 0));

        leftRightSwitchFrontMP = new AutonomousManager();
        leftRightSwitchFrontMP.addSection(new ArmMover(45, 20));
        leftRightSwitchFrontMP.addSection(new RunIntake(-.25,20));
        leftRightSwitchFrontMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_FAR_SWITCH_FRONT_FORWARD - Constants.ROBOT_LENGTH, FieldMap.LEFT_FAR_SWITCH_FRONT_HORIZONTAL - Constants.ROBOT_HALF_WIDTH, 0)
        }, 15, 1000));
        leftRightSwitchFrontMP.addSection(new Drive(20, 0, 0));
        leftRightSwitchFrontMP.addSection(new RunIntake(0.5, 300));
        leftRightSwitchFrontMP.addSection(new DriveDistance(-15, 2000));
        leftRightSwitchFrontMP.addSection(new ArmMover(0));
        leftRightSwitchFrontMP.addSection(new ElevatorMover(0));
        leftRightSwitchFrontMP.addSection(new Drive(20, 0, 0));

        rightLeftSwitchFrontMP = new AutonomousManager();
        rightLeftSwitchFrontMP.addSection(new ArmMover(45, 20));
        rightLeftSwitchFrontMP.addSection(new RunIntake(-.25, 20));
        rightLeftSwitchFrontMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_FRONT_FORWARD - Constants.ROBOT_LENGTH, FieldMap.RIGHT_FAR_SWITCH_FRONT_HORIZONTAL + Constants.ROBOT_HALF_WIDTH, 0)
        }, 15, 1000));
        rightLeftSwitchFrontMP.addSection(new Drive(20, 0, 0));
        rightLeftSwitchFrontMP.addSection(new RunIntake(0.5, 500));
        rightLeftSwitchFrontMP.addSection(new DriveDistance(-15, 2000));
        rightLeftSwitchFrontMP.addSection(new ArmMover(0));
        rightLeftSwitchFrontMP.addSection(new ElevatorMover(0));
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
        leftRightSwitchBackMP.addSection(new ArmMover(0, 400));
        leftRightSwitchBackMP.addSection(new ElevatorMover(25, 700));
        leftRightSwitchBackMP.addSection(new RunIntake(0.3, 500));

        rightLeftSwitchBackMP = new AutonomousManager();
        rightLeftSwitchBackMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_SIDE_WALL_FORWARD - Constants.ROBOT_HALF_LENGTH, 0, 0),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_SWITCH_BACK_WALL_HORIZONTAL + Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90)),
                new Waypoint(FieldMap.RIGHT_FAR_SWITCH_BACK_FORWARD + Constants.ROBOT_HALF_WIDTH, FieldMap.RIGHT_FAR_SWITCH_BACK_HORIZONTAL + Constants.ROBOT_HALF_LENGTH, Pathfinder.d2r(-90))
        }));
        rightLeftSwitchBackMP.addSection(new TurnToAngleSection(-90));
        rightLeftSwitchBackMP.addSection(new Drive(20, 0, 0));
        rightLeftSwitchBackMP.addSection(new ArmMover(0, 400));
        rightLeftSwitchBackMP.addSection(new ElevatorMover(25, 700));
        rightLeftSwitchBackMP.addSection(new RunIntake(0.3, 500));

        leftLeftScaleLeftSwitchMP = new AutonomousManager();
        leftLeftScaleLeftSwitchMP.addSection(new RunIntake(-0.25, 20));
        leftLeftScaleLeftSwitchMP.addSection(new ArmMover(45, 20));
        leftLeftScaleLeftSwitchMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD_START_TURN, 0, 0),
                new Waypoint(FieldMap.LEFT_SCALE_FORWARD - Constants.ROBOT_LENGTH, FieldMap.LEFT_SCALE_HORIZONTAL - Constants.ROBOT_HALF_WIDTH + 12, 0)
        }, 70, 2000));
        leftLeftScaleLeftSwitchMP.addSection(new Drive(20, 0, 0));
        leftLeftScaleLeftSwitchMP.addSection(new RunIntake(0.5, 200));
        leftLeftScaleLeftSwitchMP.addSection(new ArmMover(60, 20));
        leftLeftScaleLeftSwitchMP.addSection(new ElevatorMover(0, 20));
        leftLeftScaleLeftSwitchMP.addSection(new DriveDistance(-12));
        leftLeftScaleLeftSwitchMP.addSection(new TurnToAngleSection(180, 1000));
        leftLeftScaleLeftSwitchMP.addSection(new RunIntake(-.8, 40));
        leftLeftScaleLeftSwitchMP.addSection(new ArmMover(0, 40));
        leftLeftScaleLeftSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, -10, 0)
        }));
        leftLeftScaleLeftSwitchMP.addSection(new Drive(20, 0.2, 0));
        leftLeftScaleLeftSwitchMP.addSection(new RunIntake(-.35, 1400, true));
        leftLeftScaleLeftSwitchMP.addSection(new DriveDistance(-10, 1000));
        leftLeftScaleLeftSwitchMP.addSection(new ArmMover(45, 40));
        leftLeftScaleLeftSwitchMP.addSection(new ElevatorMover(15, 400));
        leftLeftScaleLeftSwitchMP.addSection(new Drive(2000,0.2, 0));
        leftLeftScaleLeftSwitchMP.addSection(new RunIntake(.5, 40));
        leftLeftScaleLeftSwitchMP.addSection(new Drive(20, 0, 0));

        rightRightScaleRightSwitchMP = new AutonomousManager();
        rightRightScaleRightSwitchMP.addSection(new RunIntake(-0.25, 20));
        rightRightScaleRightSwitchMP.addSection(new ArmMover(45, 20));
        rightRightScaleRightSwitchMP.addSection(new MotionProfileWithElevator(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD_START_TURN, 0, 0),
                new Waypoint(FieldMap.RIGHT_SCALE_FORWARD - Constants.ROBOT_LENGTH, FieldMap.RIGHT_SCALE_HORIZONTAL + Constants.ROBOT_HALF_WIDTH - 12, 0)
        }, 70, 2000));
        rightRightScaleRightSwitchMP.addSection(new Drive(20, 0, 0));
        rightRightScaleRightSwitchMP.addSection(new RunIntake(0.5, 200));
        rightRightScaleRightSwitchMP.addSection(new ArmMover(60, 20));
        rightRightScaleRightSwitchMP.addSection(new ElevatorMover(0, 20));
        rightRightScaleRightSwitchMP.addSection(new DriveDistance(-12));
        rightRightScaleRightSwitchMP.addSection(new TurnToAngleSection(-180, 1000));
        rightRightScaleRightSwitchMP.addSection(new RunIntake(-.8, 40));
        rightRightScaleRightSwitchMP.addSection(new ArmMover(0, 40));
        rightRightScaleRightSwitchMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(35, 10, 0)
        }));
        rightRightScaleRightSwitchMP.addSection(new Drive(20, 0.2, 0));
        rightRightScaleRightSwitchMP.addSection(new RunIntake(-.35, 1400, true));
        rightRightScaleRightSwitchMP.addSection(new DriveDistance(-10, 1000));
        rightRightScaleRightSwitchMP.addSection(new ArmMover(45, 40));
        rightRightScaleRightSwitchMP.addSection(new ElevatorMover(15, 400));
        rightRightScaleRightSwitchMP.addSection(new Drive(2000,0.2, 0));
        rightRightScaleRightSwitchMP.addSection(new RunIntake(.5, 40));
        rightRightScaleRightSwitchMP.addSection(new Drive(20, 0, 0));

        leftRightScaleMP = new AutonomousManager();
        leftRightScaleMP.addSection(new ArmMover(45, 20));
        leftRightScaleMP.addSection(new RunIntake(-0.25, 20));
        leftRightScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.LEFT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.LEFT_SWITCH_SIDE_WALL_HORIZONTAL - Constants.ROBOT_WIDTH - 12, Pathfinder.d2r(0))
        }));
        leftRightScaleMP.addSection(new TurnToAngleSection(90, 1000));
        leftRightScaleMP.addSection(new Drive(20, 0, 0));
        leftRightScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(160, 0, 0)
        }));
        leftRightScaleMP.addSection(new TurnToAngleSection(-90, 1000));
        leftRightScaleMP.addSection(new Drive(20, 0, 0));

        rightLeftScaleMP = new AutonomousManager();
        rightLeftScaleMP.addSection(new ArmMover(45, 20));
        rightLeftScaleMP.addSection(new RunIntake(-0.25, 20));
        rightLeftScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(FieldMap.RIGHT_SWITCH_BACK_WALL_FORWARD + Constants.ROBOT_HALF_LENGTH, FieldMap.RIGHT_SWITCH_SIDE_WALL_HORIZONTAL + Constants.ROBOT_WIDTH + 12, Pathfinder.d2r(0))
        }));
        rightLeftScaleMP.addSection(new TurnToAngleSection(-90, 1000));
        rightLeftScaleMP.addSection(new Drive(20, 0, 0));
        rightLeftScaleMP.addSection(new MotionProfiler(new Waypoint[]{
                new Waypoint(0, 0, 0),
                new Waypoint(160, 0, 0)
        }));
        rightLeftScaleMP.addSection(new TurnToAngleSection(90, 1000));
        rightLeftScaleMP.addSection(new Drive(20, 0, 0));
    }
    
    /**
     * Retrieves the FMS field randomization and Autonomous Chooser data and
     * calls the appropriate auto mode. This currently has placeholders for autonomous
     * modes we haven't written yet.
 1    */
    public AutonomousManager start() {
    	System.out.println("Side "+ sideChooser.getSelected());
        Globals.logger.print("Event", "Autonomous Starter start");
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        Globals.logger.print("Autonomous Randomization Data", gameData);
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'L') {
                switchSide = Side.LEFT;
                Globals.logger.print("Autonomous Randomization Data", "Switch is on the left");
            } else {
                switchSide = Side.RIGHT;
                Globals.logger.print("Autonomous Randomization Data", "Switch is on the right");
            }
            if (gameData.charAt(1) == 'L') {
                scaleSide = Side.LEFT;
                Globals.logger.print("Autonomous Randomization Data", "Scale is on the left");
            } else {
                scaleSide = Side.RIGHT;
                Globals.logger.print("Autonomous Randomization Data", "Scale is on the right");
            }
        }
        startSide = sideChooser.getSelected();
        startSide= Side.RIGHT;
        //TODO REMOVE!!!
        System.out.println("SIDEEEE!!!"+sideChooser.getSelected());
        Globals.logger.print("Autonomous Start Side", startSide == Side.LEFT ? "Left" : startSide == Side.CENTER ? "Center" : "Right");
        selectedStrategy = strategyChooser.getSelected();
        scaleSide = scaleSideChooser.getSelected() != null ? scaleSideChooser.getSelected() : scaleSide;
        switchSide = switchSideChooser.getSelected() != null ? switchSideChooser.getSelected() : switchSide;
        switch(selectedStrategy){
            case NETWORK_PROFILE:
                return testAuto;
            case SCALE_SAME_SIDE:
                if(startSide == scaleSide){
                    if(startSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return driveForward;
                } else return driveForward;
            case SWITCH_SAME_SIDE:
                if(startSide == switchSide){
                    if(startSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return driveForward;
                } else return driveForward;
            case SWITCH_OR_SCALE_SAME_SIDE:
                if(startSide == switchSide){
                    if(startSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return driveForward;
                } else if(startSide == scaleSide){
                    if(startSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return driveForward;
                } else return driveForward;
            case SCALE_OR_SWITCH_SAME_SIDE:
                if(startSide == scaleSide){
                    if(startSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return driveForward;
                } else if(startSide == switchSide){
                    if(startSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return driveForward;
                } else return driveForward;
            case SCALE_ALWAYS:
                if(startSide == Side.LEFT) {
                    if (scaleSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else return null; /************TO BE FIXED***********/
                } else if (startSide == Side.RIGHT){
                    if (scaleSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return null; /************TO BE FIXED***********/
                } else {
                    if(scaleSide == Side.LEFT)
                        return null; /*************TO BE FIXED***********/
                    else return null;/*************TO BE FIXED***********/
                }
            case SWITCH_ALWAYS:
                if(startSide == Side.LEFT) {
                    if (switchSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else return leftRightSwitchFrontMP;
                } else if (startSide == Side.RIGHT){
                    if (switchSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return rightLeftSwitchFrontMP;
                } else {
                    if(switchSide == Side.LEFT)
                        return centerLeftSwitchMP;
                    else return centerRightSwitchMP;
                }
            case SWITCH_AND_SCALE_SAME_SIDE:
                if(startSide == scaleSide && startSide == switchSide) {
                    if(startSide == Side.LEFT)
                        return leftLeftScaleLeftSwitchMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleRightSwitchMP;
                    else return driveForward;
                } else if(startSide == scaleSide){
                    if(startSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return driveForward;
                } else if(startSide == switchSide){
                    if(startSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return driveForward;
                } else {
                    return driveForward;
                }
            case SWITCH_AND_SCALE:
                if(startSide == scaleSide && startSide == switchSide) {
                    if(startSide == Side.LEFT)
                        return leftLeftScaleLeftSwitchMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleRightSwitchMP;
                    else return driveForward;
                } else if(startSide == scaleSide){
                    if(startSide == Side.LEFT)
                        return leftLeftScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightScaleMP;
                    else return driveForward;
                } else if(startSide == switchSide){
                    if(startSide == Side.LEFT)
                        return leftLeftSwitchSideMP;
                    else if(startSide == Side.RIGHT)
                        return rightRightSwitchSideMP;
                    else return driveForward;
                } else {
                    if(startSide == Side.LEFT)
                        return leftRightScaleMP;
                    else if(startSide == Side.RIGHT)
                        return rightLeftScaleMP;
                    else return driveForward;
                }
            case SWITCH_THEN_SCALE:
                if(startSide == scaleSide && startSide == switchSide) {
                    if(startSide == Side.LEFT)
                        return null; /***********TO BE FIXED************/
                    else return null;/***********TO BE FIX++E
                     D************/
                } else return driveForward; /*********TO BE COMPLETED**********/
            case EXPERIMENTAL_AUTO:
                return leftLeftScaleLeftSwitchMP;
            case DRIVE_FORWARD:
            default:
                return driveForward;
        }
    }
}

enum Side {
    LEFT, CENTER, RIGHT
}

enum Strategy {
    DRIVE_FORWARD,
    SWITCH_SAME_SIDE,
    SWITCH_ALWAYS,
    SCALE_SAME_SIDE,
    SCALE_ALWAYS,
    SWITCH_OR_SCALE_SAME_SIDE,
    SCALE_OR_SWITCH_SAME_SIDE,
    SWITCH_AND_SCALE_SAME_SIDE,
    SWITCH_AND_SCALE,
    SWITCH_THEN_SCALE,
    NETWORK_PROFILE,
    EXPERIMENTAL_AUTO
}