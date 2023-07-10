package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Semester;

public class SemesterMapper implements RowMapper<Semester>{
	public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
		Semester semester = new Semester();
		int id = rs.getInt("id");
		boolean isActive = rs.getBoolean("is_active");
		semester.setId(id);
		semester.setActive(isActive);
		return semester;
	}

}
