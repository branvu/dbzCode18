package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.constructs.LimitSwitch;
import org.firstinspires.ftc.teamcode.extensions.DbzMotor;
import org.firstinspires.ftc.teamcode.fakehardware.FakeDcMotorEx;
import org.firstinspires.ftc.teamcode.infrastructure.DbzUnitTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Kumon on 8/27/2017.
 */
public class DbzMotorTest extends DbzUnitTester {
    final private static String TAG = DbzMotorTest.class.getName();

    @Spy
    FakeDcMotorEx dcMotorEx;
    @Mock
    LimitSwitch limitSwitch;

    private DbzMotor motor;

    @Before
    public void initMotor() {
        motor = new DbzMotor(dcMotorEx);
    }

    @Override
    public void testGettersSetters() {
        Map<Class, Object[]> testObjects = new HashMap<>();
        testObjects.put(MotorConfigurationType.class, new MotorConfigurationType[]{});
        testAllGettersAndSetters(motor, testObjects);
    }

    @Test
    public void testIsLimiting() {
        motor.addLimitSwitch(limitSwitch);
        motor.startLimiting();

        // test behavior when limit switch isn't limited
        motor.setPower(0.5);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        when(limitSwitch.isLimiting())
                .thenReturn(false);
        assertEquals("Test power of motor startLimiting using get/setPower when limit switch is not limiting",
                0.5, motor.getPower());
        assertEquals("Test direction of motor startLimiting using get/setDirection when limit switch is limiting",
                DcMotorSimple.Direction.FORWARD, motor.getDirection());

        // test behavior when limit switch is limited
        when(limitSwitch.isLimiting())
                .thenReturn(true);
        sleep(100);
        assertEquals("Test power of motor startLimiting using get/setPower when limit switch is limiting",
                0.5, motor.getPower());
        assertEquals("Test direction of motor startLimiting using get/setDirection when limit switch is limiting",
                DcMotorSimple.Direction.REVERSE, motor.getDirection());

        // test behavior when limit switch is un-limited
        when(limitSwitch.isLimiting())
                .thenReturn(false);
        sleep(100);
        assertEquals("Test power of motor startLimiting using get/setPower when limit switch is done limiting",
                0d, motor.getPower());
        // no direction test needed if power is 0
        motor.stopLimiting();

        // test behavior after limiting ends with a true limit switch
        when(limitSwitch.isLimiting())
                .thenReturn(true);
        motor.setPower(0.5);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        assertEquals("Test power of motor after stopLimiting using get/setPower when limit switch is limiting",
                0.5, motor.getPower());
        assertEquals("Test direction of motor startLimiting using get/setDirection when limit switch is limiting",
                DcMotorSimple.Direction.FORWARD, motor.getDirection());
    }

    @Test
    public void testBreakOnLimit() {
        motor.addLimitSwitch(limitSwitch);
        motor.setPower(0.5);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        when(limitSwitch.isLimiting())
                .thenReturn(false);

        // test if the motor will work normally when not breaking
        motor.breakOnLimit();
        assertEquals("Test motor breaking power using get/setPower when limit switch is not limiting",
                0.5, motor.getPower());
        assertEquals("Test motor breaking direction using get/setDirection when limit switch is not limiting",
                DcMotorSimple.Direction.FORWARD, motor.getDirection());
        sleep(100);

        // test if the motor will break when the limit switch is pressed
        when(limitSwitch.isLimiting())
                .thenReturn(true);
        sleep(100);
        assertEquals("Test motor breaking power using get/setPower when limit switch is limiting",
                0d, motor.getPower());
        // no direction test needed if power is 0
        motor.cancelBreakOnLimit();
    }


}
