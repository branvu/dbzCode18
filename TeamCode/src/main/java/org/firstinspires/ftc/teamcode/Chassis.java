package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.extensions.DbzHardwareMap;
import org.firstinspires.ftc.teamcode.extensions.DbzMotor;
import org.firstinspires.ftc.teamcode.extensions.DbzServo;

/**
 * Right now, a placeholder demonstrating possible higher level constructs
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
