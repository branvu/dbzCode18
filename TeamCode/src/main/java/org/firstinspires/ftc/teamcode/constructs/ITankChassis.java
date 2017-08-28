package org.firstinspires.ftc.teamcode.constructs;

/**
 * Created by Matthew on 8/28/2017.
 */

public interface ITankChassis {
    double CHASSIS_WIDTH = 0.4; //m
    double WHEEL_RADIUS = 0.1; //m

    /**
     * Moves the robot in an arc of radius radius at speed speed for radians radians
     * Note that "turning right" does not depend on whether you are going fwd or not; it's the same direction
     *
     * @param speed   speed to move at in m/s (positive)
     * @param radius  radius to move in m (positive to turn right, negative to turn left)
     * @param radians angle to move in radians (positive for fwd, negative for reverse)
     */
    void turn(double speed, double radius, double radians, boolean waitForCompletion);

    /**
     * Performs a point turn.  The speed is the speed of the wheels against the ground
     *
     * @param speed   speed to move at in m/s (positive)
     * @param radians angle to move in radians (positive for fwd, negative for reverse)
     */
    void pointTurn(double speed, double radians, boolean waitForCompletion);

    /**
     * Move forward or backward
     *
     * @param speed    speed to move at in m/s (positive)
     * @param distance distance to move in m (positive for fwd, negative for reverse)
     */
    void move(double speed, double distance, boolean waitForCompletion);
}
