package com.money.moneyreminder.tool;

import android.util.Log;

import com.money.moneyreminder.BuildConfig;

public class MichaelLog {

    private static final boolean isDebug = BuildConfig.DEBUG;

    private static final String TAG = "Michael";

    public static void i(String message){
        if (isDebug){
            Log.i(TAG,message);
        }
    }

}
