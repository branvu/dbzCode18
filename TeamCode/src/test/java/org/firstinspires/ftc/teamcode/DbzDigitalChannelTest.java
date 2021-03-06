package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.extensions.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.fakehardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.mockito.Spy;

/**
 * Created by Kumon on 8/27/2017.
 */
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
