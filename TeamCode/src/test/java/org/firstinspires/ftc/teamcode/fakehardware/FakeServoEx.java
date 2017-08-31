package org.firstinspires.ftc.teamcode.fakehardware;

import org.firstinspires.ftc.teamcode.extensions.DbzServo;

/**
 * Created by Brandon on 8/27/2017.
 */

public abstract class FakeServoEx implements DbzServo.ServoEx{
    final private static String TAG = FakeServoEx.class.getName();

    private Direction direction = Direction.FORWARD;
    private double position = 0;

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
}
