package com.nanicky.emailsender.scheduler;

import com.nanicky.emailsender.util.TimeParser;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Scheduler {
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Runnable callback;

    public Scheduler(Runnable callback) {
        this.callback = callback;
    }

    public long shedule(String text) {
        LocalTime time = TimeParser.parse(text);
        long diff = (MINUTES.between(LocalTime.now(), time) + 1440) % 1440;
        scheduler.schedule(callback, diff, TimeUnit.MINUTES);
        return diff;
    }
}
