package com.project.college_portal.model;

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
	private String department;
	private String parentName;
	private int year_of_joining;
	private boolean isActive;

	public User() {
	}

	public User(int userId, String firstName, String lastName, String email, String password, Long phone, Date DOB,
			String gender, String roll, String status, String department, String parentName, int year_of_joining,
			boolean isActive) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.DOB = DOB;
		this.gender = gender;
		this.roll = roll;
		this.status = status;
		this.department = department;
		this.parentName = parentName;
		this.year_of_joining = year_of_joining;
		this.isActive = isActive;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getYear_of_joining() {
		return year_of_joining;
	}

	public void setYear_of_joining(int year_of_joining) {
		this.year_of_joining = year_of_joining;
	}

	public boolean isIsActive() {
		return isActive;
	}

	public void setIs_active(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", DOB=" + DOB + ", gender=" + gender + ", roll="
				+ roll + ", status=" + status + ", department=" + department + ", parentName=" + parentName
				+ ", year_of_joining=" + year_of_joining + ", isActive=" + isActive + "]";
	}

}
