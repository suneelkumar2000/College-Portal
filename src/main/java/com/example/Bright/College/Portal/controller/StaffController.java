package com.example.Bright.College.Portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bright.College.Portal.dao.StaffDao;
import com.example.Bright.College.Portal.model.Attendance;
import com.example.Bright.College.Portal.model.Department;
import com.example.Bright.College.Portal.model.Exam;
import com.example.Bright.College.Portal.model.Result;
import com.example.Bright.College.Portal.model.Semester;
import com.example.Bright.College.Portal.model.Subject;
import com.example.Bright.College.Portal.model.User;

@Controller
public class StaffController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	StaffDao staffDao;

	// method to get student list
	@GetMapping(path = "/listofusers")
	public String getAllUser(Model model) {
		System.out.println("getting datas");
		List<User> users = staffDao.studentList();
		model.addAttribute("USER_LIST", users);
		return "listusers";
	}

	// method to approve student
	@GetMapping(path = "/approve")
	public void approve(@RequestParam("userID") int userID) {
		User user = new User();
		user.setUserId(userID);
		staffDao.approve(user);
	}

	// --------- Department methods ------------

	// method to get department list
	@GetMapping(path = "/departmentlist")
	public String departmentList(Model model) {
		model.addAttribute("departmentList", staffDao.departmentList());
		return "department";
	}

	// method to get inactiveDepartment list
	@GetMapping(path = "/inactive-departmentlist")
	public String inactiveDepartmentList(Model model) {
		model.addAttribute("departmentList", staffDao.inactiveDepartmentList());
		return "department";
	}

	// method to add department
	@GetMapping(path = "/insertdepartment")
	public String addDepartment(@RequestParam("department") String department, Model model) {
		Department depart = new Department();
		depart.setDepartment(department);
		int value = staffDao.addDepartment(depart);
		if (value == 1) {
			model.addAttribute("departmentList", staffDao.departmentList());
			return "department";
		} else
			return "departmentForm";
	}

	// method to activate/Deactivate Department
	@GetMapping(path = "/activate-deactivate-department/{name}")
	public String activateOrDeactivateDepartment(@PathVariable(value = "name") String name, Model model) {
		Department department = new Department();
		department.setDepartment(name);
		int value = staffDao.activateOrDeactivateDepartment(department);
		if (value == 1) {
			model.addAttribute("departmentList", staffDao.departmentList());
			return "department";
		} else if (value == 2) {
			model.addAttribute("departmentList", staffDao.inactiveDepartmentList());
			return "department";
		} else
			return "department";
	}

	// --------- Attendance methods ------------

	// method to get attendance List
	@GetMapping(path = "/attendancelist")
	public String attendanceList(Model model) {
		model.addAttribute("attendanceList", staffDao.attendanceList());
		return "attendancAdmin";
	}

	// method to get inactiveAttendance List
	@GetMapping(path = "/inactive-attendancelist")
	public String inactiveAttendanceList(Model model) {
		model.addAttribute("attendanceList", staffDao.inactiveAttendanceList());
		return "attendancAdmin";
	}

	// method to add present
	@GetMapping(path = "/add-update-present-by-one")
	public String addOrUpdatePresentByOne(@RequestParam("present") int present, Model model) {
		Attendance attendance = new Attendance();
		attendance.setDaysAttended(present);
		int value = staffDao.addOrUpdatePresentByOne(attendance);
		if (value == 1) {
			model.addAttribute("attendanceList", staffDao.attendanceList());
			return "attendancAdmin";
		} else
			return "attendancAdmin";
	}

	// method to add absent
	@GetMapping(path = "/add-update-absent-by-one")
	public String addOrUpdateAbsentByOne(@RequestParam("absent") int absent, Model model) {
		Attendance attendance = new Attendance();
		attendance.setDaysLeave(absent);
		int value = staffDao.addOrUpdateAbsentByOne(attendance);
		if (value == 1) {
			model.addAttribute("attendanceList", staffDao.attendanceList());
			return "attendancAdmin";
		} else
			return "attendancAdmin";
	}

	// method to activate Or Deactivate Attendance
	@GetMapping(path = "/activate-deactivate-attendance/{userId}")
	public String activateOrDeactivateAttendance(@PathVariable(value = "userId") int userId, Model model) {
		Attendance attendance = new Attendance();
		attendance.setUserId(userId);
		int value = staffDao.activateOrDeactivateAttendance(attendance);
		if (value == 1) {
			model.addAttribute("attendanceList", staffDao.attendanceList());
			return "attendancAdmin";
		} else
			return "attendancAdmin";
	}

	// --------- Semester methods ------------

	// method to get Semester List
	@GetMapping(path = "/semesterlist")
	public String semesterList(Model model) {
		model.addAttribute("semesterList", staffDao.semesterList());
		return "semester";
	}

	// method to get inactive Semester List
	@GetMapping(path = "/inactive-semesterlist")
	public String inactiveSemesterList(Model model) {
		model.addAttribute("semesterList", staffDao.inactiveSemesterList());
		return "semester";
	}

	// method to add Semester
	@GetMapping(path = "/addsemester")
	public String addSemester(@RequestParam("semesterId") int semesterId, Model model) {
		Semester semester = new Semester();
		semester.setId(semesterId);
		int value = staffDao.addSemester(semester);
		if (value == 1) {
			model.addAttribute("semesterList", staffDao.semesterList());
			return "semester";
		} else
			return "semester";
	}

	// method to activate Or Deactivate Semester
	@GetMapping(path = "/activate-deactivate-semester/{semesterId}")
	public String activateOrDeactivateSemester(@PathVariable(value = "semesterId") int semesterId, Model model) {
		Semester semester = new Semester();
		semester.setId(semesterId);
		int value = staffDao.activateOrDeactivateSemester(semester);
		if (value == 1) {
			model.addAttribute("semesterList", staffDao.semesterList());
			return "semester";
		} else
			return "semester";
	}

	// --------- Subject methods ------------

	// method to get subject list
	@GetMapping(path = "/subjectlist")
	public String subjectList(Model model) {
		model.addAttribute("subjectList", staffDao.subjectList());
		return "subjectDetails";
	}

	// method to get inactive subject list
	@GetMapping(path = "/inactive-subjectlist")
	public String inactivesubjectList(Model model) {
		model.addAttribute("subjectList", staffDao.inactivesubjectList());
		return "subjectDetails";
	}

	// method to add subject
	@GetMapping(path = "/addsubject")
	public String addSubject(@RequestParam("subjectId") int subjectId, @RequestParam("name") String name,
			@RequestParam("semesterId") int semesterId, @RequestParam("department") String department, Model model) {
		Subject subject = new Subject();
		subject.setId(subjectId);
		subject.setName(name);
		subject.setSemesterId(semesterId);
		subject.setDepartment(department);
		int value = staffDao.addSubject(subject);
		if (value == 1) {
			model.addAttribute("subjectList", staffDao.subjectList());
			return "subjectDetails";
		} else
			return "subjectDetails";
	}

	// method to find Subject By ID

	// method to activate/Deactivate Subject
	@GetMapping(path = "/activate-deactivate-subject/{subjectId}")
	public String activateOrDeactivateSubject(@PathVariable(value = "subjectId") int subjectId, Model model) {
		Subject subject = new Subject();
		subject.setId(subjectId);
		int value = staffDao.activateOrDeactivateSubject(subject);
		if (value == 1) {
			model.addAttribute("subjectList", staffDao.subjectList());
			return "subjectDetails";
		} else if (value == 2) {
			model.addAttribute("subjectList", staffDao.inactivesubjectList());
			return "subjectDetails";
		} else
			return "subjectDetails";
	}

	// --------- Exam methods ------------

	// method to get exam list
	@GetMapping(path = "/examlist")
	public String examList(Model model) {
		model.addAttribute("examList", staffDao.examList());
		return "examDetails";
	}

	// method to get inactive exam list
	@GetMapping(path = "/inactive-examlist")
	public String inactiveExamList(Model model) {
		model.addAttribute("inactiveExamList", staffDao.inactiveExamList());
		return "examDetails";
	}

	// method to add exam
	@GetMapping(path = "/addexam")
	public String addExam(@RequestParam("examId") int examId, @RequestParam("name") String name,
			@RequestParam("subjectId") int subjectId, @RequestParam("type") String type, Model model) {
		Exam exam = new Exam();
		exam.setId(examId);
		exam.setName(name);
		exam.setSubjectId(subjectId);
		exam.setType(type);
		int value = staffDao.addExam(exam);
		if (value == 1) {
			model.addAttribute("examList", staffDao.examList());
			return "examDetails";
		} else
			return "examDetails";
	}

	// method to activate Or Deactivate Exam
	@GetMapping(path = "/activate-deactivate-exam/{examId}")
	public String activateOrDeactivateExam(@PathVariable(value = "examId") int examId, Model model) {
		Exam exam = new Exam();
		exam.setId(examId);
		int value = staffDao.activateOrDeactivateExam(exam);
		if (value == 1) {
			model.addAttribute("examList", staffDao.examList());
			return "examDetails";
		} else if (value == 2) {
			model.addAttribute("examList", staffDao.inactiveExamList());
			return "examDetails";
		} else
			return "examDetails";
	}

	// --------- Result methods ------------

	// method to get result List
	@GetMapping(path = "/resultlist")
	public String resultList(Model model) {
		model.addAttribute("resultList", staffDao.resultList());
		return "resultAdmin";
	}

	// method to get inactive Result List
	@GetMapping(path = "/inactive-resultlist")
	public String inactiveResultList(Model model) {
		model.addAttribute("inactiveResultList", staffDao.inactiveResultList());
		return "resultAdmin";
	}

	// method to Add Or Update Result
	@GetMapping(path = "/add-update-result")
	public String addOrUpdateResult(@RequestParam("examId") int examId, @RequestParam("userId") int userId,
			@RequestParam("marks") int marks, Model model) {
		Result result = new Result();
		result.setExamId(examId);
		result.setUserId(userId);
		result.setMarks(marks);
		int value = staffDao.addOrUpdateResult(result);
		if (value == 1) {
			model.addAttribute("resultList", staffDao.resultList());
			return "resultAdmin";
		} else
			return "resultAdmin";
	}

	// method to Activate Or Deactivate one Result of particular exam and user
	@GetMapping(path = "/activate-deactivate-one-result/{examId}/{userId}")
	public String activateOrDeactivateOneResult(@PathVariable(value = "examId") int examId,
			@PathVariable(value = "userId") int userId, Model model) {
		Result result = new Result();
		result.setExamId(examId);
		result.setUserId(userId);
		int value = staffDao.activateOrDeactivateOneResult(result);
		if (value == 1) {
			model.addAttribute("resultList", staffDao.resultList());
			return "resultAdmin";
		} else if (value == 2) {
			model.addAttribute("resultList", staffDao.inactiveResultList());
			return "resultAdmin";
		} else
			return "resultAdmin";
	}

	// method to Activate Or Deactivate Result of one whole exam
	@GetMapping(path = "/activate-deactivate-whole-examresult/{examId}")
	public String activateOrDeactivateWholeExamResult(@PathVariable(value = "examId") int examId, Model model) {
		Result result = new Result();
		result.setExamId(examId);
		int value = staffDao.activateOrDeactivateWholeExamResult(result);
		if (value == 1) {
			model.addAttribute("resultList", staffDao.resultList());
			return "resultAdmin";
		} else if (value == 2) {
			model.addAttribute("resultList", staffDao.inactiveResultList());
			return "resultAdmin";
		} else
			return "resultAdmin";
	}

	// method to Activate Or Deactivate one Result of one whole user
	@GetMapping(path = "/activate-deactivate-whole-userresult/{userId}")
	public String activateOrDeactivateWholeUserResult(@PathVariable(value = "userId") int userId, Model model) {
		Result result = new Result();
		result.setUserId(userId);
		int value = staffDao.activateOrDeactivateWholeUserResult(result);
		if (value == 1) {
			model.addAttribute("resultList", staffDao.resultList());
			return "resultAdmin";
		} else if (value == 2) {
			model.addAttribute("resultList", staffDao.inactiveResultList());
			return "resultAdmin";
		} else
			return "resultAdmin";
	}
}
