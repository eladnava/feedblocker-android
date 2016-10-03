package com.feedblocker.utils;

import com.feedblocker.config.RootCommands;

import java.io.DataOutputStream;

public class CommandExecutor {
    public static void runRootCommand(String cmd) throws Exception {
        // Get "su" root privileges (assumes we checked for RootShell.isAccessGiven() before calling this method)
        Process process = Runtime.getRuntime().exec(RootCommands.GAIN_ROOT_COMMAND);

        // Prepare the command output pipe
        DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());

        // Write our desired command + write a new line to execute it
        outputStream.writeBytes(cmd + "\n");

        // Add an "exit" command to close the shell when done
        outputStream.writeBytes("exit\n");

        // Actually send and execute the commands
        outputStream.flush();
    }
}
