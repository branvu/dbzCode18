package org.firstinspires.ftc.teamcode.extensions;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/**
 * Created by Matthew on 8/26/2017.
 */

public class DbzDigitalChannel implements DigitalChannel, DbzDevice {
    final private static String TAG = DbzDigitalChannel.class.getName();
    private final DigitalChannel digitalChannel;

    public DbzDigitalChannel(DigitalChannel digitalChannel) {
        this.digitalChannel = digitalChannel;
    }

    public Mode getMode() {
        return digitalChannel.getMode();
    }

    public void setMode(Mode mode) {
        digitalChannel.setMode(mode);
    }

    public void setMode(DigitalChannelController.Mode mode) {
        digitalChannel.setMode(mode);
    }

    public boolean getState() {
        return digitalChannel.getState();
    }

    public void setState(boolean state) {
        digitalChannel.setState(state);
    }

    public Manufacturer getManufacturer() {
        return digitalChannel.getManufacturer();
    }

    public String getDeviceName() {
        return digitalChannel.getDeviceName();
    }

    public String getConnectionInfo() {
        return digitalChannel.getConnectionInfo();
    }

    public int getVersion() {
        return digitalChannel.getVersion();
    }

    public void resetDeviceConfigurationForOpMode() {
        digitalChannel.resetDeviceConfigurationForOpMode();
    }

    public void close() {
        digitalChannel.close();
    }
}
