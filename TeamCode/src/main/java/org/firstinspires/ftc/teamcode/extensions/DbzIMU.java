package org.firstinspires.ftc.teamcode.extensions;

import android.support.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by Matthew on 8/28/2017.
 */

public class DbzIMU implements BNO055IMU, DbzDevice {
    private final BNO055IMU lynxEmbeddedIMU;

    public DbzIMU(BNO055IMU embeddedIMU) {
        lynxEmbeddedIMU = embeddedIMU;
    }

    @NonNull
    public Parameters getParameters() {
        return lynxEmbeddedIMU.getParameters();
    }

    public boolean initialize(@NonNull Parameters parameters) {
        return lynxEmbeddedIMU.initialize(parameters);
    }

    public SystemStatus getSystemStatus() {
        return lynxEmbeddedIMU.getSystemStatus();
    }

    public SystemError getSystemError() {
        return lynxEmbeddedIMU.getSystemError();
    }

    public CalibrationStatus getCalibrationStatus() {
        return lynxEmbeddedIMU.getCalibrationStatus();
    }

    public void close() {
        lynxEmbeddedIMU.close();
    }

    public Orientation getAngularOrientation(AxesReference reference, AxesOrder order, org.firstinspires.ftc.robotcore.external.navigation.AngleUnit angleUnit) {
        return lynxEmbeddedIMU.getAngularOrientation(reference, order, angleUnit);
    }

    public boolean isSystemCalibrated() {
        return lynxEmbeddedIMU.isSystemCalibrated();
    }

    public boolean isGyroCalibrated() {
        return lynxEmbeddedIMU.isGyroCalibrated();
    }

    public boolean isAccelerometerCalibrated() {
        return lynxEmbeddedIMU.isAccelerometerCalibrated();
    }

    public boolean isMagnetometerCalibrated() {
        return lynxEmbeddedIMU.isMagnetometerCalibrated();
    }

    public CalibrationData readCalibrationData() {
        return lynxEmbeddedIMU.readCalibrationData();
    }

    public void writeCalibrationData(CalibrationData data) {
        lynxEmbeddedIMU.writeCalibrationData(data);
    }

    public Temperature getTemperature() {
        return lynxEmbeddedIMU.getTemperature();
    }

    public MagneticFlux getMagneticFieldStrength() {
        return lynxEmbeddedIMU.getMagneticFieldStrength();
    }

    public Acceleration getOverallAcceleration() {
        return lynxEmbeddedIMU.getOverallAcceleration();
    }

    public Acceleration getLinearAcceleration() {
        return lynxEmbeddedIMU.getLinearAcceleration();
    }

    public Acceleration getGravity() {
        return lynxEmbeddedIMU.getGravity();
    }

    public AngularVelocity getAngularVelocity() {
        return lynxEmbeddedIMU.getAngularVelocity();
    }

    public Orientation getAngularOrientation() {
        return lynxEmbeddedIMU.getAngularOrientation();
    }

    public Quaternion getQuaternionOrientation() {
        return lynxEmbeddedIMU.getQuaternionOrientation();
    }

    public Acceleration getAcceleration() {
        return lynxEmbeddedIMU.getAcceleration();
    }

    public Velocity getVelocity() {
        return lynxEmbeddedIMU.getVelocity();
    }

    public Position getPosition() {
        return lynxEmbeddedIMU.getPosition();
    }

    public void startAccelerationIntegration(Position initalPosition, Velocity initialVelocity, int msPollInterval) {
        lynxEmbeddedIMU.startAccelerationIntegration(initalPosition, initialVelocity, msPollInterval);
    }

    public void stopAccelerationIntegration() {
        lynxEmbeddedIMU.stopAccelerationIntegration();
    }

    public byte read8(Register reg) {
        return lynxEmbeddedIMU.read8(reg);
    }

    public byte[] read(Register reg, int cb) {
        return lynxEmbeddedIMU.read(reg, cb);
    }

    public void write8(Register reg, int data) {
        lynxEmbeddedIMU.write8(reg, data);
    }

    public void write(Register reg, byte[] data) {
        lynxEmbeddedIMU.write(reg, data);
    }
}
