package com.rmart.utilits;

import android.util.Log;

/**
 * Created by Satya Seshu on 14/09/20.
 */
public class LoggerInfo {

    public static void printLog(String from, Object message) {
        Log.d("RokadMart", String.format("%s  %s", from, message));
    }

    public static void errorLog(String from, Object message) {
        Log.e("RokadMart", "from " + from + " message " + message);
    }
}
