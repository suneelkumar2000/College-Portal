package com.project.college_portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.college_portal.model.StudentResult;

public class StudentResultMapper implements RowMapper<StudentResult>{
	public StudentResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StudentResult studentResult=new StudentResult();
		
		int examId = rs.getInt("exam_id");
		String subjectId = rs.getString("subject_id");
		String name = rs.getString("name");
		int semesterId = rs.getInt("semester_id");
		int marks = rs.getInt("marks");

		studentResult.setExamId(examId);
		studentResult.setSubjectId(subjectId);
		studentResult.setSubjectName(name);
		studentResult.setSemesterId(semesterId);
		studentResult.setMarks(marks);
		return studentResult ;
		
	}
}
