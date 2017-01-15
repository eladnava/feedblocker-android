package com.feedblocker.integrations;

import com.feedblocker.model.FeedApplication;

public class Facebook extends FeedApplication {
    @Override
    public String[] getAppResumeLogIndicators() {
        return new String[]{
                "START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10200000 cmp=com.facebook.katana/.LoginActivity"
        };
    }

    @Override
    public String[] getAppPauseLogIndicators() {
        return new String[]{
                "moveTaskToBack: TaskRecord",
                "START u0 {act=com.android.systemui.recents.SHOW_RECENTS",
                "START u0 {act=android.intent.action.MAIN cat=[android.intent.category.HOME]"

        };
    }

    @Override
    public String getAppProcessName() {
        return "{facebook.katana}";
    }
}
