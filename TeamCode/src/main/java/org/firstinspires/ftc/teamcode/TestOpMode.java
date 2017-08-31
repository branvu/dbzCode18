package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.extensions.DbzOpMode;

/**
 * Created by Matthew on 8/30/2017.
 */

@Autonomous(name = "Test", group = "Tests")
public class TestOpMode extends DbzOpMode {
    @Override
    protected void dbzInit() {

    }

    @Override
    protected void dbzLoop() {
        leftMotor.setVelocity(1, AngleUnit.RADIANS);
        rightMotor.setVelocity(-1, AngleUnit.RADIANS);
        telemetry.addData("imu x rotation", imu.getAngularVelocity().xRotationRate);

    }

    @Override
    protected void dbzLoopHook() {

    }

    @Override
    protected void dbzTeardown() {

    }
}
