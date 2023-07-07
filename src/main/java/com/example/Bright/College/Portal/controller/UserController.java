package com.example.Bright.College.Portal.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bright.College.Portal.dao.UserDao;
import com.example.Bright.College.Portal.exception.ExistMailIdException;
import com.example.Bright.College.Portal.model.User;

@Controller
public class UserController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserDao userDao;

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
