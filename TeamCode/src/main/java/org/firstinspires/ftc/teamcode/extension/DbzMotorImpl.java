package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;

/**
 * Created by Matthew on 8/25/2017.
 */

public class DbzMotorImpl extends DcMotorImplEx implements DbzMotor {
    DbzMotorImpl(DcMotorEx dcMotorEx) {
        // this looks bad.  is there really no better way??
        super(dcMotorEx.getController(), dcMotorEx.getPortNumber(), dcMotorEx.getDirection(), dcMotorEx.getMotorType());
    }
}
