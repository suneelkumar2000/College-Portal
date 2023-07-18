package com.project.college_portal.interfaces;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.model.User;

public interface UserInterface {
	public int save(User saveUser) throws ExistMailIdException;
	public int login(User loginUser) throws InvalidMailIdException;
	public List<User> listUsers();
	public int forgotPassword(User user);
	public int findIdByEmail(String email);
	public int findById(int UserId, HttpSession session);
	public List<User> findByEmail(String email);
	public int studentsave(User User);
}