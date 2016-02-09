package com.feedblocker.utils;

import android.util.Log;

import com.feedblocker.config.RootCommands;
import com.feedblocker.config.Logging;

public class FakeKeyEvents
{
    public static void sendBackButtonKeyEvent()
    {
        // Log key event
        Log.d(Logging.TAG, "Sending back button event");

        try
        {
            // Execute the root command manually (workaround for the concurrent command freeze bug in RootShell)
            ProcessExecutor.runRootCommand(RootCommands.BACK_BUTTON_PRESS_COMMAND);
        }
        catch (Exception exc)
        {
            // Log error
            Log.e(Logging.TAG, "Failed to send back button event: " + exc.toString());
        }
    }

}
