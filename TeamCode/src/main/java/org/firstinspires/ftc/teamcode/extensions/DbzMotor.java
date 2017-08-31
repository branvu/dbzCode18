package org.firstinspires.ftc.teamcode.extensions;


import com.qualcomm.hardware.motors.Matrix12vMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constructs.LimitSwitch;
import org.firstinspires.ftc.teamcode.utils.LogDbz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 8/25/2017.
 */

public class DbzMotor implements DcMotorEx, DbzDevice {
    final private static String TAG = DbzMotor.class.getName();
    private final DcMotorEx dcMotorEx;

    /**
     * This is true whenever a limit switch is pressed, and prevents other methods from changing the power
     */
    private boolean limitLockOut = false;


    /**
     * This is a list containing all the Limiter objects for this motor
     * you can only have 2 limit switches on any given motor
     */
    private List<Limiter> limiterList = new ArrayList<Limiter>();

    public DbzMotor(DcMotorEx dcMotorEx) {
        this.dcMotorEx = dcMotorEx;

        if (dcMotorEx instanceof DbzMotor)
            LogDbz.w(TAG, "Someone just made a DbzMotor wrapper around another DbzMotor; this is probably not intended");
    }

    void init(ZeroPowerBehavior zeroPowerBehavior, Direction direction) {
        setZeroPowerBehavior(zeroPowerBehavior);
        setDirection(direction);

        // we only have this one kind of motor, so they can all be of this type
        // not sure this needs explicit calling?
        setMotorType(MotorConfigurationType.getMotorType(Matrix12vMotor.class));
    }


    /* Limit switch handling - enabling and using the functionality */
    /**
     * Assign a limit switch to this motor.  This can be called up to two times
     *
     * @param limitSwitch the LimitSwitch object that is limiting this motor
     */
    public void addLimitSwitch(LimitSwitch limitSwitch) {
        if (limiterList.size() >= 2) {
            LogDbz.w(TAG, "You can't assign more than two limit switches to a DbzMotor");
            return;
        }

        limiterList.add(new Limiter(limitSwitch));
    }

    /**
     * Instruct both limit switches to, when pressed:
     * (1) Reverse motor direction & lock out other attempts to change the motor speed/direction
     * (2) Wait to be unpressed
     * (3) Stop the motor & end the lock out
     */
    public void startLimiting() {
        for (Limiter limiter : limiterList) {
            limiter.startListening();
        }
    }

    /**
     * Instruct both limit switches to end the behavior started in startLimiting()
     */
    public void stopLimiting() {
        for (Limiter limiter : limiterList) {
            limiter.stopListening();
        }
    }

    /**
     * Put out a standing order to each limit switch, to stop the motor as soon as one is pressed
     * Works for one press.
     */
    public void breakOnLimit() {
        for (Limiter limiter : limiterList) {
            limiter.breakOnPress();
        }
    }

    /**
     * Cancel the standing order to stop the motor as soon as something hits one of the limit switches
     * Note that each limit switch does this independently.  If you call breakOnLimit, you MUST
     * also call cancelBreakOnLimit.  Otherwise, undefined behavior may occur.  breakOnLimit gives
     * up to two seperate standing orders to two different limit switches.  When one switch triggers,
     * this ends one order.  However, it does not end the order on the OTHER limit switch.
     *
     * These methods are intended to be used in teleop.  For example, on button A press, a gear rack
     * is moved in with breakOnLimit().  On button A release, cancelBreakOnLimit() is sent.
     */
    public void cancelBreakOnLimit() {
        for (Limiter limiter : limiterList) {
            limiter.cancelBreakOnPress();
        }
    }

    private class Limiter {
        final private LimitSwitch limitSwitch;
        final int millisWaitDuration = 100;
        private Thread listenerThread;
        private Thread breakThread;

        Limiter(final LimitSwitch limitSwitch) {
            this.limitSwitch = limitSwitch;
        }

        /**
         * Start the thread that stops the motor if a limit switch is hit
         */
        public void startListening() {
            listenerThread = new Thread(new ListeningRunnable());
            listenerThread.start();
        }

