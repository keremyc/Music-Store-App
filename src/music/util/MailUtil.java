package music.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    public static void sendEmail(String from, String to,
                                 String subject, String body, boolean isBodyHTML){

        Properties pros = new Properties();
        pros.put("mail.transport.protocol", "smtps");
        pros.put("mail.smtps.host", "smtp.gmail.com");
        pros.put("mail.smtps.port", "465");
        pros.put("mail.smtps.auth", "true");
        pros.put("mail.smtps.quitwait", "false");

        Session session = Session.getDefaultInstance(pros);
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            if (isBodyHTML)
                message.setContent(body, "text/html");
            else
                message.setText(body);

            Address fromAddress = new InternetAddress(from);
            message.setFrom(fromAddress);
            Address toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            
            Transport trans = session.getTransport();
            trans.connect("mehmetsanli844@gmail.com", "123Mehmet");
            trans.sendMessage(message, message.getAllRecipients());
            trans.close();

        }catch (MessagingException exp){
            System.err.print(
                    "Unable to send email. \n" +
                            "You may need to configure your system as " +
                            "described in chapter 15. \n" +
                            "Here is the email you tried to send: \n" +
                            "=====================================\n" +
                            "TO: " + to + "\n" +
                            "FROM: " + from + "\n" +
                            "SUBJECT: " + subject + "\n" +
                            "\n" +
                            body + "\n\n");
            exp.printStackTrace();
        }



    }
}
