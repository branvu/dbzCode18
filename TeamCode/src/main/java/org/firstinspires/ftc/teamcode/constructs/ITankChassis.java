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
     * This does not support going straight forwards or backwards
     *
     * @param speed   speed to move at in m/s (positive)
     * @param radius  radius to move at in m (positive to turn right, negative to turn left)
     *                this is relative to teh center of the robot as defined by an X between the
     *                wheels.
     * @param radians angle to move in radians (positive for fwd, negative for reverse)
     */
    void turn(double speed, double radius, double radians, boolean waitForCompletion);


    /**
     * Move forward or backward
     *
     * @param speed    speed to move at in m/s (positive)
     * @param distance distance to move in m (positive for fwd, negative for reverse)
     */
    void move(double speed, double distance, boolean waitForCompletion);

    /**
     * Drives the robot by setting velocity based on a normalized speed and turn factor
     * Intended for teleop
     *
     * @param normSpeed a speed on [0,1] that represents how fast to go
     * @param normTurn  a turning factor on [-1,1] representing how sharp to turn
     *                  0 is go straight, -1 is pointTurn left, 1 is pointTurn right
     */
    void drive(double normSpeed, double normTurn);

    /**
     * Drives the robot by setting a speed and a turning radius
     * Intended for autonomous
     *
     * @param speed  speed to move at in m/s (positive)
     * @param radius radius to turn at in m (positive for right turn, negative for left).
     *               Infinity to go straight.  0 to point turn
     */
    void realDrive(double speed, double radius);
}
