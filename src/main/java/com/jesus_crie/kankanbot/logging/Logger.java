package com.jesus_crie.kankanbot.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static String PREFIX_INFO = "[Info] ";
    public static String PREFIX_WARNING = "[Warning] ";
    public static String PREFIX_ERROR = "[ERROR] ";

    public static void info(String str, LogFrom from) {
        System.out.println(PREFIX_DATE() + PREFIX_INFO + from + str);
        DiscordLogger.logToPrivateGuild(PREFIX_DATE() + PREFIX_INFO + from + str);
    }

    public static void warning(String str, LogFrom from) {
        System.out.println(PREFIX_DATE() + PREFIX_WARNING + from + str);
        DiscordLogger.logToPrivateGuild(PREFIX_DATE() + PREFIX_WARNING + from + str);
    }

    public static void error(String str, LogFrom from) {
        System.out.println(PREFIX_DATE() + PREFIX_ERROR + from + str);
        DiscordLogger.logToPrivateGuild(PREFIX_DATE() + PREFIX_ERROR + from + str);
    }

    private static String PREFIX_DATE() {
        return "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] ";
    }
}
