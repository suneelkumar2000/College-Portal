package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.Department;

public class DepartmentMapper implements RowMapper<Department> {
	public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
		Department department = new Department();
		int id = rs.getInt("id");
		String department1 = rs.getString("department");
		boolean isActive = rs.getBoolean("is_active");
		department.setId(id);
		department.setDepartment(department1);
		department.setIsActive(isActive);
		return department;
	}
}
