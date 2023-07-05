package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.User;

public class LoginMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs,int rowNum) throws SQLException {
		User user = new User();
		String email = rs.getString("email");
		String password = rs.getString("password");
		String roll = rs.getString("roll");
		user.setEmail(email);
		user.setPassword(password);
		user.setRoll(roll);
		return user;
	}
}
