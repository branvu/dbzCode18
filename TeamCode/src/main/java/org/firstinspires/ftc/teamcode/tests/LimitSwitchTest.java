package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.constructs.LimitSwitch;
import org.firstinspires.ftc.teamcode.extensions.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.fakehardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.firstinspires.ftc.teamcode.utils.LogDbz;
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

public class LimitSwitchTest extends DbzUnitTester {
    final private static String TAG = LimitSwitchTest.class.getName();

    @Spy
    FakeDigitalChannel digitalChannel;

    private LimitSwitch limitSwitch;
    private DbzDigitalChannel dbzDigitalChannel;

    @Before
    public void initLimitSwitch() {
        LogDbz.i(TAG, "Note: LimitSwitch relies on DbzDigitalChannel.  DbzDigitalChannel failing may " +
                "also cause this to fail");
        dbzDigitalChannel = new DbzDigitalChannel(digitalChannel);
        limitSwitch = new LimitSwitch(dbzDigitalChannel);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(limitSwitch, null);
    }

    @Test
    public void testIsLimiting() {
        // test isLimiting when activeHigh = true
        limitSwitch = new LimitSwitch(dbzDigitalChannel, true);
        digitalChannel.setState(true);
        assertEquals("Test isLimiting with true when activeHigh is true",
                true, limitSwitch.isLimiting());
        digitalChannel.setState(false);
        assertEquals("Test isLimiting with false when activeHigh is true",
                false, limitSwitch.isLimiting());

        // test isLimiting when activeHigh = false
        limitSwitch = new LimitSwitch(dbzDigitalChannel, false);
        digitalChannel.setState(true);
        assertEquals("Test isLimiting with true when activeHigh is false",
                false, limitSwitch.isLimiting());
        digitalChannel.setState(false);
        assertEquals("Test isLimiting with false when activeHigh is false",
                true, limitSwitch.isLimiting());
    }
}
