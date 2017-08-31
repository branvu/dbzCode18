package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.constructs.ITankChassis;
import org.firstinspires.ftc.teamcode.constructs.TankChassis;
import org.firstinspires.ftc.teamcode.extensions.DbzMotor;
import org.firstinspires.ftc.teamcode.fakehardware.FakeDcMotorEx;
import org.firstinspires.ftc.teamcode.infrastructure.DbzUnitTester;
import org.firstinspires.ftc.teamcode.utils.LogDbz;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Matthew on 8/29/2017.
 */

public class TankChassisTest extends DbzUnitTester {
    final private static String TAG = TankChassisTest.class.getName();

    @Spy
    FakeDcMotorEx leftMotorBase;
    @Spy
    FakeDcMotorEx rightMotorBase;

    private ITankChassis chassis;
    private DbzMotor leftMotor, rightMotor;

    @Before
    public void initChassis() {
        this.leftMotor = new DbzMotor(leftMotorBase);
        this.rightMotor = new DbzMotor(rightMotorBase);
        this.chassis = new TankChassis(leftMotor, rightMotor);
    }

    @Test
    public void testTurn() {
        double speed = 1;
        double radius = 1;
        double radians = 2 * Math.PI;
        chassis.turn(speed, radius, radians, false);
        checkTurningVelocities(speed, radius);

        double outerDistance = radians * (radius + (ITankChassis.CHASSIS_WIDTH / 2));
        // if we are going right
        if (1 / radius > 0) {
            assertEquals(outerDistance, 2 * Math.PI * ITankChassis.WHEEL_RADIUS * rightMotorBase.getTargetPosition() / rightMotor.getTicksPerRev());
            //todo: this fails for some reason
        }

    }

    @Test
    public void testRealDrive() {
        // check different speeds and radii, positive and negative, on realDrive
        analyzeRealDrive(1, 2);
        analyzeRealDrive(1, 1);
        analyzeRealDrive(0, 1);
        analyzeRealDrive(2, -1);

        // check realDrive's behavior when given signed zeros
        analyzeRealDriveZeros();

        // check realDrive's behavior when given infinities
        analyzeRealDriveInfs();
    }

    private void analyzeRealDrive(double speed, double radius) {
        if (radius == 0) {
            LogDbz.e(TAG, "Cannot call analyzeRealDrive with 0 radius; ignoring");
            return;
        }
        LogDbz.v(TAG, "realDrive at speed " + Double.toString(speed) + "m/s and at radius " + Double.toString(radius) + "m");
        chassis.realDrive(speed, radius);
        checkTurningVelocities(speed, radius);
    }

    private void checkTurningVelocities(double speed, double radius) {
        double leftV = ITankChassis.WHEEL_RADIUS * leftMotorBase.getVelocity(null);
        double rightV = ITankChassis.WHEEL_RADIUS * rightMotorBase.getVelocity(null);
        LogDbz.v(TAG, "leftV: " + Double.toString(leftV) + " | rightV: " + Double.toString(rightV));
        double netOmega = (leftV - rightV) / ITankChassis.CHASSIS_WIDTH;
        double netVelocity = (leftV + rightV) / 2;
        LogDbz.v(TAG, "Net velocity: " + Double.toString(netVelocity) + " | Net Omega: " + Double.toString(netOmega));
        // check to see if our outer speed is the same as our tangential velocity as calculated by omega*r
        // to 4 decimals places
        assertEquals(speed, Math.round(1e4 * netOmega * (radius + (Math.signum(radius) * ITankChassis.CHASSIS_WIDTH / 2))) / 1e4);
        LogDbz.v(TAG, "This line intentionally blank \n");
    }

    /**
     * Tests realDrive in the case of a signed zero as the turn radius
     */
    private void analyzeRealDriveZeros() {
        // a positive zero should give a point turn right, see if it does
        chassis.realDrive(1, 0d);
        double leftV = ITankChassis.WHEEL_RADIUS * leftMotorBase.getVelocity(null);
        double rightV = ITankChassis.WHEEL_RADIUS * rightMotorBase.getVelocity(null);
        assertEquals(1d, leftV);
        assertEquals(-1d, rightV);

        // a negative zero should give a point turn left, see if it does
        chassis.realDrive(1, -0d);
        leftV = ITankChassis.WHEEL_RADIUS * leftMotorBase.getVelocity(null);
        rightV = ITankChassis.WHEEL_RADIUS * rightMotorBase.getVelocity(null);
        assertEquals(-1d, leftV);
        assertEquals(1d, rightV);
    }

    private void analyzeRealDriveInfs() {
        chassis.realDrive(1, Double.POSITIVE_INFINITY);
        double leftV = ITankChassis.WHEEL_RADIUS * leftMotorBase.getVelocity(null);
        double rightV = ITankChassis.WHEEL_RADIUS * rightMotorBase.getVelocity(null);
        assertEquals(1d, leftV);
        assertEquals(1d, rightV);

        chassis.realDrive(1, Double.NEGATIVE_INFINITY);
        leftV = ITankChassis.WHEEL_RADIUS * leftMotorBase.getVelocity(null);
        rightV = ITankChassis.WHEEL_RADIUS * rightMotorBase.getVelocity(null);
        assertEquals(-1d, leftV);
        assertEquals(-1d, rightV);
    }

    @Override
    public void testGettersSetters() {
        testAllGettersAndSetters(chassis, null);
    }
}
