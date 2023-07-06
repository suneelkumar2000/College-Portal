package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.User;

public class ApprovingMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		int userId = rs.getInt("id");
		String roll = rs.getString("roll");
		user.setUserId(userId);
		user.setRoll(roll);
		return user;
		
	}
}
