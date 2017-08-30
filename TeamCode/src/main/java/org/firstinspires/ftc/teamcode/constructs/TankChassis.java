package org.firstinspires.ftc.teamcode.constructs;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.extensions.DbzMotor;
import org.firstinspires.ftc.teamcode.utils.LogDbz;

/**
 * Created by Matthew on 8/28/2017.
 */

public class TankChassis implements ITankChassis {
    final private static String TAG = TankChassis.class.getName();

    private DbzMotor leftWheel, rightWheel;

    public TankChassis(DbzMotor leftWheel, DbzMotor rightWheel) {
        this.leftWheel = leftWheel;
        this.rightWheel = rightWheel;
    }

    @Override
    public void drive(double normSpeed, double normTurn) {
        normSpeed = boundToOne(normSpeed, false);
        normTurn = boundToOne(normTurn, true);

        double slowFactor = 1 - 2 * Math.abs(normTurn);
        if (normTurn > 0) {
            leftWheel.setVelocity(leftWheel.getAchievableMaxRadiansPerSec() * normSpeed,
                    AngleUnit.RADIANS);
            rightWheel.setVelocity(slowFactor * rightWheel.getAchievableMaxRadiansPerSec() * normSpeed,
                    AngleUnit.RADIANS);
        } else {
            leftWheel.setVelocity(slowFactor * leftWheel.getAchievableMaxRadiansPerSec() * normSpeed,
                    AngleUnit.RADIANS);
            rightWheel.setVelocity(rightWheel.getAchievableMaxRadiansPerSec() * normSpeed,
                    AngleUnit.RADIANS);
        }
    }

    @Override
    public void realDrive(double speed, double radius) {
        // convert from linear speed in m/s to rotational speed in rad/s
        speed = Math.abs(speed / WHEEL_RADIUS);
        // if you give an infinite radius, then let's go straight.
        // +inf to go fwd or -inf to go backwards
        if (radius == Double.POSITIVE_INFINITY) {
            leftWheel.setVelocity(speed, AngleUnit.RADIANS);
            rightWheel.setVelocity(speed, AngleUnit.RADIANS);
            return;
        } else if (radius == Double.NEGATIVE_INFINITY) {
            leftWheel.setVelocity(-speed, AngleUnit.RADIANS);
            rightWheel.setVelocity(-speed, AngleUnit.RADIANS);
            return;
        }
        // if radius is positive (right turn), then rightRadius > leftRadius
        // if radius is negative (left turn), then rightRadius < leftRadius
        double leftRadius = (Math.abs(radius) - signumSignedZeros(radius) * (CHASSIS_WIDTH / 2));
        double rightRadius = (Math.abs(radius) + signumSignedZeros(radius) * (CHASSIS_WIDTH / 2));

        // so, if radius is positive, then slowFactor < 1
        // if radius is negative, then slowFactor > 1
        // we need to always slow down to remain under the speed limit, not speed something up
        double slowFactor = leftRadius / rightRadius;
        // handle signed 0s in this equation
        if (1 / radius > 0) {
            leftWheel.setVelocity(speed, AngleUnit.RADIANS);
            rightWheel.setVelocity(slowFactor * speed, AngleUnit.RADIANS);
        } else {
            leftWheel.setVelocity(speed / slowFactor, AngleUnit.RADIANS);
            rightWheel.setVelocity(speed, AngleUnit.RADIANS);
        }

    }

