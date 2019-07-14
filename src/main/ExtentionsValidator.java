package main;

import java.util.Arrays;
import java.util.List;

public class ExtentionsValidator {
    public static List<String> validateAndGet(String text) {
        return Arrays.asList(text.trim().replaceAll(" ", "").split(","));
    }
}
