package org.usfirst.frc.team1086.autonomous.sections;

import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;

public class MoveArm extends AutonomousSection {
    double targetAngle;
    public MoveArm(double targetAngle) {
        this(targetAngle, -1);
    }
    public MoveArm(double targetAngle, int duration) {
        this.duration = duration;
        this.targetAngle = targetAngle;
    }
    @Override public void update() {
        Globals.arm.setArmPosition(targetAngle);
    }
    @Override public void finish(){}
    @Override public boolean isFinished() {
        return super.isFinished() || Math.abs(Globals.arm.getArmPosition() - targetAngle) < 3;
    }
}
