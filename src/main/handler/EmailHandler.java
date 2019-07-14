package main.handler;

import mail.EmailService;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmailHandler {
    private final String emailFrom;
    private final String login;
    private final String password;
    private final String emailTo;
    private final String subject;
    private final String bodyText;
    private final List<File> files;

    public EmailHandler(String emailFrom, String login, String password, String emailTo, String subject, String bodyText, List<File> files) {
        this.emailFrom = emailFrom;
        this.login = login;
        this.password = password;
        this.emailTo = emailTo;
        this.subject = subject;
        this.bodyText = bodyText;
        this.files = files;
    }

    public void sendMail() throws IOException, MessagingException {
        EmailService emailService = new EmailService(emailFrom, password, emailTo, subject, bodyText, files);
        emailService.sendMail();
    }
}
