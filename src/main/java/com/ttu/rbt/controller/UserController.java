package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.entity.User;
import com.ttu.rbt.pojo.UserPojo;
import com.ttu.rbt.repo.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/add")
	public ResponseEntity<User> addNewUser(@RequestBody UserPojo userData) {

		User user = new User();
		user.setName(userData.getName());
		user.setMailId(userData.getMailId());
		user.setCity(userData.getCity());
		user.setState(userData.getState());
		user.setCountry(userData.getCountry());

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String Encoded = bCryptPasswordEncoder.encode(userData.getPassword());
		user.setPassword(Encoded);
		user.setPermissions(userData.getPermissions());

		return new ResponseEntity<>(userRepository.save(user), HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/delete")
	public ResponseEntity<String> deteleUser(@RequestBody UserPojo userData) {

		if (!userData.getMailId().isEmpty()) {
			User user = new User();
			user = userRepository.findByMailId(userData.getMailId());
			userRepository.delete(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(path = "/update")
	public ResponseEntity<User> updateUser(@RequestBody UserPojo userData) {
		User user = new User();
		if (!userData.getMailId().isEmpty()) {
			user = userRepository.findByMailId(userData.getMailId());

		} else if (!userData.getName().isEmpty()) {

			user = userRepository.findByName(userData.getName());
		}

		user.setName(userData.getName());
		user.setMailId(userData.getMailId());
		user.setCity(userData.getCity());
		user.setState(userData.getState());
		user.setCountry(userData.getCountry());

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String Encoded = bCryptPasswordEncoder.encode(userData.getPassword());
		user.setPassword(Encoded);
		user.setPermissions(userData.getPermissions());

		return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}

	@GetMapping(path = "/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

}
