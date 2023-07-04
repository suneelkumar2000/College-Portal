package com.example.Bright.College.Portal.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bright.College.Portal.dao.StaffDao;
import com.example.Bright.College.Portal.dao.UserDao;
import com.example.Bright.College.Portal.exception.ExistMailIdException;
import com.example.Bright.College.Portal.model.User;

@Controller
public class HomeController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserDao userDao;
	@Autowired
	StaffDao staffDao;

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

	// method to save register details
	@GetMapping(path = "/signup-submit")
	public String saveUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phone") Long phone, @RequestParam("gender") String gender, @RequestParam("roll") String roll,
			@RequestParam("DOB") Date DOB) {
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
		try {
			if (value == 1) {
				return "index";
			} else {
				throw new ExistMailIdException("Exist Email Exception");
			}
		} catch (ExistMailIdException e) {
			System.out.println("Exception: Email Id Already Exist");
			return "signup";
		}

	}

	// method to get Login success
	@GetMapping(path = "/login-submit")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		int value = userDao.login(user);
		if (value == 1) {
			return "home";
		} else if (value == 2) {
			return "adminHome";
		} else
			return "index";
	}

	@GetMapping(path = "/listofusers")
	public String getAllUser(Model model) {
		System.out.println("getting datas");
		List<User> users = staffDao.studentList();
		model.addAttribute("USER_LIST", users);
		return "listusers";
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

	// method to insert Attendance
	@GetMapping(path = "/insertAttendance")
	public String insertAttendance() {
		return "insertAttendance";
	}

	// method to approve student
	@GetMapping(path = "")
	public String approve(@RequestParam("userID") int userID) {
		User user = new User();
		user.setUserId(userID);
		int value = staffDao.approve(user);
		if (value == 1) {
			return null;
		}
		return null;
	}

	// method to get forgot password
	@GetMapping(path = "/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phone") Long phone) {
		User user = new User();
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);
		int value = userDao.forgotPassword(user);
		if (value == 1) {
			return "home";
		} else if (value == 2) {
			return "adminHome";
		} else
			return "index";
	}

}
