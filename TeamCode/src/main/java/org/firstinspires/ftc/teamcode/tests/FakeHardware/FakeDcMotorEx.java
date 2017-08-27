package org.firstinspires.ftc.teamcode.tests.FakeHardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class FakeDcMotorEx implements DcMotorEx {
        double power = 0;
        Direction direction = Direction.FORWARD;

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
    }