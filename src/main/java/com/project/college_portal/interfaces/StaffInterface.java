package com.project.college_portal.interfaces;

import java.util.List;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistExamException;
import com.project.college_portal.exception.ExistSemesterIdException;
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

public interface StaffInterface {
	public User findDepartmentById(int UserId);
	public List<User> studentList(Model model) throws JsonProcessingException;
	public int approve(int staffId,User approveUser) throws UserIdException, HigherAuthorityException;
	public List<User> approvedStudentList(Model model) throws JsonProcessingException;
	public List<User> notApprovedStudentList();
	public int activateOrDeactivateStudent(User User);
	public List<User> inactiveStudentList();
	
	public int addDepartment(Department department) throws ExistDepartmentNameException;
	public int activateOrDeactivateDepartment(Department Department);
	public List<Department> departmentList(Model model) throws JsonProcessingException;
	public List<Department> inactiveDepartmentList(Model model) throws JsonProcessingException;
	
	public int addOrUpdatePresentByOne(int userId) throws UserIdException;
	public int addOrUpdateAbsentByOne(int userId) throws UserIdException;
	public int activateOrDeactivateAttendance(Attendance attendance);
	public List<Attendance> attendanceList();
	public List<Attendance> inactiveAttendanceList();
	
	public int addSemester(Semester semester) throws ExistSemesterIdException;
	public int activateOrDeactivateSemester(Semester Semester);
	public int activeOrInactiveSemester();
	public List<Semester> semesterList(Model model) throws JsonProcessingException;
	public List<Semester> inactiveSemesterList(Model model) throws JsonProcessingException;
	
	public int addSubject(Subject subject) throws SemesterIdException, ExistDepartmentNameException;
	public int activateOrDeactivateSubject(Subject Subject);
	public Subject findByID(int id);
	public Subject findSubjectNameByDepartment(String department);
	public List<Subject> findSubjectListBySemester(int semesterId,Model model) throws JsonProcessingException;
	public List<Subject> findSubjectList(int semesterId,String department,Model model) throws JsonProcessingException;
	public List<Subject> subjectList(Model model) throws JsonProcessingException;
	public List<Subject> inactivesubjectList(Model model) throws JsonProcessingException;
	
	public int addExam(Exam exam) throws SubjectIdException, ExistExamException;
	public int activateOrDeactivateExam(Exam Exam);
	public List<Exam> examList(Model model) throws JsonProcessingException;
	public List<Exam> inactiveExamList(Model model) throws JsonProcessingException;
	
	public int addOrUpdateResult(Result Result) throws MarkException, UserIdException, ExamIdException;
	public int activateOrDeactivateOneResult(Result Result);
	public int activateOrDeactivateWholeExamResult(Result Result);
	public int activateOrDeactivateWholeUserResult(Result Result);
	public List<Result> resultList(Model model) throws JsonProcessingException;
	public List<Result> inactiveResultList(Model model) throws JsonProcessingException;
}
