package com.ttu.rbt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ttu.rbt.permissions.PermissionsEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPojo {

private Integer id;
	
	private String name;
	
	private String mailId;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String password;
	
	private PermissionsEnum permissions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
}
