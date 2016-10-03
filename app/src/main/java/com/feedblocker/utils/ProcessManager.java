package com.feedblocker.utils;

import android.util.Log;

import com.feedblocker.config.RootCommands;
import com.feedblocker.config.Logging;

public class ProcessManager {
    public static void killSystemProcess(String processName) {
        // Log process kill
        Log.d(Logging.TAG, "Killing process by name:" + processName);

        try {
            // Execute the root command manually (workaround for the concurrent command freeze bug in RootShell)
            CommandExecutor.runRootCommand(String.format(RootCommands.KILL_PROCESS_BY_NAME, processName));
        } catch (Exception exc) {
            // Log error
            Log.e(Logging.TAG, "Failed to kill feed activity: " + exc.toString());
        }
    }

}
