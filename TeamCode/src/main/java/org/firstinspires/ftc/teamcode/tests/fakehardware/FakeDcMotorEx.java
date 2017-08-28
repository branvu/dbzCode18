package org.firstinspires.ftc.teamcode.tests.fakehardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public abstract class FakeDcMotorEx implements DcMotorEx {
    final private static String TAG = FakeDcMotorEx.class.getName();

    private double power = 0;
    private Direction direction = Direction.FORWARD;
    private RunMode runMode = RunMode.RUN_USING_ENCODER;
    private int tolerance = 10;
    private ZeroPowerBehavior zeroPowerBehavior = ZeroPowerBehavior.BRAKE;
    private int targetPostion = 1000;
    private double velocity = 0;

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void setMode(RunMode mode) {
        this.runMode = mode;
    }

    @Override
    public RunMode getMode() {
        return runMode;
    }

    @Override
    public void setTargetPositionTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    public int getTargetPositionTolerance() {
        return tolerance;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return zeroPowerBehavior;
    }

    @Override
    public void setTargetPosition(int position) {
        this.targetPostion = position;
    }

    @Override
    public int getTargetPosition() {
        return targetPostion;
    }

    @Override
    public void setVelocity(double angularRate, AngleUnit unit) {
        this.velocity = angularRate;
    }

    @Override
    public double getVelocity(AngleUnit unit) {
        return velocity;
    }
}