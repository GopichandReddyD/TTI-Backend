package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.pojo.ContactUsEmail;
import com.ttu.rbt.service.EmailService;;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping(value = "/email/contactUS")
	public ResponseEntity<String>sendmail(@RequestBody ContactUsEmail contactUsEmail) {

		String subject = "TTI Contact Us | "+contactUsEmail.getSubject();
		String body = "Hi,\r\n please see the following response and click on the email address to reply back\r\n"+"Name:" + contactUsEmail.getName() + "\r\nEmail:" + contactUsEmail.getEmail() + "\r\nSubject:"
				+ contactUsEmail.getSubject() + "\r\nMessage:" + contactUsEmail.getMessage();
		
		System.out.println(body);
		
		emailService.sendMail("gdoggala@ttu.edu", subject, body);

		return new ResponseEntity<>("Email sent", HttpStatus.ACCEPTED);
	}

}
