package com.feedblocker.config;

public class RootCommands {
    // Shell command to get root privileges manually
    public static final String GAIN_ROOT_COMMAND = "su";

    // Shell command to read logcat indefinitely (get last log first using "-T 1" and then read only futuristic ones)
    public static final String LOGCAT_READ_COMMAND = "logcat -T 1";

    // Shell command to kill a process by name in order to close the feed activity
    public static final String KILL_PROCESS_BY_NAME = "ps -A | grep com.facebook.katana | grep -v grep | awk '{print $2}' | xargs kill";
}
