package com.ishanitech.ipalika.service.impl;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.ishanitech.ipalika.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    // Config parameter for email api which is used to send mail to user.
    private final String HOST = "mail.ishanitech.com";
    private final String EMAIL = "test@ishanitech.com";
    private final String PASSWORD = "Password1*#";
    private final String PORT = "587";
    private final String AUTH = "true";
    private final String ENABLE_TLS = "true";
    private final String SUBJECT = "टोकन बीबर्रण ";


    public void sendEmail(String msg, String to) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", ENABLE_TLS);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {

	   	      /*  Create an instance of MimeMessage,
	   	          it accept MIME types and headers
	   	      */

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(SUBJECT,"UTF-8");
            message.setText(msg, "UTF-8","html" );

            /* Transport class is used to deliver the message to the recipients */
            Transport.send(message);
        }
        catch(Exception e) {
            log.error("Error: {}", e.getLocalizedMessage());
        }
    }


    public void sendAccountVerifyEmail(String msg, String to, String url) {
        String subject = "तपाई को सिफारिस स्वीकृत भए  को  छ |";

        Properties props = new Properties();
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", ENABLE_TLS);
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {

	   	      /*  Create an instance of MimeMessage,
	   	          it accept MIME types and headers
	   	      */

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject ,"UTF-8");
            message.setText(msg, "UTF-8","html");

            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart textMime = new MimeBodyPart();
            MimeBodyPart fileMime = new MimeBodyPart();

            textMime.setText(msg);

            File file=new File(url);
            fileMime.attachFile(file);

            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(fileMime);
            message.setContent(mimeMultipart);
            /* Transport class is used to deliver the message to the recipients */
            Transport.send(message);
        }
        catch(Exception e) {
            log.error("Error: {}", e.getLocalizedMessage());
        }
    }
}
