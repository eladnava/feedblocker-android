package com.feedblocker.config;

public class RootCommands {
    // Shell command to get root privileges manually (for ProcessExecutor)
    public static final String GAIN_ROOT_COMMAND = "su";

    // Shell command to read logcat indefinitely (get last log first using "-T 1" and then read only futuristic ones)
    public static final String LOGCAT_READ_COMMAND = "logcat -T 1";

    // Shell command to fake a back button press in order to close the currently focused activity (key code 4 is back button)
    public static final String BACK_BUTTON_PRESS_COMMAND = "input keyevent 4";
}
