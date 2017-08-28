package org.firstinspires.ftc.teamcode.tests.infrastructure;

import org.junit.Test;

/**
 * Created by Matthew on 8/27/2017.
 */

public abstract class DbzUnitTester<T> extends GetterSetterTester<T> {
    final private static String TAG = DbzUnitTester.class.getName();

    @Test
    public abstract void testGettersSetters();
}
