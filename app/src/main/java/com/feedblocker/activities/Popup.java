package com.feedblocker.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.feedblocker.R;
import com.feedblocker.config.Logging;
import com.feedblocker.model.FeedApplication;
import com.feedblocker.scheduler.PopupScheduler;
import com.feedblocker.utils.AppPreferences;
import com.feedblocker.utils.ProcessManager;
import com.feedblocker.utils.ui.LayoutUtil;

import java.util.Random;

public class Popup extends AppCompatActivity {
    Button mOkay;
    Button mIgnore;
    FeedApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up activity UI
        initializeUI();

        // Read feed app from intent extras
        initializeFeedApplication();

        // Write to log
        Log.d(Logging.TAG, "Displayed popup");
    }

    private void initializeFeedApplication() {
        // Get app name from intent extras
        String app = getIntent().getStringExtra(getString(R.string.app_name_extra));

        try {
            // Instantiate it via reflection
            mApp = (FeedApplication) Class.forName(app).newInstance();
        } catch (Exception exc) {
            // Log error
            Log.e(Logging.TAG, "Failed to instantiate class by name: " + app, exc);
        }
    }

    @Override
    public void onBackPressed() {
        // Force blocking enabled?
        if (AppPreferences.isForcedBlockingEnabled(this)) {
            // Don't permit back press (force close feed)
            return;
        }

        // End the activity
        super.onBackPressed();
    }

    private void initializeUI() {
        // Inflate popup activity layout
        setContentView(R.layout.activity_popup);

        // Button views
        mOkay = (Button) findViewById(R.id.okay);
        mIgnore = (Button) findViewById(R.id.ignore);

        // Force blocking enabled?
        if (AppPreferences.isForcedBlockingEnabled(this)) {
            // No ignore button for you!
            mIgnore.setVisibility(View.GONE);
        }

        // Click listeners
        initializeListeners();
    }

    private void initializeListeners() {
        // "Okay" button click
        mOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Run okay button logic
                okayPressed();
            }
        });

        // "Ignore" button click
        mIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Run ignore button logic
                ignorePressed();
            }
        });
    }

    private void okayPressed() {
        // Show friendly toast to congratulate the user
        Toast.makeText(Popup.this, R.string.ok_toast, Toast.LENGTH_LONG).show();

        // Close popup
        finish();

        // Schedule close feed activity (wait for the popup to close first)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Close feed activity by PID
                ProcessManager.killSystemProcess(mApp.getAppProcessName());
            }
        }, 300);
    }

    private void ignorePressed() {
        // Get all ignore toast messages
        String[] messages = getResources().getStringArray(R.array.ignore_toasts);

        // Select a random one
        int randomIndex = new Random().nextInt(messages.length);

        // Show toast with random ignore message
        Toast.makeText(Popup.this, messages[randomIndex], Toast.LENGTH_LONG).show();

        // Re-schedule feed blocker popup
        PopupScheduler.schedulePopupDisplayIntent(mApp, Popup.this);

        // Close popup
        finish();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Get window object
        Window window = getWindow();

        // Center the popup
        LayoutUtil.centerPopupInParentWindow(window);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Fade out gradually
        overridePendingTransition(0, R.anim.fade_out);
    }
}
