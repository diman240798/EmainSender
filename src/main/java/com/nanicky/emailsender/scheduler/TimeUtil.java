package com.nanicky.emailsender.scheduler;

import com.nanicky.emailsender.util.TimeParser;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class TimeUtil {
    public static long getDiffMinutes(String timeStr) {
        LocalTime time = TimeParser.parse(timeStr);
        long diff = (MINUTES.between(LocalTime.now(), time) + 1440) % 1440;
        return diff;
    }
}
