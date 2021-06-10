package com.ttu.rbt.service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ttu.rbt.entity.User;
import com.ttu.rbt.exceptions.EmailNotSentException;
import com.ttu.rbt.exceptions.UserNotFound;
import com.ttu.rbt.pojo.ResetPasswordPojo;
import com.ttu.rbt.repo.UserRepository;

@Service
public class EmailService {

	@Autowired
	private UserRepository userRepository;

	public void sendMail(String toEmail, String subject, String body) throws EmailNotSentException {

		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "false");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "basic.smtp.ttu.edu");
		properties.put("mail.smtp.port", 25);
		Session session = Session.getInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@tti.educ.ttu.edu"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			System.out.println("Sent Email successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();  
			throw new EmailNotSentException("Problem in sending Email");
		}
	}

	public String passwordResetLink(String emailID) throws UserNotFound {
		User user = userRepository.findByMailId(emailID);

		if (user != null) {
			String resetToken = UUID.randomUUID().toString();
			user.setRestPasswordToken(resetToken);
			userRepository.save(user);
			return resetToken;

		} else {
			throw new UserNotFound("Could not find any customer with the email " + emailID);
		}
	}

	public void resetPassword(ResetPasswordPojo resetPassword) throws UserNotFound {
		User user = userRepository.findByRestPasswordToken(resetPassword.getToken());

		if (user != null) {

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encoded = bCryptPasswordEncoder.encode(resetPassword.getPassword());

			user.setPassword(encoded);
			userRepository.save(user);
		} else {
			throw new UserNotFound("User not found, token given is wrong please try again with a new request");
		}
	}

}
