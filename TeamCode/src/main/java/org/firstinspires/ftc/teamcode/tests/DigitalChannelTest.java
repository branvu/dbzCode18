package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDigitalChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class DigitalChannelTest {
    @Spy
    FakeDigitalChannel fakedigitalChannel;

    /**
     * Test if DbzDigitalChannel can read states from DigitalChannel
     */
    @Test
    public void testDigitalChannelStateRead() {
        DbzDigitalChannel digitalChannel = new DbzDigitalChannel(fakedigitalChannel);
        fakedigitalChannel.setState(true);
        assertEquals("Test if DbzDigitalChannel can read true from the underlying DigitalChannel",
                true, digitalChannel.getState());
        fakedigitalChannel.setState(false);
        assertEquals("Test if DbzDigitalChannel can read false from the underlying DigitalChannel",
                false, digitalChannel.getState());
    }

    /**
     * Test if DbzDigitalChannel can write states to DigitalChannel
     */
    @Test
    public void testDigitalChannelStateWrite() {
        DbzDigitalChannel digitalChannel = new DbzDigitalChannel(fakedigitalChannel);
        digitalChannel.setState(true);
        assertEquals("Test if DbzDigitalChannel can write true to the underlying DigitalChannel",
                true, fakedigitalChannel.getState());
        digitalChannel.setState(false);
        assertEquals("Test if DbzDigitalChannel can write false to the underlying DigitalChannel",
                false, fakedigitalChannel.getState());
    }

    /**
     * Test if DbzDigitalChannel can write modes to DigitalChannel
     */
    @Test
    public void testDigitalChannelModeWrite() {
        DbzDigitalChannel digitalChannel = new DbzDigitalChannel(fakedigitalChannel);
        digitalChannel.setMode(DigitalChannel.Mode.INPUT);
        assertEquals("Test if DbzDigitalChannel can write INPUT to the underlying DigitalChannel",
                DigitalChannel.Mode.INPUT, fakedigitalChannel.getMode());
        digitalChannel.setMode(DigitalChannel.Mode.OUTPUT);
        assertEquals("Test if DbzDigitalChannel can write OUTPUT to the underlying DigitalChannel",
                DigitalChannel.Mode.OUTPUT, fakedigitalChannel.getMode());
    }

    /**
     * Test if DbzDigitalChannel can read modes from DigitalChannel
     */
    @Test
    public void testDigitalChannelModeRead() {
        DbzDigitalChannel digitalChannel = new DbzDigitalChannel(fakedigitalChannel);
        fakedigitalChannel.setMode(DigitalChannel.Mode.INPUT);
        assertEquals("Test if DbzDigitalChannel can read INPUT from the underlying DigitalChannel",
                DigitalChannel.Mode.INPUT, digitalChannel.getMode());
        fakedigitalChannel.setMode(DigitalChannel.Mode.OUTPUT);
        assertEquals("Test if DbzDigitalChannel can read OUTPUT from the underlying DigitalChannel",
                DigitalChannel.Mode.OUTPUT, digitalChannel.getMode());
    }

}
