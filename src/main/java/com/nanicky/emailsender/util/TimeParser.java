package com.nanicky.emailsender.util;

import java.time.LocalTime;

public class TimeParser {
    public static LocalTime parse(String text) {
        String[] split = text.split(":");
        int hours = Integer.parseInt(split[0]);
        int minutes = Integer.parseInt(split[1]);

        if (hours < 0 || hours > 23) throw new RuntimeException("Incorrect Hours");
        if (minutes < 0 || minutes > 59) throw new RuntimeException("Incorrect minutes");

        LocalTime time = LocalTime.of(hours, minutes);
        return time;
    }
}
