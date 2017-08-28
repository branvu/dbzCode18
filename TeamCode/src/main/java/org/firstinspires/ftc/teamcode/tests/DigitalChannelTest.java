package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.extension.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDigitalChannel;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class DigitalChannelTest extends DbzUnitTester<DbzDigitalChannel> {
    @Spy
    FakeDigitalChannel fakeDigitalChannel;

    private DbzDigitalChannel dbzDigitalChannel;

    @Before
    public void initDigitalChannel() {
        dbzDigitalChannel = new DbzDigitalChannel(fakeDigitalChannel);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(DbzDigitalChannel.class, null);
    }

    @Override
    protected DbzDigitalChannel getInstance() {
        return dbzDigitalChannel;
    }
}
