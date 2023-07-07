package com.example.Bright.College.Portal.model;

public class Attendance {
	private int userId ;
	private int totalDays ;
	private int daysAttended;
	private int daysLeave ;
	private int attendance;
	private boolean isActive ;
	
	public Attendance() {}
	
	public Attendance(int userId, int totalDays, int daysAttended, int daysLeave, int attendance, boolean isActive) {
		super();
		this.userId = userId;
		this.totalDays = totalDays;
		this.daysAttended = daysAttended;
		this.daysLeave = daysLeave;
		this.attendance = attendance;
		this.isActive = isActive;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public int getDaysAttended() {
		return daysAttended;
	}
	public void setDaysAttended(int daysAttended) {
		this.daysAttended = daysAttended;
	}
	public int getDaysLeave() {
		return daysLeave;
	}
	public void setDaysLeave(int daysLeave) {
		this.daysLeave = daysLeave;
	}
	public int getAttendance() {
		return attendance;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "Attendance [userId=" + userId + ", totalDays=" + totalDays + ", daysAttended=" + daysAttended
				+ ", daysLeave=" + daysLeave + ", attendance=" + attendance + ", isActive=" + isActive + "]";
	}
	
}