        /**
         * Stop the thread that stops the motor if a limit switch is hit
         */
        public void stopListening() {
            if (listenerThread == null) {
                LogDbz.w(TAG, "Trying to shut down a listener thread that has not yet started");
                return;
            }
            listenerThread.interrupt();
        }

        /**
         * Wait for the limit switch to be pressed and stop the motor at that point
         * Checks for the limit switch's state every millisWaitDuration
         */
        public void breakOnPress() {
            breakThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LogDbz.i(TAG, "Starting breakOnPress thread");
                    try {
                        // While limit switch is limiting, wait
                        while (!(!limitSwitch.isLimiting() || Thread.interrupted()))
                            Thread.sleep(millisWaitDuration);
                    } catch (InterruptedException e) {
                        LogDbz.v(TAG, "Thread shut down while waiting for limit switch press; stopping motor");
                    }
                    setPower(0);
                    LogDbz.i(TAG, "Limit switch hit; ending breakOnPress thread");
                }
            });
            breakThread.start();
        }

        public void cancelBreakOnPress() {
            //If breakThread is not started, there is nothing to cancel
            if (breakThread == null)
                return;
            breakThread.interrupt();
        }

        /**
         * This class provides a Runnable that checks to see if the limit switch is pressed
         * if it is, it will reverse the motor and stop the motor after the limit is no longer pressed
         */
        private class ListeningRunnable implements Runnable {
            ListeningRunnable() {
            }

            @Override
            public void run() {
                LogDbz.v(TAG, "Starting limit switch listening thread");
                //if we aren't shut down, keep checking for a limiting condition every millisWaitDuration
                while (!Thread.interrupted()) {
                    try {
                        //if we are limited, then reverse the motor direction
                        //otherwise wait 0.1s
                        if (limitSwitch.isLimiting()) {
                            LogDbz.v(TAG, "Limit switch activated");

                            //if another limit switch is active, something is wrong.  Stop everything
                            //Since we didn't setLimitLockout to true, the other one should NOT be true,
                            //no limit switches should be true as lockout at this point
                            if (isLimitLockOut()) {
                                dcMotorEx.setMotorDisable();
                                //LogDbz.e(TAG, "Multiple limit switches pressed at the same time?");
                                throw new RuntimeException("Multiple active limit switches on one motor?");
                            }

                            //Let's start going the other way and stop anyone else from interfering
                            setLimitLockOut(true);
                            reverseMotorDirection();

                            //wait for the limit switch to be un-pressed
                            while (limitSwitch.isLimiting())
                                Thread.sleep(10);

                            //we are good to move on.  stop the motor and allow others to access
                            dcMotorEx.setPower(0);
                            setLimitLockOut(false);
                            LogDbz.v(TAG, "Limit switch deactivated");
                        } else {
                            Thread.sleep(millisWaitDuration);
                        }
                    } catch (InterruptedException e) {
                        LogDbz.v(TAG, "Limit switch listener thread killed while sleeping");
                    }
                }
                LogDbz.v(TAG, "Ending limit switch listening thread");

            }

            private void reverseMotorDirection() {
                if (dcMotorEx.getDirection() == Direction.FORWARD)
                    dcMotorEx.setDirection(Direction.REVERSE);
                else
                    dcMotorEx.setDirection(Direction.FORWARD);
            }
        }
    }


    /* Limit switch lockout logic.  If the limit switch is pressed, keep everyone else out */
    private synchronized void setLimitLockOut(boolean limitLockOut) {
        this.limitLockOut = limitLockOut;
    }

    private synchronized boolean isLimitLockOut() {
        return limitLockOut;
    }

    private boolean checkLimitLockOut() {
        if (isLimitLockOut()) {
            LogDbz.d(TAG, "Cannot set motor power while locked out by limit switch");
            return true;
        } else
            return false;
    }

    public void setVelocity(double angularRate, AngleUnit unit) {
        if (checkLimitLockOut())
            return;
        dcMotorEx.setVelocity(angularRate, unit);
    }

    public void setPower(double power) {
        if (checkLimitLockOut())
            return;
        dcMotorEx.setPower(power);
    }

    public void setDirection(Direction direction) {
        if (checkLimitLockOut())
            return;
        dcMotorEx.setDirection(direction);
    }


    /* Methods dealing with velocity */
    /**
     * Get the maximum achievable radians per second
     *
     * @return the maximum number of radians per second the motor can spin at
     */
    public double getAchievableMaxRadiansPerSec() {
        MotorConfigurationType type = dcMotorEx.getMotorType();
        return 2 * Math.PI * type.getAchieveableMaxRPMFraction() * type.getMaxRPM() / 60;
    }

    /* */
    public double getTicksPerRev() {
        try {
            return getMotorType().getTicksPerRev();
        } catch (NullPointerException e) {
            LogDbz.w(TAG, "If you aren't mocking DcMotorEx, be concerned.");
            return 1478.4; //the value for Matrix12VMotor
        }
    }


    /* Delegate all other methods to DcMotorEx */
    /**
     * @param unit
     * @return
     */
    public double getVelocity(AngleUnit unit) {
        return dcMotorEx.getVelocity(unit);
    }

    public double getPower() {
        return dcMotorEx.getPower();
    }

    public Manufacturer getManufacturer() {
        return dcMotorEx.getManufacturer();
    }

    public String getDeviceName() {
        return dcMotorEx.getDeviceName();
    }

    public String getConnectionInfo() {
        return dcMotorEx.getConnectionInfo();
    }

    public int getVersion() {
        return dcMotorEx.getVersion();
    }

    public void resetDeviceConfigurationForOpMode() {
        dcMotorEx.resetDeviceConfigurationForOpMode();
    }

    public void close() {
        dcMotorEx.close();
    }

    public MotorConfigurationType getMotorType() {
        return dcMotorEx.getMotorType();
    }

    public void setMotorType(MotorConfigurationType motorType) {
        dcMotorEx.setMotorType(motorType);
    }

    public DcMotorController getController() {
        return dcMotorEx.getController();
    }

    public Direction getDirection() {
        return dcMotorEx.getDirection();
    }

    public int getPortNumber() {
        return dcMotorEx.getPortNumber();
    }

    public boolean isBusy() {
        return dcMotorEx.isBusy();
    }

    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        dcMotorEx.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public ZeroPowerBehavior getZeroPowerBehavior() {
        return dcMotorEx.getZeroPowerBehavior();
    }

    @Deprecated
    public void setPowerFloat() {
        LogDbz.w(TAG, "Call made to setPowerFloat, a deprecated method");
        dcMotorEx.setPowerFloat();
    }

    public boolean getPowerFloat() {
        return dcMotorEx.getPowerFloat();
    }

    public void setTargetPosition(int position) {
        dcMotorEx.setTargetPosition(position);
    }

    public int getTargetPosition() {
        return dcMotorEx.getTargetPosition();
    }

    public int getCurrentPosition() {
        return dcMotorEx.getCurrentPosition();
    }

    public void setMode(RunMode mode) {
        dcMotorEx.setMode(mode);
    }

    public RunMode getMode() {
        return dcMotorEx.getMode();
    }

    public void setMotorEnable() {
        dcMotorEx.setMotorEnable();
    }

    public void setMotorDisable() {
        dcMotorEx.setMotorDisable();
    }

    public boolean isMotorEnabled() {
        return dcMotorEx.isMotorEnabled();
    }

    public void setPIDCoefficients(RunMode mode, PIDCoefficients pidCoefficients) {
        dcMotorEx.setPIDCoefficients(mode, pidCoefficients);
    }

    public PIDCoefficients getPIDCoefficients(RunMode mode) {
        return dcMotorEx.getPIDCoefficients(mode);
    }

    public int getTargetPositionTolerance() {
        return dcMotorEx.getTargetPositionTolerance();
    }

    public void setTargetPositionTolerance(int tolerance) {
        dcMotorEx.setTargetPositionTolerance(tolerance);
    }
}
