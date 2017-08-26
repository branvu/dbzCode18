package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Matthew on 8/25/2017.
 */

@TeleOp(name = "MainTeleOp", group = "TeleOp")
public class TeleOpMode extends DbzOpMode {
    final private static String TAG = TeleOpMode.class.getName();

    @Override
    protected void dbzInit() {
        //maybe reset some motor encoders?  talk to some sensors?
    }

    @Override
    protected void dbzLoopHook() {
        //probably nothing to do here
    }


    @Override
    protected void dbzLoop() {
        //get gamepad info and feed to motors
    }
}
