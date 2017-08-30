package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extensions.DbzServo;
import org.firstinspires.ftc.teamcode.tests.fakehardware.FakeServoEx;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.mockito.Spy;

/**
 * Created by Brandon on 8/27/2017.
 */
public class DbzServoTest extends DbzUnitTester {
    final private static String TAG = DbzServoTest.class.getName();

    @Spy
    private FakeServoEx servoEx;

    private DbzServo servo;

    @Before
    public void initServo(){
        servo = new DbzServo(servoEx);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(servo, null);
    }
}
