package com.project.college_portal.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		int userId = rs.getInt("id");
		String email = rs.getString("email");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String password = rs.getString("password");
		Long phone = rs.getLong("phone_number");
		Date DOB = rs.getDate("dob");
		String gender = rs.getString("gender");
		String roll = rs.getString("roll");
		String status = rs.getString("status");
		String department = rs.getString("department");
		String parentName = rs.getString("parent_name");
		int joiningYear = rs.getInt("year_of_joining");
		
		boolean isActive = rs.getBoolean("is_active");
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setDOB(DOB);
		user.setGender(gender);
		user.setRoll(roll);
		user.setStatus(status);
		user.setDepartment(department);
		user.setParentName(parentName);
		user.setActive(isActive);
		user.setJoiningYear(joiningYear);
		return user;
	}
}
