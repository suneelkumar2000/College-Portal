package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Subject;

public class SubjectIdMapper implements RowMapper<Subject>{
	public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subject subject = new Subject();
		String id = rs.getString("id");
		subject.setId(id);
		return subject;
	}
}
