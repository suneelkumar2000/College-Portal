package com.project.college_portal.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

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
		return  staffDao.findSubjectListBySemester(semesterId, model);
		
	}

	// method to get student list
	public List<User> studentList(Model model) throws JsonProcessingException {
		return  staffDao.studentList(model);
	}

	// method to approve student
	public int approve(int staffId, User user) throws UserIdException, HigherAuthorityException {
		return staffDao.approve(staffId, user);
	}

	// method to get department list
	public List<Department> departmentList(Model model) throws JsonProcessingException {
		return staffDao.departmentList(model);
	}

	// method to get inactiveDepartment list
	public List<Department> inactiveDepartmentList(Model model) throws JsonProcessingException {
		return staffDao.inactiveDepartmentList(model);
	}

	// method to add department
	public int addDepartment(int staffId, Department depart)
			throws ExistDepartmentNameException, HigherAuthorityException {
		return staffDao.addDepartment(staffId, depart);
	}

	// method to activate/Deactivate Department
	public int activateOrDeactivateDepartment(Department depart) {
		return staffDao.activateOrDeactivateDepartment(depart);
	}

	// method to get approvedStudentList
	public List<User> approvedStudentList(Model model) throws JsonProcessingException {
		return staffDao.approvedStudentList(model);
	}

	// method to get attendance List
	public List<Attendance> attendanceList() {
		return staffDao.attendanceList();
	}

	// method to get inactiveAttendance List
	public List<Attendance> inactiveAttendanceList() {
		return staffDao.inactiveAttendanceList();
	}

	// method to add Or Update Present By One
	public int addOrUpdatePresentByOne(int userId) throws UserIdException {
		return staffDao.addOrUpdatePresentByOne(userId);
	}

	// method to add Or Update Absent By One
	public int addOrUpdateAbsentByOne(int userId) throws UserIdException {
		return staffDao.addOrUpdateAbsentByOne(userId);
	}

	// method to activate Or Deactivate Attendance
	public int activateOrDeactivateAttendance(Attendance attendance) {
		return staffDao.activateOrDeactivateAttendance(attendance);
	}

	// method to get active Or Inactive Semester
	public int activeOrInactiveSemester() {
		return staffDao.activeOrInactiveSemester();
	}

	// method to get semester List
	public List<Semester> semesterList(Model model) throws JsonProcessingException {
		return staffDao.semesterList(model);
	}

	// method to get active Semester List
	public List<Semester> activeSemesterList(Model model) throws JsonProcessingException {
		return staffDao.activeSemesterList(model);
	}

	// method to get inactive Semester List
	public List<Semester> inactiveSemesterList(Model model) throws JsonProcessingException {
		return staffDao.inactiveSemesterList(model);
	}

	// method to add Semester
	public int addSemester(Semester semester) throws ExistSemesterIdException {
		return staffDao.addSemester(semester);
	}

	// method to activate Or Deactivate Semester
	public int activateOrDeactivateSemester(Semester semester) {
		return staffDao.activateOrDeactivateSemester(semester);
	}

	// method to get subject list
	public List<Subject> subjectList(Model model) throws JsonProcessingException {
		return staffDao.subjectList(model);
	}

	// method to get inactive subject list
	public List<Subject> inactivesubjectList(Model model) throws JsonProcessingException {
		return staffDao.inactivesubjectList(model);
	}

	// method to add subject
	public int addSubject(Subject subject) throws SemesterIdException, DepartmentException, ExistSubjectNameException {
		return staffDao.addSubject(subject);
	}

	// method to activate Or Deactivate Subject
	public int activateOrDeactivateSubject(Subject subject) {
		return staffDao.activateOrDeactivateSubject(subject);
	}

	// method to get exam list
	public List<Exam> examList(Model model) throws JsonProcessingException {
		return staffDao.examList(model);
	}

	// method to get inactive exam list
	public List<Exam> inactiveExamList(Model model) throws JsonProcessingException {
		return staffDao.inactiveExamList(model);
	}

	// method to add exam
	public int addExam(Exam exam) throws SubjectIdException, ExistExamException {
		return staffDao.addExam(exam);
	}

	// method to add exam
	public int activateOrDeactivateExam(Exam exam) {
		return staffDao.activateOrDeactivateExam(exam);
	}

	// method to get result List
	public List<Result> resultList(Model model) throws JsonProcessingException {
		return staffDao.resultList(model);
	}

	// method to get inactive Result List
	public List<Result> inactiveResultList(Model model) throws JsonProcessingException {
		return staffDao.inactiveResultList(model);
	}

	// method to Add Or Update Result
	public int addOrUpdateResult(Result result) throws MarkException, UserIdException, ExamIdException {
		return staffDao.addOrUpdateResult(result);
	}

	// method to Activate Or Deactivate one Result of particular exam and user
	public int activateOrDeactivateOneResult(Result result) {
		return staffDao.activateOrDeactivateOneResult(result);
	}

	// method to Activate Or Deactivate Result of one whole exam
	public int activateOrDeactivateWholeExamResult(Result result) {
		return staffDao.activateOrDeactivateWholeExamResult(result);
	}

	// method to Activate Or Deactivate one Result of one whole user
	public int activateOrDeactivateWholeUserResult(Result result) {
		return staffDao.activateOrDeactivateWholeUserResult(result);
	}

	// method to find Subject List
	public List<Subject> findSubjectList(int value, String department, Model model) throws JsonProcessingException {
		return staffDao.findSubjectList(value, department, model);
	}

	// method to check Higher Authority
	public int checkHigherAuthority(int staffId) throws HigherAuthorityException {
		return staffDao.checkHigherAuthority(staffId);
	}

	// method to get result popup page
	public void resultPopup(int userId, ModelMap map, Model model) throws JsonProcessingException {
		staffDao.resultPopup(userId, map, model);
	}
}