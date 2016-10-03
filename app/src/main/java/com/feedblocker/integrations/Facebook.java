package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Facebook extends FeedApplication
{
    @Override
    public String[] getAppResumeLogIndicators()
    {
        return new String[]{
                "LocalFbBroadcastManager: Called registerBroadcastReceiver twice"
        };
    }

    @Override
    public String[] getAppPauseLogIndicators()
    {
        return new String[]{
                "moveTaskToBack: TaskRecord",
                "START u0 {act=com.android.systemui.recents.SHOW_RECENTS",
                "START u0 {act=android.intent.action.MAIN cat=[android.intent.category.HOME]"

        };
    }
}
