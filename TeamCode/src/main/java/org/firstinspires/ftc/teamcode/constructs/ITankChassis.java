package org.firstinspires.ftc.teamcode.constructs;

/**
 * Created by Matthew on 8/28/2017.
 */

public interface ITankChassis {
    double CHASSIS_WIDTH = 0.4; //m
    double WHEEL_RADIUS = 0.05; //m

    /**
     * Moves the robot in an arc of radius radius at speed speed for radians radians
     * Note that "turning right" does not depend on whether you are going fwd or not; it's the same direction
     *
     * This function does not support infinite radii; that is, going straight fwd or backwards
     *
     * @param speed   speed to move at in m/s (positive).  this is defined as the speed of the outer
     *                wheel during the turn
     * @param radius  radius to move at in m (positive to turn right, negative to turn left)
     *                this is relative to the center of the robot as defined by an X between the
     *                wheels. positive 0 for point turn right.  negative 0 for point turn left
     * @param radians angle to move in radians (positive for fwd, negative for reverse)
     */
    void turn(double speed, double radius, double radians, boolean waitForCompletion);


    /**
     * Move forward or backward
     *
     * @param speed    speed to move at in m/s (positive).  this is defined as the speed of either
     *                 motor during the turn, since they are the same
     * @param distance distance to move in m (positive for fwd, negative for reverse)
     */
    void move(double speed, double distance, boolean waitForCompletion);

    /**
     * Drives the robot by setting velocity based on a normalized speed and turn factor
     * Intended for teleop
     *
     * @param normSpeed a speed on [0,1] that represents how fast to go.  1 is fastest
     * @param normTurn  a turning factor on [-1,1] representing how sharp to turn
     *                  0 is go straight, -1 is a point left turn, 1 is point right turn
     */
    void drive(double normSpeed, double normTurn);

    /**
     * Drives the robot by setting a speed and a turning radius
     * This radius can be +- infinity or +-0
     * Intended for autonomous
     *
     * @param speed  speed to move at in m/s (positive) this is defined as the speed of the outer
     *               wheel during the turn
     * @param radius radius to turn at in m (positive for right turn, negative for left).
     *               +infinity to go forward.  -infinity to go backwards.  positive 0 for point turn
     *               right.  negative 0 for point turn left
     */
    void realDrive(double speed, double radius);
}
