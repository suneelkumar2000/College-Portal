package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Exam;

public class ExamMapper implements RowMapper<Exam>{
	public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exam exam = new Exam();
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String subjectId = rs.getString("subject_id");
		String type = rs.getString("type");
		Date date = rs.getDate("date_");
		boolean isActive = rs.getBoolean("is_active");
		exam.setId(id);
		exam.setName(name);
		exam.setSubjectId(subjectId);
		exam.setType(type);
		exam.setDate(date);
		exam.setActive(isActive);
		return exam;
	}

}
