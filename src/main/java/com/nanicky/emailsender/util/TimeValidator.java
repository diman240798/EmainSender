package com.nanicky.emailsender.util;

public class TimeValidator {
    public static boolean validate(String time) {
        return time.matches("\\d(\\d)?:\\d(\\d)?");
    }
}
