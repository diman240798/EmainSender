package mail.prop;

import java.util.Properties;

public class PropertiesEmailUtil {

    public static Properties propertiesViaTLS(String emailFrom, String password) {
        Properties props = new Properties();
//        String host = "smtp." + emailFrom.split("@")[1];
        String host = "smtp.office365.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", emailFrom);
        props.put("mail.smtp.password", password);
        return props;
    }

    public static Properties propertiesViaSSL(String emailFrom, String password, PortType portType) {
        return propertiesViaSSL(emailFrom, password, portType.port);
    }

    private static Properties propertiesViaSSL(String emailFrom, String password, int port) {
        Properties props = new Properties();
//        String host = "smtp." + emailFrom.split("@")[1];
        String host = "smtp.office365.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.socketFactory.port", String.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.user", emailFrom);
        props.put("mail.smtp.password", password);
        return props;
    }

}
