package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

/**
 * An extension of the normal hardwareMap that provides only Dbz-extended objects instead of just anything
 */

public class DbzHardwareMap {
    final private static String TAG = DbzHardwareMap.class.getName();
    private final HardwareMap hardwareMap;
    /**
     * A map that maps the name of a device to the actual Dbz hardware device
     * When one of the get methods is called, we check this map to see if the Dbz device has already been created
     * If it has, we just return that object instead of making a new one
     */
    HashMap<String, HardwareDevice> createdDevices = new HashMap<>();

    DbzHardwareMap(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    DbzMotor getDbzMotor(String name) {
        // if we've already created this DbzMotor, just return that DbzMotor
        if (createdDevices.containsKey(name)) {
            if (createdDevices.get(name) instanceof DbzMotor)
                return (DbzMotor) createdDevices.get(name);
        }

        // Otherwise, go get the DcMotor out of hardwareMap
        DcMotor base = hardwareMap.dcMotor.get(name);

        // we can only make a DbzMotor if we got an DcMotorEx from hardwareMap
        // if we got one, then put it in createdDevices and return it.  otherwise be angry.
        if (base instanceof DcMotorEx) {
            DbzMotor dbzMotor = new DbzMotorImpl((DcMotorEx) base);
            createdDevices.put(name, dbzMotor);
            return dbzMotor;
        } else {
            throw new RuntimeException("Motor " + name + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports DcMotorEx?");
        }
    }

    //maybe consider a method like
    // Chassis getChassis() {
    //     return new Chassis(getDbzMotor("left"), getDbzMotor("right);
    // }
    //or something kinda like that
}
