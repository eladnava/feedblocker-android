package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Facebook extends FeedApplication {
    @Override
    public Object[] getAppResumeLogIndicators() {
        return new Object[]{
                "Displayed com.facebook.katana/.LoginActivity",
                "onResume: com.facebook.katana.activity.FbMainTabActivity"
        };
    }

    @Override
    public Object[] getAppPauseLogIndicators() {
        return new Object[]{
                "onPause: com.facebook.katana.activity.FbMainTabActivity"
        };
    }

    @Override
    public String getAppProcessName() {
        return "com.facebook.katana";
    }
}
