package com.project.college_portal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistExamException;
import com.project.college_portal.exception.ExistSemesterIdException;
import com.project.college_portal.exception.ExistSubjectNameException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.DepartmentException;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.MarkException;
import com.project.college_portal.exception.SubjectIdException;
import com.project.college_portal.exception.UserIdException;
import com.project.college_portal.model.Subject;
import com.project.college_portal.model.User;
import com.project.college_portal.service.StaffService;
import com.project.college_portal.service.UserService;
import com.project.college_portal.exception.HigherAuthorityException;

@Controller
public class HomeController {

	StaffDao staffDao = new StaffDao();
	UserDao userDao = new UserDao();
	UserService userService = new UserService();
	StaffService staffService = new StaffService();
	@Value("${email}")
	String email;

	// method to get index page
	@GetMapping(path = "/index")
	public String index() {
		System.out.println("Email : " + email);
		return "index";
	}

	// method to get register form
	@GetMapping(path = "/register")
	public String getSignUpForm() {
		return "signup";
	}

	// method to get admin home
	@GetMapping(path = "/adminHome")
	public String adminHome(Model model) throws JsonProcessingException {
		userService.updateStudentSemester(model);
		return "adminHome";
	}

	// method to get student home
	@GetMapping(path = "/studentHome")
	public String studentHome(ModelMap map, Model model, HttpSession session) throws JsonProcessingException {
		userService.updateStudentSemester(model);
		int UserId = (int) session.getAttribute("userId");
		userService.setUserSessionById(UserId, session);
		int value = userService.findStudentSemesterById(UserId, model);
		if (value > 0) {
			String department = (String) session.getAttribute("department");
			session.setAttribute("semester", value);
			map.addAttribute("semester", value);
			map.addAttribute("subjectList", staffService.findSubjectList(value, department, model));
			System.out.println();
		} else {
			model.addAttribute("semester", value);
		}
		return "Home";
	}

	// method to get studentRegistration form
	@GetMapping(path = "/studentRegistration")
	public String studentProfile(Model model) throws JsonProcessingException {
		model.addAttribute("departmentList", staffService.departmentList(model));
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
	public String adminResult(Model model) throws JsonProcessingException {
		model.addAttribute("approvedStudentList", staffService.approvedStudentList(model));
		return "resultAdmin";
	}

	// method to get result popup page
	@GetMapping(path = "/resultPopup/{userId}")
	public String resultPopup(@PathVariable(value = "userId") int userId, ModelMap map, Model model)
			throws JsonProcessingException {
		List<User> user = staffDao.findStudentById(userId, model);
		for (User userModel : user) {
			if (userModel != null) {
				map.addAttribute("userId", userId);
				map.addAttribute("userName", userModel.getFirstName());
				String department = userModel.getDepartment();
				int semester = userModel.getSemester();
				List<Subject> subject = staffDao.findSubjectNameByDepartmentSemester(department, semester);
				for (Subject subjectModel : subject) {
					if (subjectModel != null) {
						String name = subjectModel.getName();
						map.addAttribute("subjectName", subject);
						List<Subject> id = staffDao.findSubjectIdByName(name);
						for (Subject subjectModel2 :id) {
							if (subjectModel2 != null) {
								String SubjectID = subjectModel2.getId();
								map.addAttribute("Exam", staffDao.findExamNameBySubjectID(SubjectID));
								map.addAttribute("ExamType", staffDao.findExamTypeBySubjectID(SubjectID));
							}
						}
					}
				}
			}
		}
		return "resultPopup";
	}

	// method to get Subject Details
	@GetMapping(path = "/subjectDetails")
	public String subjectDetails() {
		return "redirect:/subjectlist";
	}

	// method to get department form
	@GetMapping(path = "/insertDepartmentForm")
	public String departmentForm(HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute("userId");
		staffService.checkHigherAuthority(staffId);
		return "departmentForm";
	}

	// method to get semester form
	@GetMapping(path = "/insertSemesterForm")
	public String semesterForm(HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute("userId");
		staffService.checkHigherAuthority(staffId);
		return "semesterForm";
	}

	// method to get subject form
	@GetMapping(path = "/insertSubjectForm")
	public String subjectForm(ModelMap map, Model model, HttpSession session)
			throws JsonProcessingException, HigherAuthorityException {
		int staffId = (int) session.getAttribute("userId");
		staffService.checkHigherAuthority(staffId);
		map.addAttribute("departmentList", staffService.departmentList(model));
		map.addAttribute("semesterList", staffService.semesterList(model));
		return "subjectForm";
	}

	// method to get exam form
	@GetMapping(path = "/insertExamForm")
	public String examForm(Model model) throws JsonProcessingException {
		model.addAttribute("subjectList", staffService.subjectList(model));
		return "examForm";
	}

	// ----------Exception methods---------

	// method to handle ExistDepartmentNameException
	@ExceptionHandler(value = ExistDepartmentNameException.class)
	public String ExistDepartmentNameException(ExistDepartmentNameException exception, Model model) {
		model.addAttribute("ErrorMessage", "Department Already Exist");
		return "errorpopup";
	}

	// method to handle ExistExamException
	@ExceptionHandler(value = ExistExamException.class)
	public String ExistExamException(ExistExamException exception, Model model) {
		model.addAttribute("ErrorMessage", "Exam Already Exist");
		return "errorpopup";
	}

	// method to handle ExistMailIdException
	@ExceptionHandler(value = ExistMailIdException.class)
	public String ExistMailIdException(ExistMailIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "Sorry! This Email Id Already Exist");
		return "errorpopup";
	}

