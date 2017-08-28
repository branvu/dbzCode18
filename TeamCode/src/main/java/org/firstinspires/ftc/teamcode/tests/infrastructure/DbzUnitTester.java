package org.firstinspires.ftc.teamcode.tests.infrastructure;

import org.junit.Test;

/**
 * A base class for all of our unit tests to extend
 */

public abstract class DbzUnitTester extends GetterSetterTester {
    final private static String TAG = DbzUnitTester.class.getName();

    /**
     * Tests all the getter and setters for the particular dut
     */
    @Test
    public abstract void testGettersSetters();
}
