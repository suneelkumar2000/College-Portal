package com.example.Bright.College.Portal.model;

public class Exam {
	private int id;
	private String subjectId;
	private String name;
	private String type;
	
	public Exam() {}
	
	public Exam(int id,String subjectId, String name, String type) {
		super();
		this.id = id;
		this.subjectId=subjectId;
		this.name = name;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", subjectId=" + subjectId + ", name=" + name + ", type=" + type + "]";
	}

	
	
}
