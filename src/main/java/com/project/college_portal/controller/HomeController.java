package com.project.college_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.college_portal.dao.UserDao;

@Controller
public class HomeController {

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

	// method to get student home
	@GetMapping(path = "/studentHome")
	public String studentHome() {
		return "Home";
	}

	// method to get student attendance
	@GetMapping(path = "/attendance")
	public String attendance() {
		return "attendance";
	}

	// method to get logout
	@GetMapping(path = "/logout")
	public String logout() {
		return "index";
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

	// method to get department form
	@GetMapping(path = "/insertDepartmentForm")
	public String departmentForm() {
		return "departmentForm";
	}

	// method to get semester form
	@GetMapping(path = "/insertSemesterForm")
	public String semesterForm() {
		return "semesterForm";
	}

	// method to get subject form
	@GetMapping(path = "/insertSubjectForm")
	public String subjectForm() {
		return "subjectForm";
	}
}
