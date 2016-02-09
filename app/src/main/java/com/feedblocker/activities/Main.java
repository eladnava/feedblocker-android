package com.feedblocker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.feedblocker.R;
import com.feedblocker.services.LogcatReader;
import com.feedblocker.utils.ServiceManager;
import com.stericson.RootShell.RootShell;

public class Main extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set up activity UI
        initializeUI();

        // Make sure service is not running yet (verifyRootAccessAvailable() will hang if we're already monitoring the logcat due to a bug in RootShell)
        if (!ServiceManager.isServiceRunning(LogcatReader.class, this))
        {
            // Make sure we have root access (display dialog otherwise)
            if (verifyRootAccessAvailable())
            {
                // Re-start the logcat reader service (if not already running)
                ServiceManager.startLogcatReaderService(this);
            }
        }
    }

    private void initializeUI()
    {
        // Inflate main activity layout
        setContentView(R.layout.activity_main);

        // Support action bar for older devices
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set up a toolbar for old devices
        setSupportActionBar(toolbar);
    }

    private boolean verifyRootAccessAvailable()
    {
        // No "su" binary?
        if (!RootShell.isRootAvailable())
        {
            // Show fatal error dialog
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_root)
                    .setMessage(R.string.no_root_desc)
                    .setPositiveButton(R.string.ok, null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            // End the activity
                            finish();
                        }
                    })
                    .create().show();

            // No root
            return false;
        }

        // No root access given?
        if (!RootShell.isAccessGiven())
        {
            // Show fatal error dialog
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_root_access)
                    .setMessage(R.string.no_root_access_desc)
                    .setPositiveButton(R.string.ok, null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            // End the activity
                            finish();
                        }
                    })
                    .create().show();

            // No root access
            return false;
        }

        // We're good!
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu - this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void viewSettings()
    {
        // Start the settings activity
        startActivity(new Intent(Main.this, Settings.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks by ID
        switch (item.getItemId())
        {
            // Settings
            case R.id.action_settings:
                viewSettings();
                return true;
        }

        // Don't consume the event
        return super.onOptionsItemSelected(item);
    }
}
