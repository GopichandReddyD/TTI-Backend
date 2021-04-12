package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.entity.User;
import com.ttu.rbt.pojo.LoginPojo;
import com.ttu.rbt.repo.UserRepository;

@RestController
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/login")
	public ResponseEntity<User> login(@RequestBody LoginPojo loginDetails) {
		User user = new User();
		user = userRepository.findByMailId(loginDetails.getMailId());

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		Boolean match = bCryptPasswordEncoder.matches(loginDetails.getPassword(), user.getPassword());

		if (match.equals(true)) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
