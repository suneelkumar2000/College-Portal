package com.project.college_portal.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.exception.DepartmentException;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistExamException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.ExistSemesterIdException;
import com.project.college_portal.exception.ExistSubjectNameException;
import com.project.college_portal.exception.HigherAuthorityException;
import com.project.college_portal.exception.InvalidMailIdException;
import com.project.college_portal.exception.MarkException;
import com.project.college_portal.exception.SemesterIdException;
import com.project.college_portal.exception.SubjectIdException;
import com.project.college_portal.exception.UserIdException;
import com.project.college_portal.model.Attendance;
import com.project.college_portal.model.Department;
import com.project.college_portal.model.Exam;
import com.project.college_portal.model.Result;
import com.project.college_portal.model.Semester;
import com.project.college_portal.model.Subject;
import com.project.college_portal.model.User;
import com.project.college_portal.service.StaffService;

@Controller
public class StaffController {

	StaffDao staffDao = new StaffDao();
	StaffService staffService = new StaffService();
	
	String sessionUserId = "userId";
	String ErrorMessage = "ErrorMessage";
	String errorpopup = "errorpopup";
	String modeldepartmentList="departmentList";
	String modelattendanceList="attendanceList";
	String modelsemesterList="semesterList";
	String modelsubjectList="subjectList";
	String returnDepartment="department";
	String returnAttendanceAdmin="attendanceAdmin";
	String returnSemester="semester";
	String returnSubjectDetails="subjectDetails";
	String returnExamDetails="examDetails";
	String returnResultAdmin="resultAdmin";

	// method to get student list
	@GetMapping(path = "/listofusers")
	public String getAllUser(Model model) throws JsonProcessingException {
		List<User> users = staffService.studentList(model);
		model.addAttribute("USER_LIST", users);
		return "listusers";
	}

