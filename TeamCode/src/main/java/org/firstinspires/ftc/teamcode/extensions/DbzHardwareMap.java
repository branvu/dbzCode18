package org.firstinspires.ftc.teamcode.extensions;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
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
        DcMotor base = hardwareMap.dcMotor.get(motor.getName());

        // we can only make a DbzMotor if we got an DcMotorEx from hardwareMap
        // if we got one, then put it in createdDevices and return it.  otherwise be angry.
        if (base instanceof DcMotorEx) {
            DbzMotor dbzMotor = new DbzMotor((DcMotorEx) base);
            createdDevices.put(motor, dbzMotor);
            return dbzMotor;
        } else {
            throw new RuntimeException("Motor " + motor.getName() + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports DcMotorEx?");
        }
    }

    public static DbzServo getDbzServo(DbzServoNames servo) {
        // if we've already created this DbzServo, just return that DbzServo
        if (createdDevices.containsKey(servo)) {
            if (createdDevices.get(servo) instanceof DbzServo)
                return (DbzServo) createdDevices.get(servo);
        }

        Servo base = hardwareMap.servo.get(servo.getName());

        // we can only make a DbzServo if we got an ServoEx from hardwareMap
        // if we got one, then put it in createdDevices and return it.  otherwise be angry.
        if (base instanceof DbzServo.ServoEx) {
            DbzServo dbzServo = new DbzServo((DbzServo.ServoEx) base);
            createdDevices.put(servo, dbzServo);
            return dbzServo;
        } else {
            throw new RuntimeException("Servo " + servo.getName() + " is not an Ex type; it is " + base.getClass().getSimpleName()
                    + " are you sure the attached hardware supports ServoEx?");
        }
    }

    public static DbzDigitalChannel getDbzDigitalChannel(DbzDigitalChannelNames digitalChannel) {
        // if we've already created this DbzDigitalChannel, just return that one
        if (createdDevices.containsKey(digitalChannel)) {
            if (createdDevices.get(digitalChannel) instanceof DbzDigitalChannel)
                return (DbzDigitalChannel) createdDevices.get(digitalChannel);
        }

        DbzDigitalChannel dbzDigitalChannel = new DbzDigitalChannel(hardwareMap.digitalChannel.get(digitalChannel.getName()));
        createdDevices.put(digitalChannel, dbzDigitalChannel);
        return dbzDigitalChannel;
    }
    public static DbzIMU getDbzIMU(DbzIMUNames IMU){
        if(createdDevices.containsKey(IMU)){
            if(createdDevices.get(IMU) instanceof DbzIMU){
                return (DbzIMU) createdDevices.get(IMU);
            }
        }
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class,IMU.name);

        DbzIMU dbzIMU = new DbzIMU(imu);
        createdDevices.put(IMU,dbzIMU);
        return dbzIMU;
    }

    public static DbzColorRangeSensor getDbzColorRangeSensor(DbzColorRangeSensorNames colorRangeSensor) {
        if (createdDevices.containsKey(colorRangeSensor)) {
            if (createdDevices.get(colorRangeSensor) instanceof DbzColorRangeSensor)
                return (DbzColorRangeSensor) createdDevices.get(colorRangeSensor);
        }

        ColorSensor base = hardwareMap.colorSensor.get(colorRangeSensor.getName());

        if (base instanceof RevColorRangeSensor) {
            DbzColorRangeSensor dbzColorRangeSensor = new DbzColorRangeSensor((RevColorRangeSensor) base);
            createdDevices.put(colorRangeSensor, dbzColorRangeSensor);
            return dbzColorRangeSensor;
        } else {
            throw new RuntimeException("Color sensor " + colorRangeSensor.getName() + " is not a Rev sensor; it is "
                    + base.getClass().getSimpleName() + " are you sure the attached hardware is a Rev Color Sensor?");
        }
    }


    public static HashMap<DbzDeviceNames, DbzDevice> getCreatedDevices() {
        return createdDevices;
    }

    public enum DbzMotorNames implements DbzDeviceNames {
        left("LeftMotor"), limited("limitedmotor"), right("RightMotor");

        private String name;

        DbzMotorNames(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum DbzServoNames implements DbzDeviceNames {
        cam("CamServo");

        private String name;

        DbzServoNames(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum DbzDigitalChannelNames implements DbzDeviceNames {
        limiter1("limit1"), limiter2("limit2");

        private String name;

        DbzDigitalChannelNames(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
    public enum DbzIMUNames implements DbzDeviceNames {
        imu("imu");

        String name;
        DbzIMUNames(String name){this.name = name;}
    }

    public enum DbzColorRangeSensorNames implements DbzDeviceNames {
        sensor("limit1");

        private String name;

        DbzColorRangeSensorNames(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }


    interface DbzDeviceNames {
        String getName();
    }
}
