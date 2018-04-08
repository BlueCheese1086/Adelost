package org.usfirst.frc.team1086.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wrapper for VMX-Pi
 */
public class Gyro implements PIDSource {
    public AHRS gyro;

    public Gyro(){
        try {
            gyro = new AHRS(SerialPort.Port.kUSB1);
           // gyro = new AHRS(SPI.Port.kMXP);
            System.out.println("VMX successfully instantiated");
            Globals.logger.print("Event", "VMX successfully instantiated");
        } catch(Exception e){
            System.out.println("VMX was unable to be found");
            Globals.logger.print("Event", "VMX was unable to be instantiated");
            e.printStackTrace();
        }
    }

    public double getAngle(){
        return gyro.getYaw();
    }
    public double getNormalizedAngle(){
        return Utils.normalizeAngle(getAngle());
    }
    
    public void log() {
        SmartDashboard.putNumber("Gyro Angle", this.getAngle());
        SmartDashboard.putNumber("Gyro Pitch", this.getPitch());
        SmartDashboard.putNumber("Gyro Yaw", gyro.getYaw());
        Globals.heading.setDouble(this.getAngle());
		Globals.logger.print("General", "-----------------GYRO-------------------");
        Globals.logger.print("Gyro Angle", Globals.logger.format(this.getNormalizedAngle()));
        Globals.logger.print("Gyro Pitch", Globals.logger.format(this.getPitch()));
    }


    @Override public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override public double pidGet() {
        return getNormalizedAngle();
    }
	public double getPitch() {
		return gyro.getPitch();
	}
	public double getRoll() {
		return gyro.getRoll();
	}
}
