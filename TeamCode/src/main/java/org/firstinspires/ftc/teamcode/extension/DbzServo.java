package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * Created by Matthew on 8/26/2017.
 */

public class DbzServo implements Servo, PwmControl, DbzDevice {
    final private static String TAG = DbzServo.class.getName();

    public interface ServoEx extends Servo, PwmControl {
    }

    private final ServoEx servoEx;

    public DbzServo(ServoEx servo) {
        servoEx = servo;
    }

    public Manufacturer getManufacturer() {
        return servoEx.getManufacturer();
    }

    public String getDeviceName() {
        return servoEx.getDeviceName();
    }

    public String getConnectionInfo() {
        return servoEx.getConnectionInfo();
    }

    public int getVersion() {
        return servoEx.getVersion();
    }

    public void resetDeviceConfigurationForOpMode() {
        servoEx.resetDeviceConfigurationForOpMode();
    }

    public void close() {
        servoEx.close();
    }

    public ServoController getController() {
        return servoEx.getController();
    }

    public void setDirection(Direction direction) {
        servoEx.setDirection(direction);
    }

    public Direction getDirection() {
        return servoEx.getDirection();
    }

    public int getPortNumber() {
        return servoEx.getPortNumber();
    }

    public void setPosition(double position) {
        servoEx.setPosition(position);
    }

    public double getPosition() {
        return servoEx.getPosition();
    }

    public void scaleRange(double min, double max) {
        servoEx.scaleRange(min, max);
    }

    public void setPwmRange(PwmRange range) {
        servoEx.setPwmRange(range);
    }

    public PwmRange getPwmRange() {
        return servoEx.getPwmRange();
    }

    public void setPwmEnable() {
        servoEx.setPwmEnable();
    }

    public void setPwmDisable() {
        servoEx.setPwmDisable();
    }

    public boolean isPwmEnabled() {
        return servoEx.isPwmEnabled();
    }
}
