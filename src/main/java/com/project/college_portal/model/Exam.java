package com.project.college_portal.model;

public class Exam {
	private int id;
	private int subjectId;
	private String name;
	private String type;
	private boolean isActive ;
	
	public Exam() {}

	public Exam(int id, int subjectId, String name, String type, boolean isActive) {
		super();
		this.id = id;
		this.subjectId = subjectId;
		this.name = name;
		this.type = type;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", subjectId=" + subjectId + ", name=" + name + ", type=" + type + ", isActive="
				+ isActive + "]";
	}
	
}
