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
    public static void e(String TAG, String msg) {
        if (useAndroid)
            Log.e(TAG, msg);
        else
            System.out.println(TAG + "/e: " + msg);
    }

    public static void e(String TAG, String msg, Exception e) {
        if (useAndroid)
            Log.e(TAG, msg);
        else {
            System.out.println(TAG + "/e: " + msg);
            e.printStackTrace();
        }
    }
    public static void i(String TAG, String msg){
        if(useAndroid)
            Log.i(TAG,msg);
        else
            System.out.println(TAG + "/i:" + msg);
    }
    public static void wtf(String TAG, String msg){
        if(useAndroid)
            Log.wtf(TAG,msg);
        else
            System.out.println(TAG + "/wtf:" + msg);
    }
}
