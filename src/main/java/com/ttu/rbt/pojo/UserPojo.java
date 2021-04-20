package com.ttu.rbt.pojo;

import com.ttu.rbt.permissions.PermissionsEnum;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPojo {

	private String fullName;

	private String mailId;

	private String password;

	private PermissionsEnum permissions;
	
	private String uuid;

	public UserPojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserPojo(String fullName, String mailId, String password, PermissionsEnum permissions, String uuid) {
		super();
		this.fullName = fullName;
		this.mailId = mailId;
		this.password = password;
		this.permissions = permissions;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
