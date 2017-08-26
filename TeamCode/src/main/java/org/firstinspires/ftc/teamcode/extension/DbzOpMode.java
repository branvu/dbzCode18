package org.firstinspires.ftc.teamcode.extension;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 8/25/2017.
 */

public abstract class DbzOpMode extends LinearOpMode {
    final static String TAG = DbzOpMode.class.getName();
    protected ElapsedTime runtime = new ElapsedTime();
    public DbzHardwareMap dbzHardwareMap = new DbzHardwareMap(hardwareMap);


    //TODO: this is an example motor
    protected DbzMotor leftMotor;

    @Override
    public void runOpMode() {
        /* Initialize stuff here from the dbzHardwareMap */
        leftMotor = dbzHardwareMap.getDbzMotor("leftMotor");

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
    }


    protected abstract void dbzInit();

    protected abstract void dbzLoop();

    protected abstract void dbzLoopHook();
}
