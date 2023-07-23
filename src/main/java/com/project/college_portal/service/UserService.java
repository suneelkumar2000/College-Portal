package com.project.college_portal.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.model.User;

public class UserService {
	UserDao userDao = new UserDao();

	// method to save register details
	public int saveUser(User user) throws ExistMailIdException {
		int value = userDao.save(user);
		return value;
	}

	// method to get Login success
	public int loginUser(User user, HttpSession session) throws InvalidMailIdException {
		int value = userDao.login(user);
		String email = user.getEmail();

		session.setAttribute("userId", userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute("userId");
		userDao.setUserSessionById(UserId, session);

		return value;
	}

	// method for forgot Password
	public int forgotPassword(User user, HttpSession session) {
		int value = userDao.forgotPassword(user);
		String email = user.getEmail();

		session.setAttribute("userId", userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute("userId");
		userDao.setUserSessionById(UserId, session);

		return value;
	}

	// method to update student Registration details
	public int studentsave(User user) {
		int value = userDao.studentsave(user);

		return value;
	}

	// method to find Student Semester By Id
	public int findStudentSemesterById(int UserId, Model model) throws JsonProcessingException {
		int semester = userDao.findStudentSemesterById(UserId, model);
		return semester;
	}

	// method to set User Session By Id
	public int setUserSessionById(int UserId, HttpSession session) throws JsonProcessingException {
		int semester = userDao.setUserSessionById(UserId, session);
		return semester;
	}

	// method to update student Semester details
	public void updateStudentSemester(Model model) throws JsonProcessingException {
		userDao.updateStudentSemester(model);
	}
}
