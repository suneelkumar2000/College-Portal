package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Subject;

public class SubjectNameMapper implements RowMapper<Subject>{
	public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subject subject = new Subject();
		String name = rs.getString("name");
		subject.setName(name);
		return subject;
	}
	
}
