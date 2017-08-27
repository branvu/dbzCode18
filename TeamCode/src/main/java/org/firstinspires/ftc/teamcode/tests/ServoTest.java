package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.extension.DbzServo;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeServoEx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Brandon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class ServoTest {
    @Spy
    private FakeServoEx servoEx;
    @Test
    public void testServo(){
        DbzServo servo = new DbzServo(servoEx);
        when(servo.getDirection())
                .thenReturn(Servo.Direction.FORWARD);
        assertEquals(Servo.Direction.FORWARD,servo.getDirection());
    }

}
