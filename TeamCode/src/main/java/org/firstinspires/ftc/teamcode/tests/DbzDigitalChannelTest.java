package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extensions.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.fakehardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class DbzDigitalChannelTest extends DbzUnitTester {
    final private static String TAG = DbzDigitalChannelTest.class.getName();

    @Spy
    FakeDigitalChannel fakeDigitalChannel;

    private DbzDigitalChannel dbzDigitalChannel;

    @Before
    public void initDigitalChannel() {
        dbzDigitalChannel = new DbzDigitalChannel(fakeDigitalChannel);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(dbzDigitalChannel, null);
    }
}
