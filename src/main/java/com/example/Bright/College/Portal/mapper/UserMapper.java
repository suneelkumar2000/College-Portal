package com.example.Bright.College.Portal.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.User;



public class UserMapper implements RowMapper<User>{
	
	@Override
	public User mapRow(ResultSet rs,int rowNum) throws SQLException {
		User user = new User();
		int userId = rs.getInt("User_ID");
		String email = rs.getString("Email");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String password = rs.getString("password");
		Long phone = rs.getLong("phone_number");
		Date DOB = rs.getDate("DOB");
		String gender = rs.getString("gender");
		String roll = rs.getString("roll");
		String status = rs.getString("status");
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
		return user;
	}
}

