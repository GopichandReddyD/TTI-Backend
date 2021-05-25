package com.ttu.rbt.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public void sendMail(String toEmail, String subject, String body) {

		Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "basic.smtp.ttu.edu");
        properties.put("mail.smtp.port", 25);
        Session session = Session.getInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tti@ttu.edu"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Sent Email successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
	
	

}
