package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.extension.DbzMotor;
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

        DbzMotor motor = new DbzMotor(dcMotorEx);


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

        assertEquals(0d, motor.getPower());
        motor.stopLimiting();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static abstract class FakeDcMotorEx implements DcMotorEx {
        double power = 0;
        Direction direction = Direction.FORWARD;

        @Override
        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        @Override
        public Direction getDirection() {
            return direction;
        }

        @Override
        public double getPower() {
            return power;
        }

        @Override
        public void setPower(double power) {
            this.power = power;
        }
    }

}
