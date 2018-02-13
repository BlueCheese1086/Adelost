package org.usfirst.frc.team1086.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro implements PIDSource {
    public AHRS gyro;

<<<<<<< HEAD
    static {
        instance = new Gyro();
    }

    private Gyro() {
=======
    public Gyro(){
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7
        try {
            gyro = new AHRS(SerialPort.Port.kUSB1);
            System.out.println("VMX successfully instantiated");
        } catch(Exception e) {
            System.out.println("VMX was unable to be found");
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public static Gyro getInstance() {
        return instance;
    }

    public double getAngle() {
=======
    public double getAngle(){
>>>>>>> 1301bff70f5a850fc3de502b950c4cda5f9687b7
        return gyro.getYaw();
    }

    public double getNormalizedAngle() {
        return Utils.normalizeAngle(getAngle());
    }
    
    public void logSmartDashbard() {
    	SmartDashboard.putNumber("Gyro Angle", this.getAngle());
    }


    @Override public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override public double pidGet() {
        return getNormalizedAngle();
    }
}
