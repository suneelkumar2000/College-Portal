package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Subject;

public class SubjectMapper implements RowMapper<Subject>{
	public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subject subject = new Subject();
		String id = rs.getString("id");
		String name = rs.getString("name");
		int semesterId = rs.getInt("semester_id");
		String department = rs.getString("department");
		boolean isActive = rs.getBoolean("is_active");
		subject.setId(id);
		subject.setName(name);
		subject.setSemesterId(semesterId);
		subject.setDepartment(department);
		subject.setActive(isActive);
		return subject;
	}

}
