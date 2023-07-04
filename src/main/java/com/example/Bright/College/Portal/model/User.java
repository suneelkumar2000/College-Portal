package com.example.Bright.College.Portal.model;

import java.sql.Date;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Long phone;
	private Date DOB;
	private String gender;
	private String roll;
	private String status;
	
	public User() {}
	
	public User(int userId,String firstName,String lastName,String email,String password,Long phone,Date DOB,String gender,String roll,String status) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email; 	
		this.password = password;
		this.phone = phone;
		this.DOB = DOB;
		this.gender = gender;
		this.roll = roll;
		this.setStatus(status);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", DOB=" + DOB + ", gender=" + gender + ", roll="
				+ roll +", status=" + status + "]";
	}
	
}
