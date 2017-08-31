package org.firstinspires.ftc.teamcode.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * A base class for all of our unit tests to extend
 */

@RunWith(MockitoJUnitRunner.class)
public abstract class DbzUnitTester extends GetterSetterTester {
    final private static String TAG = DbzUnitTester.class.getName();

    /**
     * Tests all the getter and setters for the particular dut
     */
    @Test
    public abstract void testGettersSetters();
}
