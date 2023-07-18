package com.project.college_portal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.dao.UserDao;

@Controller
public class HomeController {

	UserDao userDao = new UserDao();
	StaffDao staffDao = new StaffDao();

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
	public String studentHome(Model model, HttpSession session) throws JsonProcessingException {
		int UserId = (int) session.getAttribute("userId");
		userDao.findById(UserId, session);
		int value = userDao.findStudentSemesterById(UserId, model);
		System.out.println(value);
		if (value > 0) {
			model.addAttribute("semester", value);
		} else if (value == 0) {
			model.addAttribute("semester", "please complete your restration first");
		} else {
			model.addAttribute("semester", "You have Completed Your Course");
		}
		return "Home";
	}

	// method to get studentRegistration form
	@GetMapping(path = "/studentRegistration")
	public String studentProfile(Model model) throws JsonProcessingException {
		model.addAttribute("departmentList", staffDao.departmentList(model));
		return "studentRegistrationForm";
	}

	// method to get student attendance
	@GetMapping(path = "/attendance")
	public String attendance() {
		return "attendance";
	}

	// method to get logout
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
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
		return "redirect:/subjectlist";
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
	public String subjectForm(Model model) throws JsonProcessingException {
		model.addAttribute("departmentList", staffDao.departmentList(model));
		// model.addAttribute("semesterList", staffDao.semesterList(model));
		return "subjectForm";
	}
}
