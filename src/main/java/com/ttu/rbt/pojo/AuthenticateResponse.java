package com.ttu.rbt.pojo;

import com.ttu.rbt.entity.User;

public class AuthenticateResponse {

	private User user;

	private boolean tokenValid;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isTokenValid() {
		return tokenValid;
	}

	public void setTokenValid(boolean tokenValid) {
		this.tokenValid = tokenValid;
	}

}
