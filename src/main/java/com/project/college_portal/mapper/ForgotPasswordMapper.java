package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.User;

public class ForgotPasswordMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs,int rowNum) throws SQLException {
		User user = new User();
		String email = rs.getString("email");
		String password = rs.getString("password");
		String roll = rs.getString("roll");
		Long phone = rs.getLong("phone_number");
		
		user.setEmail(email);
		user.setPassword(password);
		user.setRoll(roll);
		user.setPhone(phone);
		return user;
	}

}