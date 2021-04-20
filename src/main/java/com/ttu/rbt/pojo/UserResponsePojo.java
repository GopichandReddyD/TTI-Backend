package com.ttu.rbt.pojo;

import com.ttu.rbt.entity.User;

public class UserResponsePojo {

	private User user;

	private String token;

	public UserResponsePojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserResponsePojo(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
