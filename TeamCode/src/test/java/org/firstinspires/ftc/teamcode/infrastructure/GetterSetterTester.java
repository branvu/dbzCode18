package org.firstinspires.ftc.teamcode.infrastructure;

import android.support.annotation.Nullable;

import org.firstinspires.ftc.teamcode.utils.LogDbz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

/**
 * Created by Matthew on 8/27/2017.
 * <p>
 * Adapted from https://github.com/Blastman/DtoTester
 * (licensed into public domain through the UnLicense)
 * Primary changes: works on Java 7, tests more than one value, ignores immutables
 * doesn't require instance and class or use generics
 */

public class GetterSetterTester {
    final private static String TAG = GetterSetterTester.class.getName();

    /**
     * A map mapping every class to the values it will try for that class
     * try, as in, put into the setter and see if it comes out the getter
     */
    private Map<Class, Object[]> passValueMap = new HashMap<>();

    private void initPassValueMap() {
        passValueMap.put(double.class, new Double[]{0d, 1d});
        passValueMap.put(int.class, new Integer[]{0, 1});
        passValueMap.put(float.class, new Float[]{0f, 1f});
        passValueMap.put(short.class, new Short[]{0, 1});
        passValueMap.put(long.class, new Long[]{0L, 1L});
        passValueMap.put(byte.class, new Byte[]{(byte) 0, (byte) 1});

        passValueMap.put(boolean.class, new Boolean[]{true, false});
    }

    /**
     * Given a class to test (dut), runs unit testing for passthrough on every getter and setter method
     * that is, put some values into the setter and see if they come out the getter
     *
     * @param dutInstance        the instance of the dut that we are working with
     * @param passValueMapAddOns additional entries to add into the passValueMap
     */
    protected void testAllGettersAndSetters(Object dutInstance, @Nullable Map<Class, Object[]> passValueMapAddOns) {
        // all the methods of the dut that we need to run through
        Method[] methods = dutInstance.getClass().getMethods();

        // add any extra values to passValueMap.  null means don't bother with it
        initPassValueMap();
        if (passValueMapAddOns != null)
            this.passValueMap.putAll(passValueMapAddOns);


        /* Sort items for consistent test runs. */
        final SortedMap<String, GetterSetterPair> getterSetterMapping = new TreeMap<>();

        //go through every method and figure put them into GetterSetterPairs if applicable
        for (Method method : methods) {
            String methodName = method.getName();
            String objectName;

            if (methodName.startsWith("get") && method.getParameterTypes().length == 0) {
                /* Found the get method. */
                objectName = methodName.substring("get".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            } else if (methodName.startsWith("set") && method.getParameterTypes().length == 1) {
                /* Found the set method. */
                objectName = methodName.substring("set".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setSetter(method);
            } else if (methodName.startsWith("is") && method.getParameterTypes().length == 0) {
                /* Found the is method, which really is a get method. */
                objectName = methodName.substring("is".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            }
        }

        // Found all our mappings. Now call the getter and setter and see if it works (if we can)
        for (final Map.Entry<String, GetterSetterPair> entry : getterSetterMapping.entrySet()) {
            final GetterSetterPair pair = entry.getValue();
            final String objectName = entry.getKey();

            final String fieldName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);

            if (pair.hasGetterAndSetter()) {
                // create the list of objects to test
                final Class<?> parameterType = pair.getSetter().getParameterTypes()[0];
                final Object[] testers = createObject(objectName, fieldName, parameterType);
                if (testers == null)
                    continue;

                // test all the objects individually
                for (Object tester : testers) {
                    try {
                        pair.getSetter().invoke(dutInstance, tester);
                        callGetter(objectName, fieldName, pair.getGetter(), dutInstance, tester);
                    } catch (IllegalAccessException e) {
                        LogDbz.d(TAG, "Found inaccessible getter/setter method " + fieldName + " in " + objectName);
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        LogDbz.d(TAG, "Could not call getter/setter method " + fieldName + " in " + objectName);
                        e.printStackTrace();
                    }
                }
                LogDbz.v(TAG, "Passed getter/setter method for " + objectName + " " + fieldName);
            } else if (pair.getGetter() != null) {
                LogDbz.i(TAG, "Getter without a setter for " + fieldName + ".  Ignoring");
            } else {
                LogDbz.i(TAG, "Setter without a getter for " + fieldName + ".  Ignoring");
            }
        }
    }

    /**
     * Creates a list of objects of type clazz from passValueMap.  Used to test passthrough
     * with the getters and setters
     *
     * @param clazz the type of object to create
     * @return a list of objects of type clazz
     */
    private Object[] createObject(String objectName, String fieldName, Class<?> clazz) {
        if (clazz.isEnum()) {
            return clazz.getEnumConstants();
        }

        if (passValueMap.containsKey(clazz)) {
            return passValueMap.get(clazz);
        } else {
            LogDbz.w(TAG, objectName + " " + fieldName + " of type " + clazz.getName() + " is not in passValueMap.  Skipping");
            return null;
        }

    }

    /**
     * Calls a getter and verifies the result is what is expected.
     *
     * @param fieldName The field name (used for error messages).
     * @param getter    The get {@link Method}.
     * @param instance  The test instance.
     * @param expected  The expected result.
     * @throws IllegalAccessException    if this Method object is enforcing Java language access control and the underlying
     *                                   method is inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and the specified object argument is not an
     *                                   instance of the class or interface declaring the underlying method (or of a subclass or implementor
     *                                   thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to
     *                                   the corresponding formal parameter type by a method invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an exception.
     */
    private void callGetter(String objectName, String fieldName, Method getter, Object instance, Object expected)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        final Object getResult = getter.invoke(instance);

        if (getter.getReturnType().isPrimitive() || getter.getReturnType().isEnum()) {
            /* Calling assetEquals() here due to autoboxing of primitive to object type. */
            assertEquals(objectName + " " + fieldName + " does not have the expected value", expected, getResult);
        } else {
            /* This is a normal object. The object passed in should be the exactly same object we get back. */
            assertSame(objectName + " " + fieldName + " does not have the expected value", expected, getResult);
        }
    }

    /**
     * A utility class which holds a related getter and setter method.
     */
    private class GetterSetterPair {
        /**
         * The get method.
         */
        private Method getter;

        /**
         * The set method.
         */
        private Method setter;

        /**
         * Returns the get method.
         *
         * @return The get method.
         */
        public Method getGetter() {
            return getter;
        }

        /**
         * Returns the set method.
         *
         * @return The set method.
         */
        public Method getSetter() {
            return setter;
        }

        /**
         * Returns if this has a getter and setting method set.
         *
         * @return If this has a getter and setting method set.
         */
        public boolean hasGetterAndSetter() {
            return this.getter != null && this.setter != null;
        }

        /**
         * Sets the get Method.
         *
         * @param getter The get Method.
         */
        public void setGetter(Method getter) {
            this.getter = getter;
        }

        /**
         * Sets the set Method.
         *
         * @param setter The set Method.
         */
        public void setSetter(Method setter) {
            this.setter = setter;
        }
    }
}
