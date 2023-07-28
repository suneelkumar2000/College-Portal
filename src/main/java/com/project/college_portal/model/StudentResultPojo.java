package com.project.college_portal.model;

public class StudentResultPojo {
	private int examId ;
	private String subjectId ;
	private String subjectName;
	private int semesterId ;
	private int marks;
	
	public StudentResultPojo() {}

	public StudentResultPojo(int examId, String subjectId, String subjectName, int semesterId, int marks) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.semesterId = semesterId;
		this.marks = marks;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "StudentResult [examId=" + examId + ", subjectId=" + subjectId + ", subjectName=" + subjectName + ", semesterId="
				+ semesterId + ", marks=" + marks + "]";
	}
	
	
}
