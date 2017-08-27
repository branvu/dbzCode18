package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Brandon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class LimitSwitchTest {
    @Spy
    DbzDigitalChannel dbzDigitalChannel;
    @Before
    public void InitLimiter(){
        LimitSwitch limitSwitch = new LimitSwitch(dbzDigitalChannel);
    }

}
