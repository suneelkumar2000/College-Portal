package com.project.college_portal.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.model.Department;
import com.project.college_portal.model.User;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller
public class HomeController {
	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	UserDao userDao = new UserDao();

	// method to get index page
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}

	// method to get login form
	@GetMapping(path = "/login")
	public String login() {
		return "login";
	}

	// method to get register form
	@GetMapping(path = "/register")
	public String getSignUpForm() {
		return "signup";
	}

	// method to get admin home
	@GetMapping(path = "/adminHome")
	public String adminHome() {
		return "adminHome";
	}

	// method to get logout
	@GetMapping(path = "/logout")
	public String logout() {
		return "index";
	}

	// method to get Attendance Admin page
	@GetMapping(path = "/attendanceAdmin")
	public String adminAttendance() {
		return "attendanceAdmin";
	}

	// method to get result Admin page
	@GetMapping(path = "/resultAdmin")
	public String adminResult() {
		return "resultAdmin";
	}

	// method to get Subject Details
	@GetMapping(path = "/subjectDetails")
	public String subjectDetails() {
		return "subjectDetails";
	}

	// method to get Attendance form
	@GetMapping(path = "/insertAttendance")
	public String insertAttendance() {
		return "insertAttendance";
	}

	// method to get student profile
	/*
	 * @GetMapping(path = "") public String studentProfile() { return
	 * "studentProfile"; }
	 */

	// method to get department form
	@GetMapping(path = "/insertDepartmentForm")
	public String departmentForm() {
		return "departmentForm";
	}

}
