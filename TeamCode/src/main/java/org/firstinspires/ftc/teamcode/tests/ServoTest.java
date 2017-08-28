package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.extension.DbzServo;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeServoEx;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Brandon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class ServoTest extends DbzUnitTester {
    @Spy
    private FakeServoEx servoEx;

    DbzServo servo;
    @Before
    public void initServo(){
        servo = new DbzServo(servoEx);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(DbzServo.class, null);
    }

    @Override
    protected Object getInstance() {
        return servo;
    }

    @Test
    public void testServoDirection(){
        servo.setDirection(Servo.Direction.FORWARD);
        assertEquals(Servo.Direction.FORWARD,servo.getDirection());
    }

}
