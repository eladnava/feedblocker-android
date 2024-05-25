package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Facebook extends FeedApplication {
    @Override
    public String[] getAppResumeLogIndicators() {
        return new String[]{
                "Displayed com.facebook.katana/.LoginActivity",
                "onResume: com.facebook.katana.activity.FbMainTabActivity"
        };
    }

    @Override
    public String[] getAppPauseLogIndicators() {
        return new String[]{
                "onPause: com.facebook.katana.activity.FbMainTabActivity"
        };
    }

    @Override
    public String getAppProcessName() {
        return "com.facebook.katana";
    }
}
