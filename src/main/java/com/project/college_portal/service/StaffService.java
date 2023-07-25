package com.project.college_portal.service;

import java.util.List;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.dao.StaffDao;
import com.project.college_portal.dao.UserDao;
import com.project.college_portal.exception.DepartmentException;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistExamException;
import com.project.college_portal.exception.ExistSemesterIdException;
import com.project.college_portal.exception.ExistSubjectNameException;
import com.project.college_portal.exception.HigherAuthorityException;
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

public class StaffService {
	UserDao userDao = new UserDao();
	StaffDao staffDao = new StaffDao();

	// method to find Subject List By Semester
	public List<Subject> findSubjectListBySemester(int semesterId, Model model) throws JsonProcessingException {
		List<Subject> subjectList = staffDao.findSubjectListBySemester(semesterId, model);
		return subjectList;
	}

	// method to get student list
	public List<User> studentList(Model model) throws JsonProcessingException {
		List<User> users = staffDao.studentList(model);
		return users;
	}

	// method to approve student
	public int approve(int staffId, User user) throws UserIdException, HigherAuthorityException {
		int users = staffDao.approve(staffId, user);
		return users;
	}

	// method to get department list
	public List<Department> departmentList(Model model) throws JsonProcessingException {
		List<Department> department = staffDao.departmentList(model);
		return department;
	}

	// method to get inactiveDepartment list
	public List<Department> inactiveDepartmentList(Model model) throws JsonProcessingException {
		List<Department> department = staffDao.inactiveDepartmentList(model);
		return department;
	}

	// method to add department
	public int addDepartment(int staffId, Department depart)
			throws ExistDepartmentNameException, HigherAuthorityException {
		int department = staffDao.addDepartment(staffId, depart);
		return department;
	}

	// method to activate/Deactivate Department
	public int activateOrDeactivateDepartment(Department depart) {
		int department = staffDao.activateOrDeactivateDepartment(depart);
		return department;
	}

	// method to get approvedStudentList
	public List<User> approvedStudentList(Model model) throws JsonProcessingException {
		List<User> user = staffDao.approvedStudentList(model);
		return user;
	}

	// method to get attendance List
	public List<Attendance> attendanceList() {
		List<Attendance> attendance = staffDao.attendanceList();
		return attendance;
	}

	// method to get inactiveAttendance List
	public List<Attendance> inactiveAttendanceList() {
		List<Attendance> attendance = staffDao.inactiveAttendanceList();
		return attendance;
	}

	// method to add Or Update Present By One
	public int addOrUpdatePresentByOne(int userId) throws UserIdException {
		int userid = staffDao.addOrUpdatePresentByOne(userId);
		return userid;
	}

	// method to add Or Update Absent By One
	public int addOrUpdateAbsentByOne(int userId) throws UserIdException {
		int userid = staffDao.addOrUpdateAbsentByOne(userId);
		return userid;
	}

	// method to activate Or Deactivate Attendance
	public int activateOrDeactivateAttendance(Attendance attendance) {
		int attendance1 = staffDao.activateOrDeactivateAttendance(attendance);
		return attendance1;
	}

	// method to get active Or Inactive Semester
	public int activeOrInactiveSemester() {
		int semester = staffDao.activeOrInactiveSemester();
		return semester;
	}

	// method to get semester List
	public List<Semester> semesterList(Model model) throws JsonProcessingException {
		List<Semester> semester = staffDao.semesterList(model);
		return semester;
	}

	// method to get active Semester List
	public List<Semester> activeSemesterList(Model model) throws JsonProcessingException {
		List<Semester> semester = staffDao.activeSemesterList(model);
		return semester;
	}

	// method to get inactive Semester List
	public List<Semester> inactiveSemesterList(Model model) throws JsonProcessingException {
		List<Semester> semester = staffDao.inactiveSemesterList(model);
		return semester;
	}

	// method to add Semester
	public int addSemester(Semester semester) throws ExistSemesterIdException {
		int semester1 = staffDao.addSemester(semester);
		return semester1;
	}

	// method to activate Or Deactivate Semester
	public int activateOrDeactivateSemester(Semester semester) {
		int semester1 = staffDao.activateOrDeactivateSemester(semester);
		return semester1;
	}

	// method to get subject list
	public List<Subject> subjectList(Model model) throws JsonProcessingException {
		List<Subject> subject = staffDao.subjectList(model);
		return subject;
	}

	// method to get inactive subject list
	public List<Subject> inactivesubjectList(Model model) throws JsonProcessingException {
		List<Subject> subject = staffDao.inactivesubjectList(model);
		return subject;
	}

	// method to add subject
	public int addSubject(Subject subject) throws SemesterIdException, DepartmentException, ExistSubjectNameException {
		int subject1 = staffDao.addSubject(subject);
		return subject1;
	}

	// method to activate Or Deactivate Subject
	public int activateOrDeactivateSubject(Subject subject) {
		int subject1 = staffDao.activateOrDeactivateSubject(subject);
		return subject1;
	}

	// method to get exam list
	public List<Exam> examList(Model model) throws JsonProcessingException {
		List<Exam> exam = staffDao.examList(model);
		return exam;
	}

	// method to get inactive exam list
	public List<Exam> inactiveExamList(Model model) throws JsonProcessingException {
		List<Exam> exam = staffDao.inactiveExamList(model);
		return exam;
	}

	// method to add exam
	public int addExam(Exam exam) throws SubjectIdException, ExistExamException {
		int exam1 = staffDao.addExam(exam);
		return exam1;
	}

	// method to add exam
	public int activateOrDeactivateExam(Exam exam) {
		int exam1 = staffDao.activateOrDeactivateExam(exam);
		return exam1;
	}

	// method to get result List
	public List<Result> resultList(Model model) throws JsonProcessingException {
		List<Result> exam = staffDao.resultList(model);
		return exam;
	}

	// method to get inactive Result List
	public List<Result> inactiveResultList(Model model) throws JsonProcessingException {
		List<Result> exam = staffDao.inactiveResultList(model);
		return exam;
	}

	// method to Add Or Update Result
	public int addOrUpdateResult(Result result) throws MarkException, UserIdException, ExamIdException {
		int result1 = staffDao.addOrUpdateResult(result);
		return result1;
	}

	// method to Activate Or Deactivate one Result of particular exam and user
	public int activateOrDeactivateOneResult(Result result) {
		int result1 = staffDao.activateOrDeactivateOneResult(result);
		return result1;
	}

	// method to Activate Or Deactivate Result of one whole exam
	public int activateOrDeactivateWholeExamResult(Result result) {
		int result1 = staffDao.activateOrDeactivateWholeExamResult(result);
		return result1;
	}

	// method to Activate Or Deactivate one Result of one whole user
	public int activateOrDeactivateWholeUserResult(Result result) {
		int result1 = staffDao.activateOrDeactivateWholeUserResult(result);
		return result1;
	}

	// method to find Subject List
	public List<Subject> findSubjectList(int value, String department, Model model) throws JsonProcessingException {
		List<Subject> subject = staffDao.findSubjectList(value, department, model);
		return subject;
	}

	// method to check Higher Authority
	public int checkHigherAuthority(int staffId) throws HigherAuthorityException {
		int value = staffDao.checkHigherAuthority(staffId);
		return value;
	}
}