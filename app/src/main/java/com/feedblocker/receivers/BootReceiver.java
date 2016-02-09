package com.feedblocker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.feedblocker.config.Logging;
import com.feedblocker.utils.ServiceManager;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Make sure the right intent was provided
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            // Log phone startup
            Log.d(Logging.TAG, "Boot completed");

            // Re-start the logcat listener daemon (if app is enabled and root access given)
            ServiceManager.startLogcatReaderService(context);
        }
    }
}