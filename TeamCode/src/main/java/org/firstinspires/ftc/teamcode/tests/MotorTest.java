package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.extension.DbzMotor;
import org.firstinspires.ftc.teamcode.tests.FakeHardware.FakeDcMotorEx;
import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MotorTest {
    @Spy
    FakeDcMotorEx dcMotorEx;
    DbzMotor motor;
    @Mock LimitSwitch limitSwitch;
    @Before
    public void initMotor(){
        motor = new DbzMotor(dcMotorEx);
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
