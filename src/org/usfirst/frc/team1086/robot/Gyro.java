package org.usfirst.frc.team1086.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;

public class Gyro implements PIDSource {
    public AHRS gyro;

    public Gyro(){
        try {
            gyro = new AHRS(SerialPort.Port.kUSB1);
            System.out.println("VMX successfully instantiated");
        } catch(Exception e){
            System.out.println("VMX was unable to be found");
            e.printStackTrace();
        }
    }

    public double getAngle(){
        return gyro.getYaw();
    }

    public double getNormalizedAngle(){
        return Utils.normalizeAngle(getAngle());
    }


    @Override public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override public double pidGet() {
        return getAngle();
    }
}
