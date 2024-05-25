package com.feedblocker.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.support.annotation.Nullable;

import com.feedblocker.config.RootCommands;
import com.feedblocker.config.Logging;
import com.feedblocker.integrations.Facebook;
import com.feedblocker.integrations.Instagram;
import com.feedblocker.model.FeedApplication;
import com.feedblocker.scheduler.PopupScheduler;
import com.feedblocker.utils.AppPreferences;
import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.execution.Command;

import java.util.ArrayList;
import java.util.List;

public class LogcatReader extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        // Log the event
        Log.d(Logging.TAG, "LogcatReader started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Make sure app is enabled and root access given
        if (!AppPreferences.isAppEnabled(this) || !RootShell.isAccessGiven()) {
            // Kill the service
            stopSelf();

            // Do nothing else and don't restart the service
            return START_NOT_STICKY;
        }

        try {
            // Start monitoring the logcat
            monitorLogcat();
        } catch (Exception exc) {
            // Log errors to logcat
            Log.e(Logging.TAG, "LogcatReader error", exc);
        }

        // Service should restart itself if killed by Android
        return START_STICKY;
    }

    private void monitorLogcat() throws Exception {
        // Write to log
        Log.d(Logging.TAG, "Monitoring the logcat");

        // Initialize command ID and timeout params
        int id = 0, timeout = 0;

        // Prepare the logcat reading command
        Command logcatCommand = new Command(id, timeout, RootCommands.LOGCAT_READ_COMMAND) {
            @Override
            public void commandOutput(int id, String line) {
                // Check for an app open/close log and schedule the popup accordingly
                handleNewLogLine(line);

                // MUST call the super method when overriding! (RootShell requirement)
                super.commandOutput(id, line);
            }

            @Override
            public void commandTerminated(int id, String error) {
                // Log error
                Log.e(Logging.TAG, "Logcat command terminated with error: " + error);
            }

            @Override
            public void commandCompleted(int id, int exitCode) {
                // Log completion
                Log.d(Logging.TAG, "Logcat command completed");
            }
        };

        // Execute the command (no need to wait on it as the service will keep running
        // and it will execute itself in a dedicated Handler thread created by RootShell)
        RootShell.getShell(true).add(logcatCommand);
    }

    private void handleNewLogLine(String log) {
        // Prepare a generic list of apps with a feed
        List<FeedApplication> apps = new ArrayList<>();

        // Monitor Facebook?
        if (AppPreferences.isFacebookFeedBlockingEnabled(this)) {
            // Add it to list of apps to monitor
            apps.add(new Facebook());
        }

        // Monitor Instagram?
        if (AppPreferences.isInstagramFeedBlockingEnabled(this)) {
            // Add it to list of apps to monitor
            apps.add(new Instagram());
        }

        // Traverse feed apps
        for (FeedApplication app : apps) {
            // App resumed?
            if (logMatchesIndicator(log, app.getAppResumeLogIndicators())) {
                // Log app resumed
                Log.d(Logging.TAG, app.getClass().getSimpleName() + " resumed");

                // Schedule the popup activity in X minutes
                PopupScheduler.schedulePopupDisplayIntent(app, this);
            }

            // App paused?
            if (logMatchesIndicator(log, app.getAppPauseLogIndicators())) {
                // Log app paused
                Log.d(Logging.TAG, app.getClass().getSimpleName() + " paused");

                // Cancel previously-scheduled popup activity intents
                PopupScheduler.cancelCurrentPopupDisplayIntents(app, this);
            }
        }
    }

    private boolean logMatchesIndicator(String log, Object[] indicators) {
        // Traverse indicators
        for (Object indicator : indicators) {
            if (indicator instanceof String) {
                // Log contains a part of the indicator?
                if (log.contains((String)indicator)) {
                    // We're good
                    return true;
                }
            }
            else if (indicator instanceof String[]) {
                // Traverse indicators
                for (String subIndicator : (String[])indicator) {
                    // Log contains a part of the indicator?
                    if (!log.contains(subIndicator)) {
                        // No match
                        return false;
                    }
                }

                // We have a match
                return true;
            }
        }

        // No match
        return false;
    }

    @Override
    public void onDestroy() {
        // Write to log
        Log.d(Logging.TAG, "LogcatReader destroyed");

        // Now we're ready to be destroyed
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Don't allow binding to this service
        return null;
    }
}
