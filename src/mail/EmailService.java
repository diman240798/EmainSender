package mail;


import mail.prop.PortType;
import mail.prop.PropertiesEmailUtil;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class EmailService {

    private final String fromEmail;
    private final String password;
    private final String toEmail;
    private final String subject;
    private final String body;
    private final List<File> files;

    public EmailService(String fromEmail, String password, String toEmail, String subject, String body, List<File> files) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.files = files;
    }

    public void sendMail() throws IOException, MessagingException {
       /* try {
            sendEmailByProps(PropertiesEmailUtil.propertiesViaSSL(fromEmail, password, PortType.SSL_465));
        } catch (MessagingException ex) {
            try {
                sendEmailByProps(PropertiesEmailUtil.propertiesViaSSL(fromEmail, password, PortType.SSL_587));
            } catch (MessagingException e) {*/
                sendEmailByProps(PropertiesEmailUtil.propertiesViaTLS(fromEmail, password));
//            }
//        }
    }

    private void sendEmailByProps(Properties properties) throws IOException, MessagingException {
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        EmailSender.sendEmail(session, fromEmail, toEmail, subject, body, files);
    }

}