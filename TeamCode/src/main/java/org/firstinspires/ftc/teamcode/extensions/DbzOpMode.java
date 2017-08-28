package org.firstinspires.ftc.teamcode.extensions;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constructs.ITankChassis;
import org.firstinspires.ftc.teamcode.constructs.LimitSwitch;
import org.firstinspires.ftc.teamcode.constructs.TankChassis;

/**
 * Created by Matthew on 8/25/2017.
 */

public abstract class DbzOpMode extends LinearOpMode {
    final private static String TAG = DbzOpMode.class.getName();

    private static DbzOpMode instance = null;

    public DbzOpMode() {
        instance = this;
    }

    protected ElapsedTime runtime = new ElapsedTime();


    //TODO: this is an example motor
    protected DbzMotor leftMotor, rightMotor;
    protected DbzServo camServo;
    protected DbzMotor limitedMotor;
    protected LimitSwitch limiter1, limiter2;
    protected ITankChassis chassis;


    @Override
    public void runOpMode() {
        /* Initialize stuff here from the dbzHardwareMap */
        leftMotor = DbzHardwareMap.getDbzMotor(DbzHardwareMap.DbzMotorNames.left);
        rightMotor = DbzHardwareMap.getDbzMotor(DbzHardwareMap.DbzMotorNames.right);
        camServo = DbzHardwareMap.getDbzServo(DbzHardwareMap.DbzServoNames.cam);
        limitedMotor = DbzHardwareMap.getDbzMotor(DbzHardwareMap.DbzMotorNames.limited);
        limiter1 = new LimitSwitch(DbzHardwareMap.getDbzDigitalChannel(DbzHardwareMap.DbzDigitalChannelNames.limiter1));
        limiter2 = new LimitSwitch(DbzHardwareMap.getDbzDigitalChannel(DbzHardwareMap.DbzDigitalChannelNames.limiter2));
        // then the constructs
        chassis = new TankChassis(leftMotor, rightMotor);



        /* Run our init code and say we are initialized */
        dbzInit();
        Log.i(TAG, "Successfully initialized DbzOpMode");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /* Wait for the play button, then start our normal code */
        waitForStart();
        runtime.reset();

        dbzLoopHook();
        while (opModeIsActive()) {
            dbzLoop();
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
        dbzTeardown();
    }

    static HardwareMap getInstanceHardwareMap() {
        if (instance == null) {
            Log.e(TAG, "getInstanceHardwareMap called before instantiation of DbzOpMode");
            return null;
        }
        return instance.hardwareMap;
    }


    protected abstract void dbzInit();

    protected abstract void dbzLoop();

    protected abstract void dbzLoopHook();

    protected abstract void dbzTeardown();
}
