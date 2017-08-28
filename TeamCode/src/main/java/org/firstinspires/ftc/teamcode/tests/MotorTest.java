package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.extension.DbzMotor;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDcMotorEx;
import org.firstinspires.ftc.teamcode.tests.infrastructure.DbzUnitTester;
import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MotorTest extends DbzUnitTester<DbzMotor> {
    @Spy
    FakeDcMotorEx dcMotorEx;
    DbzMotor motor;
    @Mock LimitSwitch limitSwitch;

    @Before
    public void initMotor(){
        motor = new DbzMotor(dcMotorEx);
    }

    @Override
    protected DbzMotor getInstance() {
        return motor;
    }

    @Override
    public void testGettersSetters() {
        Map<Class, Object[]> testObjects = new HashMap<>();
        testObjects.put(MotorConfigurationType.class, new MotorConfigurationType[]{});
        testAllGettersAndSetters(DbzMotor.class, testObjects);
    }

    @Test
    public void readMotorLimit(){

        motor.addLimitSwitch(limitSwitch);
        motor.startLimiting();

        motor.setPower(0.5);

        when(limitSwitch.isLimiting())
                .thenReturn(false);
        assertEquals(0.5, motor.getPower());

        when(limitSwitch.isLimiting())
                .thenReturn(true);
        sleep(100);

        when(limitSwitch.isLimiting())
                .thenReturn(false);

        sleep(100);

        assertEquals(0d, motor.getPower());//assertEquals(Expected,Actual)
        motor.stopLimiting();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
