package org.firstinspires.ftc.teamcode.extension;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by Matthew on 8/25/2017.
 */

public class DbzMotor implements DcMotorEx, DbzDevice {
    final private static String TAG = DbzMotor.class.getName();
    private final DcMotorEx dcMotorEx;

    DbzMotor(DcMotorEx dcMotorEx) {
        this.dcMotorEx = dcMotorEx;
        if (dcMotorEx instanceof DbzMotor)
            Log.w(TAG, "Someone just made a DbzMotor wrapper around another DbzMotor; this is probably not intended");
    }

    public Manufacturer getManufacturer() {
        return dcMotorEx.getManufacturer();
    }

    public String getDeviceName() {
        return dcMotorEx.getDeviceName();
    }

    public String getConnectionInfo() {
        return dcMotorEx.getConnectionInfo();
    }

    public int getVersion() {
        return dcMotorEx.getVersion();
    }

    public void resetDeviceConfigurationForOpMode() {
        dcMotorEx.resetDeviceConfigurationForOpMode();
    }

    public void close() {
        dcMotorEx.close();
    }

    public MotorConfigurationType getMotorType() {
        return dcMotorEx.getMotorType();
    }

    public void setMotorType(MotorConfigurationType motorType) {
        dcMotorEx.setMotorType(motorType);
    }

    public DcMotorController getController() {
        return dcMotorEx.getController();
    }

    public void setDirection(Direction direction) {
        dcMotorEx.setDirection(direction);
    }

    public Direction getDirection() {
        return dcMotorEx.getDirection();
    }

    public int getPortNumber() {
        return dcMotorEx.getPortNumber();
    }

    public void setPower(double power) {
        dcMotorEx.setPower(power);
    }

    public double getPower() {
        return dcMotorEx.getPower();
    }

    public boolean isBusy() {
        return dcMotorEx.isBusy();
    }

    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        dcMotorEx.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public ZeroPowerBehavior getZeroPowerBehavior() {
        return dcMotorEx.getZeroPowerBehavior();
    }

    @Deprecated
    public void setPowerFloat() {
        Log.w(TAG, "Call made to setPowerFloat, a deprecated method");
        dcMotorEx.setPowerFloat();
    }

    public boolean getPowerFloat() {
        return dcMotorEx.getPowerFloat();
    }

    public void setTargetPosition(int position) {
        dcMotorEx.setTargetPosition(position);
    }

    public int getTargetPosition() {
        return dcMotorEx.getTargetPosition();
    }

    public int getCurrentPosition() {
        return dcMotorEx.getCurrentPosition();
    }

    public void setMode(RunMode mode) {
        dcMotorEx.setMode(mode);
    }

    public RunMode getMode() {
        return dcMotorEx.getMode();
    }

    public void setMotorEnable() {
        dcMotorEx.setMotorEnable();
    }

    public void setMotorDisable() {
        dcMotorEx.setMotorDisable();
    }

    public boolean isMotorEnabled() {
        return dcMotorEx.isMotorEnabled();
    }

    public void setVelocity(double angularRate, AngleUnit unit) {
        dcMotorEx.setVelocity(angularRate, unit);
    }

    public double getVelocity(AngleUnit unit) {
        return dcMotorEx.getVelocity(unit);
    }

    public void setPIDCoefficients(RunMode mode, PIDCoefficients pidCoefficients) {
        dcMotorEx.setPIDCoefficients(mode, pidCoefficients);
    }

    public PIDCoefficients getPIDCoefficients(RunMode mode) {
        return dcMotorEx.getPIDCoefficients(mode);
    }

    public int getTargetPositionTolerance() {
        return dcMotorEx.getTargetPositionTolerance();
    }

    public void setTargetPositionTolerance(int tolerance) {
        dcMotorEx.setTargetPositionTolerance(tolerance);
    }
}
