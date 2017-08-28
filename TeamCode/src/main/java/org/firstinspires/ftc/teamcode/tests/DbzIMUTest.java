package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.extensions.DbzIMU;
import org.firstinspires.ftc.teamcode.tests.fakehardware.FakeBNO055;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.internal.matchers.Or;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.mockito.Mockito.when;

/**
 * Created by Brandon on 8/28/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class DbzIMUTest extends DbzUnitTester{
    @Spy
    FakeBNO055 fakeBNO055;

    DbzIMU dbzIMU;
    Orientation orientation;
    @Before
    public void init(){
        dbzIMU = new DbzIMU(fakeBNO055);
        orientation = dbzIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, DEGREES);

    }

    @Override
    public void testGettersSetters() {

    }
    @Test
    public void testOrientation(){
        when(dbzIMU.getAngularOrientation())//I know this doesn't test anything
                .thenReturn(orientation);
        assertEquals(orientation,dbzIMU.getAngularOrientation());
    }
}
