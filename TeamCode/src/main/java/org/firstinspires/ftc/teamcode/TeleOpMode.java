package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extensions.DbzOpMode;

/**
 * Created by Matthew on 8/25/2017.
 */

@TeleOp(name = "MainTeleOp", group = "TeleOp")
public class TeleOpMode extends DbzOpMode {
    final private static String TAG = TeleOpMode.class.getName();


    @Override
    protected void dbzInit() {
        limitedMotor.addLimitSwitch(limiter1);
        limitedMotor.addLimitSwitch(limiter2);
        limitedMotor.startLimiting();
    }

    @Override
    protected void dbzLoopHook() {
        //probably nothing to do here
    }


    @Override
    protected void dbzLoop() {
        Chassis.drive(gamepad1.left_stick_y);
    }

    @Override
    protected void dbzTeardown() {
        limitedMotor.stopLimiting();
    }
}
