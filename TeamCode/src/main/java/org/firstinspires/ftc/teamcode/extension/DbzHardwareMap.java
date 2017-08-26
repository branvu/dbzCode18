package org.firstinspires.ftc.teamcode.extension;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

/**
 * An extension of the normal hardwareMap that provides only Dbz-extended objects instead of just anything
 */

public class DbzHardwareMap {
    final private static String TAG = DbzHardwareMap.class.getName();

    private static HardwareMap hardwareMap;

    static {
        hardwareMap = DbzOpMode.getInstanceHardwareMap();
    }

    /**
     * A map that maps the name of a device to the actual Dbz hardware device
     * When one of the get methods is called, we check this map to see if the Dbz device has already been created
     * If it has, we just return that object instead of making a new one
     */
    private static HashMap<DbzDeviceNames, DbzDevice> createdDevices = new HashMap<>();


    public static DbzMotor getDbzMotor(DbzMotorNames motor) {
        // if we've already created this DbzMotor, just return that DbzMotor
        if (createdDevices.containsKey(motor)) {
            if (createdDevices.get(motor) instanceof DbzMotor)
                return (DbzMotor) createdDevices.get(motor);
        }

        // Otherwise, go get the DcMotor out of hardwareMap
        DcMotor base = hardwareMap.dcMotor.get(motor.name);

        // we can only make a DbzMotor if we got an DcMotorEx from hardwareMap
        // if we got one, then put it in createdDevices and return it.  otherwise be angry.
        if (base instanceof DcMotorEx) {
            DbzMotor dbzMotor = new DbzMotor((DcMotorEx) base);
            createdDevices.put(motor, dbzMotor);
            return dbzMotor;
        } else {
            throw new RuntimeException("Motor " + motor.name + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports DcMotorEx?");
        }
    }

    public static DbzServo getDbzServo(DbzServoNames servo) {
        // if we've already created this DbzServo, just return that DbzServo
        if (createdDevices.containsKey(servo)) {
            if (createdDevices.get(servo) instanceof DbzServo)
                return (DbzServo) createdDevices.get(servo);
        }

        Servo base = hardwareMap.servo.get(servo.name);

        // we can only make a DbzServo if we got an ServoEx from hardwareMap
        // if we got one, then put it in createdDevices and return it.  otherwise be angry.
        if (base instanceof DbzServo.ServoEx) {
            DbzServo dbzServo = new DbzServo((DbzServo.ServoEx) base);
            createdDevices.put(servo, dbzServo);
            return dbzServo;
        } else {
            throw new RuntimeException("Servo " + servo.name + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports ServoEx?");
        }
    }

    public static DbzDigitalChannel getDbzDigitalChannel(DbzDigitalChannelNames digitalChannel) {
        // if we've already created this DbzDigitalChannel, just return that one
        if (createdDevices.containsKey(digitalChannel)) {
            if (createdDevices.get(digitalChannel) instanceof DbzDigitalChannel)
                return (DbzDigitalChannel) createdDevices.get(digitalChannel);
        }

        DbzDigitalChannel dbzDigitalChannel = new DbzDigitalChannel(hardwareMap.digitalChannel.get(digitalChannel.name));
        createdDevices.put(digitalChannel, dbzDigitalChannel);
        return dbzDigitalChannel;
    }


    public static HashMap<DbzDeviceNames, DbzDevice> getCreatedDevices() {
        return createdDevices;
    }

    public enum DbzMotorNames implements DbzDeviceNames {
        left("LeftMotor"), limited("limitedmotor");

        String name;

        DbzMotorNames(String name) {
            this.name = name;
        }
    }

    public enum DbzServoNames implements DbzDeviceNames {
        cam("CamServo");

        String name;

        DbzServoNames(String name) {
            this.name = name;
        }
    }

    public enum DbzDigitalChannelNames implements DbzDeviceNames {
        limiter1("limit1"), limiter2("limit2");

        String name;

        DbzDigitalChannelNames(String name) {
            this.name = name;
        }
    }

    interface DbzDeviceNames {
    }
}
