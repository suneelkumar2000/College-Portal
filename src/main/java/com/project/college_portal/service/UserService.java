package com.project.college_portal.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.AttendanceUserIdException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.ForgotPasswordException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.model.AttendancePojo;
import com.project.college_portal.model.UserPojo;

public class UserService {
	UserDao userDao = new UserDao();
	
	String sessionUserId="userId";

	// method to save register details
	public int saveUser(UserPojo userPojo) throws ExistMailIdException {
		return userDao.save(userPojo);
	}

	// method to get Login success
	public int loginUser(UserPojo userPojo, HttpSession session) throws InvalidMailIdException {
		int value = userDao.login(userPojo);
		String email = userPojo.getEmail();

		session.setAttribute(sessionUserId, userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute(sessionUserId);
		userDao.setUserSessionById(UserId, session);

		return value;
	}

	// method for forgot Password
	public int forgotPassword(UserPojo userPojo, HttpSession session) throws ForgotPasswordException {
		int value = userDao.forgotPassword(userPojo);
		String email = userPojo.getEmail();

		session.setAttribute(sessionUserId, userDao.findIdByEmail(email));
		int UserId = (int) session.getAttribute(sessionUserId);
		userDao.setUserSessionById(UserId, session);

		return value;
	}

	// method to update student Registration details
	public int studentsave(UserPojo userPojo) {
		return userDao.studentsave(userPojo);
	}

	// method to find Student Semester By Id
	public int findStudentSemesterById(int UserId, Model model) throws JsonProcessingException {
		return userDao.findStudentSemesterById(UserId, model);
	}

	// method to set User Session By Id
	public int setUserSessionById(int UserId, HttpSession session) throws JsonProcessingException {
		return userDao.setUserSessionById(UserId, session);
	}

	// method to update student Semester details
	public void updateStudentSemester(Model model) throws JsonProcessingException {
		userDao.updateStudentSemester(model);
	}
	public List<AttendancePojo> findStudentAttendance(int userId,int semester, Model model) throws JsonProcessingException, AttendanceUserIdException{
		return userDao.findStudentAttendance(userId, semester, model);}
}
