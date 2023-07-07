package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.Attendance;

public class AttendanceMapper implements RowMapper<Attendance>{
	public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attendance attendance = new Attendance();
		int userId = rs.getInt("user_id");
		int totalDays = rs.getInt("total_days");
		int daysAttended = rs.getInt("days_attended");
		int daysLeave = rs.getInt("days_leave");
		int attendancePercentage = rs.getInt("attendance");
		boolean isActive = rs.getBoolean("is_active");
		attendance.setUserId(userId);
		attendance.setTotalDays(totalDays);
		attendance.setDaysAttended(daysAttended);
		attendance.setDaysLeave(daysLeave);
		attendance.setAttendance(attendancePercentage);
		attendance.setActive(isActive);
		return attendance;
		}

}
