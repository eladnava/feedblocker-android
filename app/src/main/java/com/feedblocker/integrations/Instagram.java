package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Instagram extends FeedApplication {
    @Override
    public Object[] getAppResumeLogIndicators() {
        return new Object[]{
                "Displayed com.instagram.android/com.instagram.mainactivity.InstagramMainActivity",
                "START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10200000 cmp=com.instagram.android/.activity.MainTabActivity"
        };
    }

    @Override
    public Object[] getAppPauseLogIndicators() {
        return new Object[]{
                "com.instagram.android U=0 visible=true visibleRequested=true",
                new String[]{"remove task", "com.instagram.android"}
        };
    }

    @Override
    public String getAppProcessName() {
        return "com.instagram.android";
    }
}
