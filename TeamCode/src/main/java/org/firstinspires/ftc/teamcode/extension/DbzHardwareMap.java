package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * An extension of the normal hardwareMap that provides only Dbz-extended objects instead of just anything
 */

public class DbzHardwareMap extends HardwareMap {

    DbzHardwareMap(HardwareMap hardwareMap) {
        super(hardwareMap.appContext);
    }

    DbzMotor getDbzMotor(String name) {
        DcMotor base = dcMotor.get(name);

        // we can only make a DbzMotor if we got an DcMotorEx from hardwareMap
        // if we didn't get one, be very angry.
        if (base instanceof DcMotorEx)
            return new DbzMotorImpl((DcMotorEx) base);
        else
            throw new RuntimeException("Motor " + name + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports DcMotorEx?");
    }
}
