package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping(value = "/sendmail")
	public String sendmail() {

		emailService.sendMail("gopichandureddy179@gmail.com", "Test Subject", "Test mail");

		return "emailsent";
	}
	
	

}
