package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.LimitSwitch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Kumon on 8/27/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MotorTest {
    @Mock
    DbzMotor motor;

    @Mock
    LimitSwitch limitSwitch;

    @Before
    public void addAndStartLimit(){
        motor.addLimitSwitch(limitSwitch);
        motor.startLimiting();
        System.out.println("Did stuff");
    }

    @Test
    public void readMotorPosition(){
        when(motor.getCurrentPosition())
                .thenReturn(5);
        assertEquals(5,motor.getCurrentPosition());
    }
    @Test
    public void readMotorLimit(){
        //motor.addLimitSwitch(limitSwitch);


        //when(limitSwitch.isLimiting())
          //      .thenReturn(true);
        assertEquals(true,limitSwitch.isLimiting());
    }
}
