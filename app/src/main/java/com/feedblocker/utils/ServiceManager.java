package com.feedblocker.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.feedblocker.activities.Main;
import com.feedblocker.services.LogcatReader;
import com.stericson.RootShell.RootShell;

public class ServiceManager
{
    public static boolean isServiceRunning(Class<?> serviceClass, Context context)
    {
        // Get system-wide activity manager
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        // Traverse active services
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            // Target service is running?
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                // Service is definitely running
                return true;
            }
        }

        // No match
        return false;
    }

    public static void startLogcatReaderService(Context context)
    {
        // App disabled?
        if (!AppPreferences.isAppEnabled(context))
        {
            return;
        }

        // Service is already running? (check before verifying root access, otherwise isAccessGiven() hangs due to bug in RootShell)
        if (isServiceRunning(LogcatReader.class, context))
        {
            return;
        }

        // No root access (yet)?
        if (!RootShell.isAccessGiven())
        {
            return;
        }

        // Start the logcat reader service
        context.startService(new Intent(context, LogcatReader.class));
    }
}
