package org.firstinspires.ftc.teamcode.constructs;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.extensions.DbzDigitalChannel;
import org.firstinspires.ftc.teamcode.utils.LogDbz;

/**
 * Created by Matthew on 8/26/2017.
 */

public class LimitSwitch {
    final private static String TAG = LimitSwitch.class.getName();
    final private DbzDigitalChannel limitSwitch;

    /**
     * If this is true, this means that the switch outputs a positive logic signal when pressed
     * If this is false, this means that the switch outputs a negative logic signal when pressed
     * It will default to true
     */
    final private boolean activeHigh;

    public LimitSwitch(DbzDigitalChannel limitSwitch, boolean activeHigh) {
        this.limitSwitch = limitSwitch;
        this.activeHigh = activeHigh;

        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

    public LimitSwitch(DbzDigitalChannel limitSwitch) {
        this(limitSwitch, true);
    }

    /**
     * Determines whether the limit switch is in its limiting position (aka is pressed)
     * Accounts for the switch being active high or active low
     *
     * @return true if the switch is pressed, false otherwise
     */
    public boolean isLimiting() {
        if (limitSwitch.getMode() == DigitalChannel.Mode.OUTPUT) {
            LogDbz.e(TAG, "Attempted read on LimitSwitch while that pin was configured to output!");
            return true; //fail safe.  Something's wrong if we have reached this point
        }

        if (activeHigh)
            return limitSwitch.getState();
        else
            return !limitSwitch.getState();
    }

    public boolean isActiveHigh() {
        return activeHigh;
    }
}
