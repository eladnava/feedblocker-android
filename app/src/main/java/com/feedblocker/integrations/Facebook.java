package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Facebook extends FeedApplication
{
    @Override
    public String[] getAppResumeLogIndicators()
    {
        // AppStateLogger to the rescue
        return new String[]{
                "Activity activity.FbMainTabActivity changed state to Started"
        };
    }

    @Override
    public String[] getAppPauseLogIndicators()
    {
        // AppStateLogger to the rescue
        return new String[]{
                "Activity activity.FbMainTabActivity changed state to Paused"
        };
    }
}
