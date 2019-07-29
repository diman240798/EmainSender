package com.nanicky.emailsender.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class EmailSender {

    public static void sendEmail(Session session, String fromEmail, String toEmail, String subject, String body, List<File> files) throws MessagingException, IOException {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // subject
            message.setSubject(subject);
            message.setSentDate(new Date());
            // message
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(new String(body.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8),"text/plain; charset=UTF-8");
            // files
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            for (File file : files) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(file);
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);

    }
}