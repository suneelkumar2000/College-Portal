package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.Exam;

public class ExamIdMapper implements RowMapper<Exam>{
	public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {
		Exam exam = new Exam();
		int id = rs.getInt("id");
		exam.setId(id);
		return exam;
	}
}
