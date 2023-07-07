package com.example.Bright.College.Portal.model;

public class Result {
	private int examId ;
	private int userId ;
	private int marks ;
	private boolean isActive ;
	
	public Result() {}
	
	public Result(int examId, int userId, int marks, boolean isActive) {
		super();
		this.examId = examId;
		this.userId = userId;
		this.marks = marks;
		this.isActive = isActive;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean is_active) {
		this.isActive = is_active;
	}

	@Override
	public String toString() {
		return "Result [examId=" + examId + ", userId=" + userId + ", marks=" + marks + ", isActive=" + isActive
				+ "]";
	}
}
