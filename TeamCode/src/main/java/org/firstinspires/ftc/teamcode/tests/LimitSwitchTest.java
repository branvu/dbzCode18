package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Brandon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class LimitSwitchTest {
    @Spy
    FakeDigitalChannel digitalChannel;

    LimitSwitch limitSwitch;
    @Before
    public void InitLimiter(){
        DbzDigitalChannel dbzDigitalChannel = new DbzDigitalChannel(digitalChannel);
         limitSwitch = new LimitSwitch(dbzDigitalChannel);
    }
    @Test
    public void testLimitLimiting(){
        digitalChannel.setState(false);
        assertEquals(true,limitSwitch.isLimiting());
    }
}
