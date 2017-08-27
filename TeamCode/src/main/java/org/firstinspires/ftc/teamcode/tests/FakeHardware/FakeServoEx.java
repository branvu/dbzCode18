package org.firstinspires.ftc.teamcode.tests.FakeHardware;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.extension.DbzServo;

/**
 * Created by Brandon on 8/27/2017.
 */

public abstract class FakeServoEx implements DbzServo.ServoEx{
    Direction direction = Direction.FORWARD;
    double position = 0;
    int portNum = 0;
    @Override
    public ServoController getController() {
        return null;
    }

    @Override
    public int getPortNumber() {
        return portNum;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setPosition(double position) {
        this.position = position;
    }

    @Override
    public double getPosition() {
        return position;
    }

    @Override
    public void scaleRange(double min, double max) {

    }
}