	// method to approve student
	@GetMapping(path = "/approve")
	public void approve(@RequestParam("userID") int userID, HttpSession session)
			throws UserIdException, HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		User user = new User();
		user.setUserId(userID);
		staffService.approve(staffId, user);
	}

	// --------- Department methods ------------

	// method to get department list
	@GetMapping(path = "/departmentlist")
	public String departmentList(Model model) throws JsonProcessingException {
		model.addAttribute(modeldepartmentList, staffService.departmentList(model));
		return returnDepartment;
	}

	// method to get inactiveDepartment list
	@GetMapping(path = "/inactiveDepartmentlist")
	public String inactiveDepartmentList(Model model) throws JsonProcessingException {
		model.addAttribute(modeldepartmentList, staffService.inactiveDepartmentList(model));
		return returnDepartment;
	}

	// method to add department
	@GetMapping(path = "/insertdepartment")
	public String addDepartment(@RequestParam("department") String department, Model model, HttpSession session)
			throws ExistDepartmentNameException, HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		Department depart = new Department();
		depart.setDepartment(department);
		int value = staffService.addDepartment(staffId, depart);
		if (value == 1) {
			return "redirect:/departmentlist";
		} else
			return "departmentForm";
	}

	// method to activate/Deactivate Department
	@GetMapping(path = "/activateDeactivateDepartment")
	public String activateOrDeactivateDepartment(@RequestParam("name") String name, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Department department = new Department();
		department.setDepartment(name);
		int value = staffService.activateOrDeactivateDepartment(department);
		if (value == 1) {
			return "redirect:/departmentlist";
		} else if (value == 2) {
			return "redirect:/inactiveDepartmentlist";
		} else
			return returnDepartment;
	}

	// --------- Attendance methods ------------

	// method to get attendance List
	@GetMapping(path = "/attendancelist")
	public String attendanceList(Model model) {
		model.addAttribute(modelattendanceList, staffService.attendanceList());
		return returnAttendanceAdmin;
	}

	// method to get inactiveAttendance List
	@GetMapping(path = "/inactiveAttendancelist")
	public String inactiveAttendanceList(Model model) {
		model.addAttribute(modelattendanceList, staffService.inactiveAttendanceList());
		return returnAttendanceAdmin;
	}

	// method to add present
	@GetMapping(path = "/addUpdatePresentbyone")
	public String addOrUpdatePresentByOne(@RequestParam("userId") int userId, Model model)
			throws UserIdException, JsonProcessingException {
		int value = staffService.addOrUpdatePresentByOne(userId);
		if (value == 1) {
			return "redirect:/attendanceAdmin";
		} else
			return returnAttendanceAdmin;
	}

	// method to add absent
	@GetMapping(path = "/addUpdateAbsentbyone")
	public String addOrUpdateAbsentByOne(@RequestParam("userId") int userId, Model model)
			throws UserIdException, JsonProcessingException {

		int value = staffService.addOrUpdateAbsentByOne(userId);
		if (value == 1) {
			return "redirect:/attendanceAdmin";
		} else
			return returnAttendanceAdmin;
	}

	// method to activate Or Deactivate Attendance
	@GetMapping(path = "/activateDeactivateAttendance/{userId}")
	public String activateOrDeactivateAttendance(@PathVariable(value = "userId") int userId, Model model) {
		Attendance attendance = new Attendance();
		attendance.setUserId(userId);
		int value = staffService.activateOrDeactivateAttendance(attendance);
		if (value == 1) {
			return "redirect:/attendancelist";
		} else
			return returnAttendanceAdmin;
	}

	// --------- Semester methods ------------

	// method to get Semester List
	@GetMapping(path = "/semesterlist")
	public String semesterList(Model model) throws JsonProcessingException {
		staffService.activeOrInactiveSemester();
		model.addAttribute(modelsemesterList, staffService.semesterList(model));
		return returnSemester;
	}

	// method to get active Semester List
	@GetMapping(path = "/activeSemesterlist")
	public String activeSemesterList(Model model) throws JsonProcessingException {
		staffService.activeOrInactiveSemester();
		model.addAttribute(modelsemesterList, staffService.activeSemesterList(model));
		return returnSemester;
	}

	// method to get inactive Semester List
	@GetMapping(path = "/inactiveSemesterlist")
	public String inactiveSemesterList(Model model) throws JsonProcessingException {
		staffService.activeOrInactiveSemester();
		model.addAttribute(modelsemesterList, staffService.inactiveSemesterList(model));
		return returnSemester;
	}

	// method to add Semester
	@GetMapping(path = "/addsemester")
	public String addSemester(@RequestParam("semesterId") int semesterId, Model model) throws ExistSemesterIdException {
		Semester semester = new Semester();
		semester.setId(semesterId);
		int value = staffService.addSemester(semester);

		if (value == 1) {
			return "redirect:/semesterlist";
		} else
			return returnSemester;
	}

	// method to activate Or Deactivate Semester
	@GetMapping(path = "/activateDeactivateSemester/{semesterId}")
	public String activateOrDeactivateSemester(@PathVariable(value = "semesterId") int semesterId, Model model) {
		Semester semester = new Semester();
		semester.setId(semesterId);
		int value = staffService.activateOrDeactivateSemester(semester);
		if (value == 1) {
			return "redirect:/semesterlist";
		} else if (value == 2) {
			return "redirect:/inactiveSemesterlist";
		} else
			return returnSemester;
	}

	// --------- Subject methods ------------

	// method to get subject list
	@GetMapping(path = "/subjectlist")
	public String subjectList(Model model) throws JsonProcessingException {
		model.addAttribute(modelsubjectList, staffService.subjectList(model));
		return returnSubjectDetails;
	}

	// method to get inactive subject list
	@GetMapping(path = "/inactiveSubjectlist")
	public String inactivesubjectList(Model model) throws JsonProcessingException {
		model.addAttribute(modelsubjectList, staffService.inactivesubjectList(model));
		return returnSubjectDetails;
	}

	// method to add subject
	@GetMapping(path = "/addsubject")
	public String addSubject(@RequestParam("name") String name, @RequestParam("semesterId") int semesterId,
			@RequestParam("department") String department, Model model)
			throws SemesterIdException, DepartmentException, ExistSubjectNameException {
		Subject subject = new Subject();
		subject.setName(name);
		subject.setSemesterId(semesterId);
		subject.setDepartment(department);
		int value = staffService.addSubject(subject);
		if (value == 1) {
			return "redirect:/subjectlist";
		} else
			return returnSubjectDetails;
	}

	// method to find Subject By ID

	// method to activate/Deactivate Subject
	@GetMapping(path = "/activateDeactivateSubject")
	public String activateOrDeactivateSubject(@RequestParam("subjectId") String subjectId, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Subject subject = new Subject();
		subject.setId(subjectId);
		int value = staffService.activateOrDeactivateSubject(subject);
		if (value == 1) {
			return "redirect:/subjectlist";
		} else if (value == 2) {
			return "redirect:/inactiveSubjectlist";
		} else
			return returnSubjectDetails;
	}

	// --------- Exam methods ------------

	// method to get exam list
	@GetMapping(path = "/examlist")
	public String examList(Model model) throws JsonProcessingException {
		model.addAttribute("examList", staffService.examList(model));
		return returnExamDetails;
	}

	// method to get inactive exam list
	@GetMapping(path = "/inactiveExamlist")
	public String inactiveExamList(Model model) throws JsonProcessingException {
		model.addAttribute("inactiveExamList", staffService.inactiveExamList(model));
		return returnExamDetails;
	}

	// method to add exam
	@GetMapping(path = "/addexam")
	public String addExam(@RequestParam("name") String name, @RequestParam("date") Date date,
			@RequestParam("subjectId") String subjectId, @RequestParam("type") String type, Model model)
			throws SubjectIdException, ExistExamException {
		Exam exam = new Exam();
		exam.setName(name);
		exam.setSubjectId(subjectId);
		exam.setDate(date);
		exam.setType(type);
		int value = staffService.addExam(exam);
		if (value == 1) {
			return "redirect:/examlist";
		} else
			return returnExamDetails;
	}

	// method to activate Or Deactivate Exam
	@GetMapping(path = "/activateDeactivateExam")
	public String activateOrDeactivateExam(@RequestParam("examId") int examId, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Exam exam = new Exam();
		exam.setId(examId);
		int value = staffService.activateOrDeactivateExam(exam);
		if (value == 1) {
			return "redirect:/examlist";
		} else if (value == 2) {
			return "redirect:/inactiveExamlist";
		} else
			return returnExamDetails;
	}

	// --------- Result methods ------------

	// method to get result List
	@GetMapping(path = "/resultlist")
	public String resultList(Model model) throws JsonProcessingException {
		model.addAttribute("resultList", staffService.resultList(model));
		return returnResultAdmin;
	}

	// method to get inactive Result List
	@GetMapping(path = "/inactiveResultlist")
	public String inactiveResultList(Model model) throws JsonProcessingException {
		model.addAttribute("inactiveResultList", staffService.inactiveResultList(model));
		return returnResultAdmin;
	}

	// method to Add Or Update Result
	@GetMapping(path = "/addUpdateResult")
	public String addOrUpdateResult(@RequestParam("subject") String subjectName, @RequestParam("exam") String examName,
			@RequestParam("examType") String examType, @RequestParam("userId") int userId,
			@RequestParam("marks") int marks, Model model) throws MarkException, UserIdException, ExamIdException {
		Result result = new Result();
		result.setUserId(userId);
		System.out.println(userId);
		result.setMarks(marks);
		System.out.println(marks);

		List<User> user = staffDao.findStudentById(userId, model);
		for (User userModel : user) {
			if (userModel != null) {
				String department = userModel.getDepartment();
				System.out.println(department);
				int semester = userModel.getSemester();
				System.out.println(semester);
				List<Subject> subject = staffDao.findSubjectID(department, semester, subjectName);
				for (Subject subjectModel : subject) {
					if (subjectModel != null) {
						String subjectId = subjectModel.getId();
						System.out.println(subjectId);
						List<Exam> exam = staffDao.findExam(examName, examType, subjectId);
						System.out.println(exam);
						for (Exam examModel : exam) {
							if (examModel != null) {
								int examId = examModel.getId();
								result.setExamId(examId);
								System.out.println(examId);
								int value = staffService.addOrUpdateResult(result);
								if (value == 1) {
									return "redirect:/resultAdmin";
								}
							}
						}
					}
				}
			}
		}
		return "redirect:/resultAdmin";
	}

	// method to Activate Or Deactivate one Result of particular exam and user
	@GetMapping(path = "/activateDeactivateOneResult/{examId}/{userId}")
	public String activateOrDeactivateOneResult(@RequestParam("examId") int examId,
			@RequestParam("userId") int userId, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Result result = new Result();
		result.setExamId(examId);
		result.setUserId(userId);
		int value = staffService.activateOrDeactivateOneResult(result);
		if (value == 1) {
			return "redirect:/resultlist";
		} else if (value == 2) {
			return "redirect:/inactiveResultlist";
		} else
			return returnResultAdmin;
	}

	// method to Activate Or Deactivate Result of one whole exam
	@GetMapping(path = "/activateDeactivateWholeExamresult/{examId}")
	public String activateOrDeactivateWholeExamResult(@RequestParam("examId") int examId, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Result result = new Result();
		result.setExamId(examId);
		int value = staffService.activateOrDeactivateWholeExamResult(result);
		if (value == 1) {
			return "redirect:/resultlist";
		} else if (value == 2) {
			return "redirect:/inactiveResultlist";
		} else
			return returnResultAdmin;
	}

	// method to Activate Or Deactivate one Result of one whole user
	@GetMapping(path = "/activateDeactivateWholeUserresult/{userId}")
	public String activateOrDeactivateWholeUserResult(@RequestParam("userId") int userId, Model model,HttpSession session) throws HigherAuthorityException {
		int staffId = (int) session.getAttribute(sessionUserId);
		staffService.checkHigherAuthority(staffId);
		Result result = new Result();
		result.setUserId(userId);
		int value = staffService.activateOrDeactivateWholeUserResult(result);
		if (value == 1) {
			return "redirect:/resultlist";
		} else if (value == 2) {
			return "redirect:/inactiveResultlist";
		} else
			return returnResultAdmin;
	}

	// ----------Exception methods---------

	// method to handle ExistDepartmentNameException
	@ExceptionHandler(value = ExistDepartmentNameException.class)
	public String existDepartmentNameException(ExistDepartmentNameException exception, Model model) {
		model.addAttribute(ErrorMessage, "Department Already Exist");
		return errorpopup;
	}

	// method to handle ExistExamException
	@ExceptionHandler(value = ExistExamException.class)
	public String existExamException(ExistExamException exception, Model model) {
		model.addAttribute(ErrorMessage, "Exam Already Exist");
		return errorpopup;
	}

	// method to handle ExistMailIdException
	@ExceptionHandler(value = ExistMailIdException.class)
	public String existMailIdException(ExistMailIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Sorry! This Email Id Already Exist");
		return errorpopup;
	}

	// method to handle InvalidMailIdException
	@ExceptionHandler(value = InvalidMailIdException.class)
	public String invalidMailIdException(InvalidMailIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Sorry! Invalid Email Id And Password");
		return errorpopup;
	}

	// method to handle ExamIdException
	@ExceptionHandler(value = ExamIdException.class)
	public String examIdException(ExamIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Exam Id dosen't Exist");
		return errorpopup;
	}

	// method to handle MarkException
	@ExceptionHandler(value = MarkException.class)
	public String markException(MarkException exception, Model model) {
		model.addAttribute(ErrorMessage, "Invalid Marks ,Marks should be between 0 to 100");
		return errorpopup;
	}

	// method to handle ExistSemesterIdException
	@ExceptionHandler(value = ExistSemesterIdException.class)
	public String existSemesterIdException(ExistSemesterIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Semester Already Exist");
		return errorpopup;
	}

	// method to handle SubjectIdException
	@ExceptionHandler(value = SubjectIdException.class)
	public String subjectIdException(SubjectIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "Subject Id dosen't Exist");
		return errorpopup;
	}

	// method to handle UserIdException
	@ExceptionHandler(value = UserIdException.class)
	public String userIdException(UserIdException exception, Model model) {
		model.addAttribute(ErrorMessage, "User dosen't Exist");
		return errorpopup;
	}

	// method to handle HigherAuthorityException
	@ExceptionHandler(value = HigherAuthorityException.class)
	public String higherAuthorityException(HigherAuthorityException exception, Model model) {
		model.addAttribute(ErrorMessage, "opps sorry! only HigherAuthority can do this Process");
		return errorpopup;
	}

	// method to handle DepartmentException
	@ExceptionHandler(value = DepartmentException.class)
	public String departmentException(DepartmentException exception, Model model) {
		model.addAttribute(ErrorMessage, "Department dosen't Exist");
		return errorpopup;
	}

	// method to handle ExistSubjectNameException
	@ExceptionHandler(value = ExistSubjectNameException.class)
	public String existSubjectNameException(ExistSubjectNameException exception, Model model) {
		model.addAttribute(ErrorMessage, "Subject Already Exist");
		return errorpopup;
	}
}
