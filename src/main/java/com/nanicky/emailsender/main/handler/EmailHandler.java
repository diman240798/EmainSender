package com.nanicky.emailsender.main.handler;

import com.nanicky.emailsender.mail.EmailService;
import com.nanicky.emailsender.model.UserData;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmailHandler {
    private final UserData userData;
    private final String emailTo;
    private final String subject;
    private final String bodyText;
    private final List<File> files;

    public EmailHandler(UserData userData, String emailTo, String subject, String bodyText, List<File> files) {
        this.userData = userData;
        this.emailTo = emailTo;
        this.subject = subject;
        this.bodyText = bodyText;
        this.files = files;
    }

    public void sendMail() throws IOException, MessagingException {
        EmailService emailService = new EmailService(userData.getEmail(), userData.getPassword(), emailTo, subject, bodyText, files);
        emailService.sendMail();
    }
}
