package com.example.Bright.College.Portal.model;

public class Subject {
	private int id;
	private String name;
	private int semesterId;
	private String department;
	private boolean isActive;

	public Subject() {
	}

	public Subject(int id, String name, int semesterId, String department, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.semesterId = semesterId;
		this.department = department;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", semesterId=" + semesterId + ", department=" + department
				+ ", isActive=" + isActive + "]";
	}
	
}