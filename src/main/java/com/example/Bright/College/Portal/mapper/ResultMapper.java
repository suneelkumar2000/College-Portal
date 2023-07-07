package com.example.Bright.College.Portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.Bright.College.Portal.model.Result;

public class ResultMapper implements RowMapper<Result>{
	public Result mapRow(ResultSet rs, int rowNum) throws SQLException {
		Result result = new Result();
		int examId = rs.getInt("exam_id");
		int userId = rs.getInt("user_id");
		int marks = rs.getInt("marks");
		boolean isActive = rs.getBoolean("is_active");
		result.setExamId(examId);
		result.setUserId(userId);
		result.setMarks(marks);
		result.setIsActive(isActive);
		return result;
		}
}
