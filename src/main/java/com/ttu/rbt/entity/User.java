package com.ttu.rbt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ttu.rbt.permissions.PermissionsEnum;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String uuid;

	private String fullName;

	private String mailId;

	private String password;

	private PermissionsEnum permissions;

	private Boolean firstLogin;

	private String restPasswordToken;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String uuid, String fullName, String mailId, String password, PermissionsEnum permissions,
			Boolean firstLogin) {
		super();
		this.uuid = uuid;
		this.fullName = fullName;
		this.mailId = mailId;
		this.password = password;
		this.permissions = permissions;
		this.firstLogin = firstLogin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PermissionsEnum getPermissions() {
		return permissions;
	}

	public void setPermissions(PermissionsEnum permissions) {
		this.permissions = permissions;
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getRestPasswordToken() {
		return restPasswordToken;
	}

	public void setRestPasswordToken(String restPasswordToken) {
		this.restPasswordToken = restPasswordToken;
	}

}
