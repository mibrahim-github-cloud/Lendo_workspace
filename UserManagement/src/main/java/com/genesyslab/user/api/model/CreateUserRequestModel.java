package com.genesyslab.user.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message = "First Name should not be empty")
	@Size(min = 2, message = "First Name should be greater than 2 character")
	private String firstname;
	
	@NotNull(message = "Last Name should not be empty")
	@Size(min = 2, message = "Last Name should be greater than 2 character")
	private String lastname;
	
	@NotNull(message = "Email should not be empty")
	@Email
	private String email;
	
	@NotNull(message = "Password should not be empty")
	@Size(min = 8, max = 16, message = "Password should greater than 8 character and less than 16 character")
	private String password;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
