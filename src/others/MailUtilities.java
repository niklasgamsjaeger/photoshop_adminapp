package at.htlstp.fotoherfert4school_admin.others;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author 20110386
 */
public class MailUtilities {

    private static final boolean DEBUG = true;

    public static Session getGMailSession(String user, String pass) {
        final Properties props = new Properties();

        // Zum Senden
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.auth", "true");
//        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.smtp.port", "587");´
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
//        session.setDebug(MailUtilities.DEBUG);
        return session;
    }

    public static void postMail(Session session, String recipient,
            String subject, String message)
            throws MessagingException {
        Message msg = new MimeMessage(session);

        InternetAddress addressTo = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }

    public static void postHtml(Session session, String recipient, String subject,
            String htmlMsg) throws MessagingException {
        Message msg = new MimeMessage(session);

        InternetAddress addressTo = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(htmlMsg, "text/html");
        Transport.send(msg);
    }

    public static void postHtmlWithAttachement(Session session, String recipient,
            String subject, String htmlMsg, String path, String attachmentName) 
            throws MessagingException {
        MimeMultipart content = new MimeMultipart("mixed");

        MimeBodyPart html = new MimeBodyPart();
        html.setContent(htmlMsg, "text/html");
        content.addBodyPart(html);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(
                new DataHandler(new FileDataSource(path)));
        messageBodyPart.setFileName(attachmentName);
        content.addBodyPart(messageBodyPart);

        Message msg = new MimeMessage(session);

        InternetAddress addressTo = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(content);
        Transport.send(msg);
    }

    public static void postMultipartTextAndHtmlMail(
            Session session, String recipient,
            String subject, String txtMsg, String htmlMsg)
            throws MessagingException {
        MimeMultipart content = new MimeMultipart("alternative");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent(txtMsg, "text/text");
        content.addBodyPart(text);

        MimeBodyPart html = new MimeBodyPart();
        html.setContent(htmlMsg, "text/html");
        content.addBodyPart(html);

        Message msg = new MimeMessage(session);

        InternetAddress addressTo = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(content);
        Transport.send(msg);
    }

    public static void postAttachement(Session session, String recipient,
            String subject, String message,
            String filename)
            throws MessagingException {
        MimeMultipart content = new MimeMultipart("mixed");

        MimeBodyPart text = new MimeBodyPart();
        text.setText(message);
        content.addBodyPart(text);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(
                new DataHandler(new FileDataSource(filename)));
        messageBodyPart.setFileName(new File(filename).getName());
        content.addBodyPart(messageBodyPart);

        Message msg = new MimeMessage(session);

        InternetAddress addressTo = new InternetAddress(recipient);
        msg.setRecipient(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(content);
        Transport.send(msg);
    }

    public static void postConfirmationMail(Session session, String logoSrc, String recipient) throws MessagingException {
        // Create a default MimeMessage object.
        Message message = new MimeMessage(session);

        // Set From: header field of the header.
        //message.setFrom(new InternetAddress());
        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));

        // Set Subject: header field
        message.setSubject("Auftragsbestätigung");

        // This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("related");

        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<img src=\"cid:image\"><br /><h3>Herzlichen Dank für Ihren Auftrag!</h3><br /><br />Ihr Auftrag wurde an die zuständigen Voith-Mitarbeiter weitergeleitet. Diese werden sich in kürze mit Ihnen in Verbindung setzen. <br /> <br /> <small>Bitte antworten Sie nicht auf diese Email!</small>";
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(logoSrc);

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        // add image to the multipart
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);
        // Send message
        Transport.send(message);
    }

}
