package org.firstinspires.ftc.teamcode;

import android.util.Log;

/**
 * Created by Matthew on 8/27/2017.
 */

public class LogDbz {
    final private static String TAG = LogDbz.class.getName();

    final private static boolean useAndroid = false;

    public static void v(String TAG, String msg) {
        if (useAndroid)
            Log.v(TAG, msg);
        else
            System.out.println(TAG + "/v: " + msg);
    }

    public static void w(String TAG, String msg) {
        if (useAndroid)
            Log.w(TAG, msg);
        else
            System.out.println(TAG + "/w: " + msg);
    }

    public static void d(String TAG, String msg) {
        if (useAndroid)
            Log.d(TAG, msg);
        else
            System.out.println(TAG + "/d: " + msg);
    }
}
