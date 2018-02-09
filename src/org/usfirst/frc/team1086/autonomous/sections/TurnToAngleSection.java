package org.usfirst.frc.team1086.autonomous.sections;

import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc.team1086.autonomous.AutonomousSection;
import org.usfirst.frc.team1086.robot.Globals;
import org.usfirst.frc.team1086.robot.Gyro;
import org.usfirst.frc.team1086.robot.Utils;
import org.usfirst.frc.team1086.subsystems.Drivetrain;

public class TurnToAngleSection extends AutonomousSection {
    PIDController turnPID;
    Drivetrain drive;
    Gyro gyro;
    double setAngle;

    public TurnToAngleSection(double angle){
        this.duration = -1;
        init(angle);
    }

    public TurnToAngleSection(double angle, long timeOut){
        this.duration = timeOut;
        init(angle);
    }

    private void init(double angle){
        drive = Globals.drivetrain;
        gyro = Globals.gyro;
        turnPID = drive.turnToAngleController;
        setAngle = angle;
    }

    @Override public void start(){
        turnPID.setSetpoint(Utils.normalizeAngle(gyro.getNormalizedAngle() + setAngle));
        turnPID.enable();
    }

    @Override public void update() {
        drive.drive(0, turnPID.get());
    }

    @Override public void finish() {

    }

    @Override public boolean isFinished(){
        return super.isFinished() || turnPID.onTarget();
    }
}
