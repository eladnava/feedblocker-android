package com.feedblocker.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SystemServices {
    private static AlarmManager mAlarmManager;
    private static SharedPreferences mSharedPreferences;

    public static SharedPreferences getSharedPreferences(Context context) {
        // First time?
        if (mSharedPreferences == null) {
            // Acquire system service
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        // Return cached instance
        return mSharedPreferences;
    }

    public static AlarmManager getAlarmManager(Context context) {
        // First time?
        if (mAlarmManager == null) {
            // Acquire system service
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        // Return cached instance
        return mAlarmManager;
    }
}
