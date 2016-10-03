package com.feedblocker.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.feedblocker.R;
import com.feedblocker.activities.Popup;
import com.feedblocker.config.Logging;
import com.feedblocker.integrations.Facebook;
import com.feedblocker.model.FeedApplication;
import com.feedblocker.utils.AppPreferences;
import com.feedblocker.utils.SystemServices;

public class PopupScheduler {
    public static void schedulePopupDisplayIntent(FeedApplication app, Context context) {
        // Cancel previously-scheduled popup intents
        cancelCurrentPopupDisplayIntents(app, context);

        // By default, 10 minutes is the feed browsing time limit
        int timeLimitMinutes = 10;

        // Set app-specific time limit (can't use switch-case with instanceof, unfortunately, so we'll have to do with "if" statement(s))
        if (app instanceof Facebook) {
            // Get the time limit configured in app settings
            timeLimitMinutes = AppPreferences.getFacebookTimeLimitMinutes(context);
        }

        // Define when to show the popup (convert time limit to millis)
        long triggerAtTimestamp = System.currentTimeMillis() + (timeLimitMinutes * 60 * 1000);

        // Schedule the popup activity by setting its trigger timestamp and pending intent
        SystemServices.getAlarmManager(context).set(AlarmManager.RTC, triggerAtTimestamp, getPopupPendingIntent(app, context));

        // Prepare user-friendly time limit countdown message
        String timeLimitString = timeLimitMinutes + " minute" + ((timeLimitMinutes != 1) ? "s" : "");

        // Show friendly toast with countdown
        Toast.makeText(context, context.getString(R.string.time_remaining, timeLimitString), Toast.LENGTH_SHORT).show();

        // Log the scheduled popup intent
        Log.d(Logging.TAG, "Scheduled popup in " + timeLimitString);
    }

    public static void cancelCurrentPopupDisplayIntents(FeedApplication app, Context context) {
        // Clear all previously-scheduled popup intents
        SystemServices.getAlarmManager(context).cancel(getPopupPendingIntent(app, context));
    }

    private static PendingIntent getPopupPendingIntent(FeedApplication app, Context context) {
        // Create new popup intent
        Intent popupIntent = new Intent();

        // Set class to popup activity
        popupIntent.setClass(context, Popup.class);

        // Add "app" with the target app's name (instantiate it by class name inside the popup activity since we can't pass it as an object)
        popupIntent.putExtra(context.getString(R.string.app_name_extra), app.getClass().getName());

        // Clear top, set as new task
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        // Convert to pending intent for use with AlarmManager
        return PendingIntent.getActivity(context, 0, popupIntent, 0);
    }
}