    @Override
    public void turn(double speed, double radius, double radians, boolean waitForCompletion) {
        // the fraction of the circle we are traveling, pos or neg
        double circle_fraction = radians / (2 * Math.PI);
        // the distance on that circle we are traveling in m, pos or neg
        // this is the circle_fraction*2*pi*r.  However, r depends on which way we are turning
        // for each wheel, since each wheel is tracing out a circle of a different radius
        final double left_arc_distance = circle_fraction * (Math.PI * 2 *
                (Math.abs(radius) - signumSignedZeros(radius) * (CHASSIS_WIDTH / 2)));
        final double right_arc_distance = circle_fraction * (Math.PI * 2 *
                (Math.abs(radius) + signumSignedZeros(radius) * (CHASSIS_WIDTH / 2)));

        Thread thread = new Thread(new RunToPositionEnabler(speed) {
            @Override
            public void setMotorTargetPositions() {
                // the number of ticks we need spin on the left wheel in order to go that distance
                double leftTicks = leftWheel.getMotorType().getTicksPerRev() * left_arc_distance / (2 * Math.PI * WHEEL_RADIUS);
                // the right wheel is the same as the left, but it gets negative
                // if radians was positive, we need to spin right.  this means that right wheel should
                // reverse while left goes forward.  if radians was negative, we have the reverse effect
                double rightTicks = -rightWheel.getMotorType().getTicksPerRev() * right_arc_distance / (2 * Math.PI * WHEEL_RADIUS);
                leftWheel.setTargetPosition(leftWheel.getCurrentPosition() + (int) Math.round(leftTicks));
                rightWheel.setTargetPosition(rightWheel.getCurrentPosition() + (int) Math.round(rightTicks));
            }

            @Override
            public void setMotorPowers(double speed) {
                // slow down the appropriate wheel by the appropriate fraction in order to meet the
                // speed requirements.  the spec'd speed is the speed of the outer wheel
                // Double.compare in order to handle signed zeros
                if (Double.compare(right_arc_distance, left_arc_distance) > 0) {
                    leftWheel.setVelocity(speed * left_arc_distance / right_arc_distance, AngleUnit.RADIANS);
                    rightWheel.setVelocity(speed, AngleUnit.RADIANS);
                } else {
                    leftWheel.setVelocity(speed, AngleUnit.RADIANS);
                    rightWheel.setVelocity(speed * right_arc_distance / left_arc_distance, AngleUnit.RADIANS);
                }
            }
        });
        if (waitForCompletion)
            thread.run();
        else
            thread.start();
    }

    @Override
    public void move(double speed, final double distance, boolean waitForCompletion) {
        Thread thread = new Thread(new RunToPositionEnabler(speed) {
            @Override
            public void setMotorTargetPositions() {
                // the tick deltas we need.  if distance is negative, these will be too.
                double leftTicks = leftWheel.getMotorType().getTicksPerRev() * distance / (2 * Math.PI * WHEEL_RADIUS);
                double rightTicks = rightWheel.getMotorType().getTicksPerRev() * distance / (2 * Math.PI * WHEEL_RADIUS);
                leftWheel.setTargetPosition(leftWheel.getCurrentPosition() + (int) Math.round(leftTicks));
                rightWheel.setTargetPosition(rightWheel.getCurrentPosition() + (int) Math.round(rightTicks));
            }
        });
        if (waitForCompletion)
            thread.run();
        else
            thread.start();
    }

    /**
     * Cuts down on boilerplate code needed to get the motors to run to a target position
     */
    private abstract class RunToPositionEnabler implements Runnable {
        final double rotationalSpeed;

        RunToPositionEnabler(final double speed) {
            rotationalSpeed = Math.abs(speed / WHEEL_RADIUS);
        }

        /**
         * Implement this method with however you want to set the target positions for
         * leftWheel and rightWheel
         */
        public abstract void setMotorTargetPositions();

        @Override
        public void run() {
            leftWheel.setMotorEnable();
            rightWheel.setMotorEnable();

            setMotorTargetPositions();

            // start running.  I think.  this order is from PushbotAutoDriveByEncoder_Linear
            // it really makes no sense and I'm not sure why, but the example works this way.
            leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            setMotorPowers(rotationalSpeed);

            while (leftWheel.isBusy() && rightWheel.isBusy()) {
                try {
                    Thread.sleep(10);  // is this even allowed on main thread
                } catch (InterruptedException e) {
                    LogDbz.w(TAG, "Interrupted while sleeping in move");
                }
            }

            leftWheel.setPower(0);
            rightWheel.setPower(0);
            leftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public void setMotorPowers(double speed) {
            // need to use setPower for some reason
            // normalize the speed in rad/s against the max rad/s speed to get the power on [0,1]
            leftWheel.setVelocity(speed, AngleUnit.RADIANS);
            rightWheel.setVelocity(speed, AngleUnit.RADIANS);
        }
    }

    /**
     * Normal signum, except that it returns 1 for positive 0s and -1 for negative zeros
     *
     * @param n the number to take the signum of
     * @return 1 if the number is positive or 0d.  -1 if the number is negative or -0d
     */
    private static double signumSignedZeros(double n) {
        if (1 / n > 0)
            return 1d;
        else
            return -1d;
    }

    /**
     * Given a double, makes it fit between [0,1] if negAllowed is false, or [-1,1] otherwise
     *
     * @param number     the double to range
     * @param negAllowed whether the accepted range is [0,1] or [-1,1]
     * @return the ranged double
     */
    private double boundToOne(double number, boolean negAllowed) {
        if (number > 1)
            return 1;
        if (number < 0) {
            if (negAllowed) {
                if (number < -1)
                    return -1;
                else
                    return number;
            } else {
                return 0;
            }
        }
        return number;
    }

}
