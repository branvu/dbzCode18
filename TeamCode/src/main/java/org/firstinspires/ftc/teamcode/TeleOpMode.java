package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.extension.DbzOpMode;

/**
 * Created by Matthew on 8/25/2017.
 */

@TeleOp(name = "MainTeleOp", group = "TeleOp")
public class TeleOpMode extends DbzOpMode {

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