package com.project.college_portal.controller;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.model.User;

@Controller
public class UserController {

	UserDao userDao = new UserDao();
	StaffDao staffDao = new StaffDao();

	// --------- user method ---------

	// method to save register details
	@GetMapping(path = "/signupSubmit")
	public String saveUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phone") Long phone, @RequestParam("gender") String gender, @RequestParam("roll") String roll,
			@RequestParam("DOB") Date DOB) throws ExistMailIdException {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setDOB(DOB);
		user.setGender(gender);
		user.setRoll(roll);
		int value = userDao.save(user);

		if (value == 1) {
			return "index";
		}
		return "signup";

	}

	// method to get Login success
	@GetMapping(path = "/loginSubmit")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) throws InvalidMailIdException {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		int value = userDao.login(user);

		session.setAttribute("userId", userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute("userId");
		userDao.findById(UserId, session);

		if (value == 1) {
			return "redirect:/studentHome";
		} else if (value == 2) {
			return "adminHome";
		} else
			return "index";
	}

	// method to get forgot password
	@GetMapping(path = "/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phone") Long phone, HttpSession session) {
		User user = new User();
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);
		int value = userDao.forgotPassword(user);

		session.setAttribute("userId", userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute("userId");
		userDao.findById(UserId, session);

		if (value == 1) {
			return "redirect:/studentHome";
		} else if (value == 2) {
			return "adminHome";
		} else
			return "index";
	}

	// --------- student method ---------

	// method to update student Registration details
	@GetMapping(path = "/studentsave")
	public String studentsave(@RequestParam("phone") Long phone, @RequestParam("DOB") Date DOB,
			@RequestParam("department") String department, @RequestParam("year") int year,
			@RequestParam("parentName") String parentName, HttpSession session) {
		int UserId = (int) session.getAttribute("userId");
		User user = new User();
		user.setUserId(UserId);
		user.setPhone(phone);
		user.setDOB(DOB);
		user.setDepartment(department);
		user.setParentName(parentName);
		user.setJoiningYear(year);

		int value = userDao.studentsave(user);

		if (value == 1) {
			return "redirect:/studentHome";
		}
		return "redirect:/studentRegistration";
	}
	
	// method to find Subject By semester
	@GetMapping(path = "/findsubjectListbySemester")
	public String findSubjectListBySemester(Model model, HttpSession session) throws JsonProcessingException {
		int semesterId = (int) session.getAttribute("semester");
		model.addAttribute("subjectList", staffDao.findSubjectBySemester(semesterId,model));
		return "subjectDetails";
	}
}
