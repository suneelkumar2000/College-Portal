package com.example.Bright.College.Portal.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.Bright.College.Portal.mapper.ApprovingMapper;
import com.example.Bright.College.Portal.mapper.UserMapper;
import com.example.Bright.College.Portal.model.User;

@Repository
public class StaffDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<User> studentList() {
		String sql = "select * from user where roll='student'";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}
	
	public List<User> approvedStudentList() {
		String sql = "select * from user where (roll='student' and status='approved')";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}
	
	public List<User> notApprovedStudentList() {
		String sql = "select * from user where (roll='student' and status='not approved')";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public int approve(User approveUser) {
		// TODO Auto-generated method stub
		String approve = "update user set status ='approved'  where User_ID=?";
		Object[] params ={ approveUser.getUserId()};
		int noOfRows = jdbcTemplate.update(approve, params);
		return 0;
	}
}

