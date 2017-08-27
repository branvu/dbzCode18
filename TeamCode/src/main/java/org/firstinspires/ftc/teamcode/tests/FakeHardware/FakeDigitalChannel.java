package org.firstinspires.ftc.teamcode.tests.FakeHardware;

import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * Created by Kumon on 8/27/2017.
 */

public abstract class FakeDigitalChannel implements DigitalChannel{
    Mode mode = Mode.INPUT;
    boolean state = false;
    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }
}
