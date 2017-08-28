package org.firstinspires.ftc.teamcode.tests.FakeHardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class FakeDcMotorEx implements DcMotorEx {
    private double power = 0;
    private Direction direction = Direction.FORWARD;
    private RunMode runMode = RunMode.RUN_USING_ENCODER;

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

}