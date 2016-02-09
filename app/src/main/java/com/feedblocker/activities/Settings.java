package com.feedblocker.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.feedblocker.R;
import com.feedblocker.utils.ui.compatibility.AppCompatPreferenceActivity;


public class Settings extends AppCompatPreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set-up activity UI
        initializeUI();
    }

    void displayBackButton()
    {
        // Support action bar for older devices
        ActionBar actionBar = getSupportActionBar();

        // Is the action bar available?
        if (actionBar != null)
        {
            // Show the back button in the action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    void initializeUI()
    {
        // Display home as back button
        displayBackButton();

        // Load settings from XML (find a better, non-deprecated way to do this)
        addPreferencesFromResource(R.xml.settings);
    }

    public boolean onOptionsItemSelected(final MenuItem Item)
    {
        // Handle item ID cases
        switch (Item.getItemId())
        {
            // Home button?
            case android.R.id.home:
                onBackPressed();
        }

        // Don't consume the event
        return super.onOptionsItemSelected(Item);
    }
}
