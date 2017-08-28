package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
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

public class LimitSwitchTest extends DbzUnitTester<LimitSwitch> {
    @Spy
    FakeDigitalChannel digitalChannel;

    private LimitSwitch limitSwitch;

    @Before
    public void initLimitSwitch() {
        DbzDigitalChannel dbzDigitalChannel = new DbzDigitalChannel(digitalChannel);
        limitSwitch = new LimitSwitch(dbzDigitalChannel);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(LimitSwitch.class, null);
    }

    @Override
    protected LimitSwitch getInstance() {
        return limitSwitch;
    }


    @Test
    public void testLimitLimiting() { //TODO:this is not gud enough
        digitalChannel.setState(true);
        assertEquals(true,limitSwitch.isLimiting());
    }
}
