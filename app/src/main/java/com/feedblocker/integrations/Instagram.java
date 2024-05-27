package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Instagram extends FeedApplication {
    @Override
    public Object[] getAppResumeLogIndicators() {
        return new Object[]{
                new String[]{"Displayed", "com.instagram.android/com.instagram.mainactivity.InstagramMainActivity"},
                new String[]{"START u0", "com.instagram.android"}
        };
    }

    @Override
    public Object[] getAppPauseLogIndicators() {
        return new Object[]{
                new String[]{"moveTaskToBack", "com.instagram.android"},
                new String[]{"remove task", "com.instagram.android"}
        };
    }

    @Override
    public String getAppProcessName() {
        return "com.instagram.android";
    }
}
