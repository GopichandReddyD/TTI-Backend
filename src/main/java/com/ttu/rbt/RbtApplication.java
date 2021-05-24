package com.ttu.rbt;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RbtApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbtApplication.class, args);
		
		final String username = "";  // like yourname@outlook.com
	    final String password = "";   // password here

	    Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "basic.smtp.ttu.edu");
        properties.put("mail.smtp.port", 587);
        Session session = Session.getInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tti@ttu.edu"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("gdoggala@ttu.edu"));
            message.setSubject("This is the Subject Line!");
            message.setText("This is actual message");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	    
	   /* Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "basic.smtp.ttu.edu");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	      });
	    session.setDebug(true);

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(username));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse("gdoggala@ttu.edu"));   // like inzi769@gmail.com
	        message.setSubject("Test");
	        message.setText("HI you have done sending mail with outlook");

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }*/
		
	}
	

}
