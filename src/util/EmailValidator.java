package util;

public class EmailValidator {
    public static boolean validate(String email) {
        return email.matches(".+@.+\\..+");
    }
}
