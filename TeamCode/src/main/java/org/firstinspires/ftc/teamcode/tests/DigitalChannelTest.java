package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDigitalChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class DigitalChannelTest {
    @Spy
    FakeDigitalChannel fakedigitalChannel;

    @Test
    public void testDigitalChannel(){
        DbzDigitalChannel digitalChannel = new DbzDigitalChannel(fakedigitalChannel);
        when(digitalChannel.getState())
                .thenReturn(false);
        assertEquals(false,digitalChannel.getState());
    }
}
