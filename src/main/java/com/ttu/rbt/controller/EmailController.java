package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.pojo.ContactUsEmail;
import com.ttu.rbt.pojo.ResetPasswordPojo;
import com.ttu.rbt.service.EmailService;;

@RestController
public class EmailController {

	public static final String TTI_URL = "http:tti.educ.ttu.edu/auth/reset-password/";
	
	@Autowired
	private EmailService emailService;

	// responsible for contact us email
	@PostMapping(value = "/email/contactUS")
	public ResponseEntity<String> sendMail(@RequestBody ContactUsEmail contactUsEmail) {

		String subject = "TTI Contact Us | " + contactUsEmail.getSubject();
		String body = "Hi,\r\n please see the following response and click on the email address to reply back\r\n"
				+ "Name:" + contactUsEmail.getName() + "\r\nEmail:" + contactUsEmail.getEmail() + "\r\nSubject:"
				+ contactUsEmail.getSubject() + "\r\nMessage:" + contactUsEmail.getMessage();

		try {
			emailService.sendMail("gdoggala@ttu.edu", subject, body);

			return new ResponseEntity<>("Email sent", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// responsible to verify user and send resetpasswordlink
	@PostMapping(value = "/forgotpassword/userName/{userName}")
	public ResponseEntity<String> sendResetLink(@PathVariable("userName") String userName) {
		try {
			String token = emailService.passwordResetLink(userName);

			String subject = "TTI Password reset";
			String URL = TTI_URL+token;
			emailService.sendMail(userName, subject, URL);

			return new ResponseEntity<>("resetpassword link sent successfully", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// responsible resetpassword from the token
	@PostMapping(value = "/forgotpassword/reset")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordPojo resetPassword) {
		try {
			emailService.resetPassword(resetPassword);

			return new ResponseEntity<>("password reset successful", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
