package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.extension.DbzHardwareMap;
import org.firstinspires.ftc.teamcode.extension.DbzMotor;
import org.firstinspires.ftc.teamcode.extension.DbzServo;

/**
 * Created by Matthew on 8/26/2017.
 */

public class Chassis {
    final private static String TAG = Chassis.class.getName();

    static DbzMotor leftMotor;
    static DbzServo rightWheel;

    static {
        leftMotor = DbzHardwareMap.getDbzMotor(DbzHardwareMap.DbzMotorNames.left);
        rightWheel = DbzHardwareMap.getDbzServo(DbzHardwareMap.DbzServoNames.cam);
    }

    static void drive(double speed) {
        leftMotor.setPower(speed);
        rightWheel.setPosition(0.3);
    }
}
