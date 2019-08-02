package com.nanicky.emailsender.scheduler;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    private Timer timer;
    private int diffMinutes;
    private final Label timeElapsedLabel;
    private final Runnable callback;
    private final Label timeDescLabel;

    public MyTimer(Label timeElapsedLabel, Runnable callback, Label timeDescLabel) {
        this.timeElapsedLabel = timeElapsedLabel;
        this.callback = callback;
        this.timeDescLabel = timeDescLabel;
    }

    public void setTimer(int diffMin) {
         timer = new Timer();
        this.diffMinutes = diffMin;
        timeDescLabel.setDisable(false);
        // create task
        TimerTask task = new TimerTask() {
            public void run() {
                if (diffMinutes > 0) {
                    int hours = diffMinutes / 60;
                    int minutes = diffMinutes % 60;
                    String timeLeft = LocalTime.of(hours, minutes).toString();
                    Platform.runLater(() -> timeElapsedLabel.setText(timeLeft));
                    System.out.println("Minutes Left:" + diffMinutes);
                    diffMinutes--;
                } else {
                    stop();
                    System.out.println("Run Callback");
                    callback.run();
                }
            }
        };
        //start
        timer.scheduleAtFixedRate(task, 0, Duration.of(1, ChronoUnit.MINUTES).toMillis());
    }

    public void stop() {
        System.out.println("Cancel Timer");
        timeDescLabel.setDisable(true);
        timeElapsedLabel.setText("");
        timer.cancel();
        timer = null;
    }

    public boolean isRunning() {
        return diffMinutes > 0;
    }
}
