package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.User;

public class UserDepartmentMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		String department = rs.getString("department");
		user.setDepartment(department);
		return user;
	}
}
