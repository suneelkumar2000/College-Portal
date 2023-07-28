package com.project.college_portal.interfaces;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.ForgotPasswordException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.model.StudentResultPojo;
import com.project.college_portal.model.User;

public interface UserInterface {
	public int save(User saveUser) throws ExistMailIdException;
	public int login(User loginUser) throws InvalidMailIdException;
	public List<User> listUsers();
	public int forgotPassword(User user) throws ForgotPasswordException;
	public int findIdByEmail(String email);
	public int setUserSessionById(int UserId, HttpSession session);
	public List<User> findByEmail(String email);
	public int studentsave(User User);
	public void updateStudentSemester(Model model) throws JsonProcessingException ;
	public int findStudentSemesterById(int userid, Model model) throws JsonProcessingException;
	public List<StudentResultPojo> findStudentResult(int userid, Model model) throws JsonProcessingException ;
}
