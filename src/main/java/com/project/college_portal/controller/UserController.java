package com.project.college_portal.controller;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.DepartmentException;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistExamException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.ExistSemesterIdException;
import com.project.college_portal.exception.ExistSubjectNameException;
import com.project.college_portal.exception.ForgotPasswordException;
import com.project.college_portal.exception.HigherAuthorityException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.exception.MarkException;
import com.project.college_portal.exception.SubjectIdException;
import com.project.college_portal.exception.UserIdException;
import com.project.college_portal.model.User;
import com.project.college_portal.service.StaffService;
import com.project.college_portal.service.UserService;

@Controller
public class UserController {
	
	String ErrorMessage = "ErrorMessage";
	String subjectList ="subjectList";

	UserDao userDao = new UserDao();
	UserService userService = new UserService();
	StaffService staffService = new StaffService();

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
		int value = userService.saveUser(user);

		if (value == 1) {
			return "index";
		}
		return "signup";

	}

	// method to get Login success
	@GetMapping(path = "/loginSubmit")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) throws InvalidMailIdException, JsonProcessingException {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		int value = userService.loginUser(user, session);

		if (value == 1) {
			return "redirect:/studentHome";
		} else if (value == 2) {
			return "redirect:/adminHome";
		} else
			return "index";
	}

	// method to get forgot password
	@GetMapping(path = "/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("phone") Long phone, HttpSession session) throws ForgotPasswordException {
		User user = new User();
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);
		int value = userService.forgotPassword(user, session);

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

		int value = userService.studentsave(user);

		if (value == 1) {
			return "redirect:/studentHome";
		}
		return "redirect:/studentRegistration";
	}

	// method to find Subject By semester
	@GetMapping(path = "/findsubjectListbySemester")
	public String findSubjectListBySemester(Model model, HttpSession session) throws JsonProcessingException {
		int semesterId = (int) session.getAttribute("semester");
		model.addAttribute(subjectList, staffService.findSubjectListBySemester(semesterId, model));
		return "subjectDetails";
	}

	// method to view student result
	@GetMapping(path = "/studentResult")
	public String findStudentResult(Model model, HttpSession session) throws JsonProcessingException {
		int userId = (int) session.getAttribute("userId");
		model.addAttribute(subjectList, userDao.findStudentResult(userId, model));
		return "studentResult";
	}

	// ----------Exception methods---------

	// method to handle ExistDepartmentNameException
	@ExceptionHandler(value = ExistDepartmentNameException.class)
	public String ExistDepartmentNameException(ExistDepartmentNameException exception, Model model) {
		model.addAttribute(ErrorMessage, "Department Already Exist");
		return "errorpopup";
	}

	// method to handle ExistExamException
	@ExceptionHandler(value = ExistExamException.class)
	public String ExistExamException(ExistExamException exception, Model model) {
		model.addAttribute(ErrorMessage, "Exam Already Exist");
		return "errorpopup";
	}

	// method to handle ExistMailIdException
	@ExceptionHandler(value = ExistMailIdException.class)
	public String ExistMailIdException(ExistMailIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Sorry! This Email Id Already Exist");
		return "errorpopup";
	}

	// method to handle InvalidMailIdException
	@ExceptionHandler(value = InvalidMailIdException.class)
	public String InvalidMailIdException(InvalidMailIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Sorry! Invalid Email Id And Password");
		return "errorpopup";
	}

	// method to handle ExamIdException
	@ExceptionHandler(value = ExamIdException.class)
	public String ExamIdException(ExamIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Exam Id dosen't Exist");
		return "errorpopup";
	}

	// method to handle MarkException
	@ExceptionHandler(value = MarkException.class)
	public String MarkException(MarkException exception, Model model) {
		model.addAttribute(ErrorMessage, "Invalid Marks ,Marks should be between 0 to 100");
		return "errorpopup";
	}

	// method to handle ExistSemesterIdException
	@ExceptionHandler(value = ExistSemesterIdException.class)
	public String ExistSemesterIdException(ExistSemesterIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Semester Already Exist");
		return "errorpopup";
	}

	// method to handle SubjectIdException
	@ExceptionHandler(value = SubjectIdException.class)
	public String SubjectIdException(SubjectIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Subject Id dosen't Exist");
		return "errorpopup";
	}

	// method to handle UserIdException
	@ExceptionHandler(value = UserIdException.class)
	public String UserIdException(UserIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "User dosen't Exist");
		return "errorpopup";
	}

	// method to handle HigherAuthorityException
	@ExceptionHandler(value = HigherAuthorityException.class)
	public String HigherAuthorityException(HigherAuthorityException exception, Model model) {
		model.addAttribute(ErrorMessage, "opps sorry! only HigherAuthority can do this Process");
		return "errorpopup";
	}

	// method to handle DepartmentException
	@ExceptionHandler(value = DepartmentException.class)
	public String DepartmentException(DepartmentException exception, Model model) {
		model.addAttribute(ErrorMessage, "Department dosen't Exist");
		return "errorpopup";
	}

	// method to handle ExistSubjectNameException
	@ExceptionHandler(value = ExistSubjectNameException.class)
	public String ExistSubjectNameException(ExistSubjectNameException exception, Model model) {
		model.addAttribute(ErrorMessage, "Subject Already Exist");
		return "errorpopup";
	}

	// method to handle ForgotPasswordException
	@ExceptionHandler(value = ForgotPasswordException.class)
	public String ForgotPasswordException(ForgotPasswordException exception, Model model) {
		model.addAttribute(ErrorMessage, "Sorry! Invalid Email Id or Phone Number");
		return "errorpopup";
	}
}
