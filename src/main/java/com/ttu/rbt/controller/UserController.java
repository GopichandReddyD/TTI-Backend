package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.entity.User;
import com.ttu.rbt.pojo.AuthenticateResponse;
import com.ttu.rbt.pojo.LoginPojo;
import com.ttu.rbt.pojo.UserPojo;
import com.ttu.rbt.pojo.UserResponsePojo;
import com.ttu.rbt.repo.UserRepository;
import com.ttu.rbt.service.UserService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/add")
	public ResponseEntity<UserResponsePojo> addNewUser(@RequestBody UserPojo userData) {

		return new ResponseEntity<>(userService.addUser(userData), HttpStatus.ACCEPTED);
	}

	@PutMapping(path = "/update/password/{uuid}/{password}")
	public ResponseEntity<String> updateUserPassword(@PathVariable("uuid") String uuid,
			@PathVariable("password") String password) {

		userService.updateUserPassword(uuid, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/update")
	public ResponseEntity<User> updateUser(@RequestBody UserPojo userData) {

		return new ResponseEntity<>(userService.updateUser(userData), HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete")
	public ResponseEntity<String> deteleUser(@RequestBody LoginPojo loginDetails) {

		User user = userRepository.findByMailId(loginDetails.getMailId());
		if (user != null) {

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Boolean match = bCryptPasswordEncoder.matches(loginDetails.getPassword(), user.getPassword());

			if (match.equals(true)) {

				userRepository.delete(user);
				
				return new ResponseEntity<>("user delete sucessfull", HttpStatus.OK);
				
			}
		}
		return new ResponseEntity<>("credentials didn't  match", HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/authenticate/{token}/{uuid}")
	public ResponseEntity<AuthenticateResponse> getToken(@PathVariable("token") String token,
			@PathVariable("uuid") String uuid) {

		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		try {
			Claims claims = userService.decodeJWT(token, uuid);
			User user = userRepository.findByUuid(uuid);
			authenticateResponse.setUser(user);
			authenticateResponse.setTokenValid(true);
		} catch (Exception e) {
			// TODO: handle exception
			authenticateResponse.setUser(null);
			authenticateResponse.setTokenValid(false);
			System.out.println("error:" + e.getMessage());
		}

		// claims.

		return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);

	}

	@PostMapping(path = "/login")
	public ResponseEntity<UserResponsePojo> login(@RequestBody LoginPojo loginDetails) {

		return new ResponseEntity<>(userService.login(loginDetails), HttpStatus.OK);
	}

}
