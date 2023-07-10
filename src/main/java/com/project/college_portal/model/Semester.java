package com.project.college_portal.model;

public class Semester {
	private int id;
	private boolean isActive;
	
	public Semester() {}

	public Semester(int id, boolean isActive) {
		super();
		this.id = id;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Semester [id=" + id + ", isActive=" + isActive + "]";
	}
}
