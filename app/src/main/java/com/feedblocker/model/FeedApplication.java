package com.feedblocker.model;

public abstract class FeedApplication {
    // Return a list of log messages that indicate feed activity resumed (not started) in order to support maximizing a minimized feed
    public abstract Object[] getAppResumeLogIndicators();

    // Return a list of log messages that indicate feed activity paused (not finished) in order to support home button minimization
    public abstract Object[] getAppPauseLogIndicators();

    // Return the name of the app's process when running "adb shell ps"
    public abstract String getAppProcessName();
}