	// method to handle InvalidMailIdException
	@ExceptionHandler(value = InvalidMailIdException.class)
	public String InvalidMailIdException(InvalidMailIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "Sorry! Invalid Email Id And Password");
		return "errorpopup";
	}

	// method to handle ExamIdException
	@ExceptionHandler(value = ExamIdException.class)
	public String ExamIdException(ExamIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "Exam Id dosen't Exist");
		return "errorpopup";
	}

	// method to handle MarkException
	@ExceptionHandler(value = MarkException.class)
	public String MarkException(MarkException exception, Model model) {
		model.addAttribute("ErrorMessage", "Invalid Marks ,Marks should be between 0 to 100");
		return "errorpopup";
	}

	// method to handle ExistSemesterIdException
	@ExceptionHandler(value = ExistSemesterIdException.class)
	public String ExistSemesterIdException(ExistSemesterIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "Semester Already Exist");
		return "errorpopup";
	}

	// method to handle SubjectIdException
	@ExceptionHandler(value = SubjectIdException.class)
	public String SubjectIdException(SubjectIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "Subject Id dosen't Exist");
		return "errorpopup";
	}

	// method to handle UserIdException
	@ExceptionHandler(value = UserIdException.class)
	public String UserIdException(UserIdException exception, Model model) {
		model.addAttribute("ErrorMessage", "User dosen't Exist");
		return "errorpopup";
	}

	// method to handle HigherAuthorityException
	@ExceptionHandler(value = HigherAuthorityException.class)
	public String HigherAuthorityException(HigherAuthorityException exception, Model model) {
		model.addAttribute("ErrorMessage", "opps sorry! only HigherAuthority can do this Process");
		return "errorpopup";
	}

	// method to handle DepartmentException
	@ExceptionHandler(value = DepartmentException.class)
	public String DepartmentException(DepartmentException exception, Model model) {
		model.addAttribute("ErrorMessage", "Department dosen't Exist");
		return "errorpopup";
	}

	// method to handle ExistSubjectNameException
	@ExceptionHandler(value = ExistSubjectNameException.class)
	public String ExistSubjectNameException(ExistSubjectNameException exception, Model model) {
		model.addAttribute("ErrorMessage", "Subject Already Exist");
		return "errorpopup";
	}
}
