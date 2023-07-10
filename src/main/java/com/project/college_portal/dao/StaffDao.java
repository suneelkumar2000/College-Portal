package com.project.college_portal.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.college_portal.mapper.ApprovingMapper;
import com.project.college_portal.mapper.AttendanceMapper;
import com.project.college_portal.mapper.DepartmentMapper;
import com.project.college_portal.mapper.ExamMapper;
import com.project.college_portal.mapper.ResultMapper;
import com.project.college_portal.mapper.SemesterMapper;
import com.project.college_portal.mapper.SubjectMapper;
import com.project.college_portal.mapper.SubjectNameMapper;
import com.project.college_portal.mapper.UserDepartmentMapper;
import com.project.college_portal.mapper.UserMapper;
import com.project.college_portal.model.Attendance;
import com.project.college_portal.model.Department;
import com.project.college_portal.model.Exam;
import com.project.college_portal.model.Result;
import com.project.college_portal.model.Semester;
import com.project.college_portal.model.Subject;
import com.project.college_portal.model.User;

@Repository
public class StaffDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	// --------- Students methods ------------

	public User findDepartmentById(User user) {
		String select = "select department from user where (roll='student' and id=?)";
		User userDepartment = jdbcTemplate.queryForObject(select, new UserDepartmentMapper(), user.getUserId());
		System.out.println(userDepartment);
		return userDepartment;
	}

	public List<User> studentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public int approve(User approveUser) {
		// TODO Auto-generated method stub
		String select = "Select id,roll from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (approveUser.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				String approve = "update user set status='approved'  where (roll='student' and id=?)";
				Object[] params = { approveUser.getUserId() };
				int noOfRows = jdbcTemplate.update(approve, params);
				System.out.println(noOfRows + " student are approved");
				return 1;
			}
		}
		return 0;
	}

	public List<User> approvedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and status='approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public List<User> notApprovedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and status='not approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public int activateOrDeactivateStudent(User User) {
		// TODO Auto-generated method stub
		String select = "Select id,roll from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (User.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isIsActive() == (true))
				.collect(Collectors.toList());
		List<User> user2 = user.stream().filter(id -> id.getUserId() == (User.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isIsActive() == (false))
				.collect(Collectors.toList());
		for (User userModel1 : user1) {
			if (userModel1 != null) {
				String deactivate = "update user set is_active = false  where (roll='student' and id=?)";
				Object[] params = { User.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " student are deactivated");
				return 1;
			}
		}
		for (User userModel2 : user2) {
			if (userModel2 != null) {
				String activate = "update user set is_active = true where (roll='student' and id=?)";
				Object[] params = { User.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " student are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<User> inactiveStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and is_active =false)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	// --------- Department methods ------------

	public int addDepartment(Department department) {
		String add = "insert into classroom(department) values(?)";
		Object[] params = { department.getDepartment() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			System.out.println(noOfRows + "Saved");
			return 1;
		} else
			return 0;
	}

	public int activateOrDeactivateDepartment(Department Department) {
		// TODO Auto-generated method stub
		String select = "Select id,department,is_active from classroom";
		List<Department> department = jdbcTemplate.query(select, new DepartmentMapper());
		List<Department> department1 = department.stream()
				.filter(dep -> dep.getDepartment().equals(Department.getDepartment()))
				.filter(isActive -> isActive.isIsActive() == (true)).collect(Collectors.toList());
		List<Department> department2 = department.stream()
				.filter(dep -> dep.getDepartment().equals(Department.getDepartment()))
				.filter(isActive -> isActive.isIsActive() == (false)).collect(Collectors.toList());
		for (Department departmentModel1 : department1) {
			if (departmentModel1 != null) {
				String deactivate = "update classroom set is_active =false where department=?";
				Object[] params = { Department.getDepartment() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " department are deactivated");
				return 1;
			}
		}
		for (Department departmentModel2 : department2) {
			if (departmentModel2 != null) {
				String activate = "update classroom set is_active =true where department=?";
				Object[] params = { Department.getDepartment() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " department are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Department> departmentList() {
		String select = "select id,department,is_active from classroom where (is_active =true)";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		System.out.println(departmentList);
		return departmentList;
	}

	public List<Department> inactiveDepartmentList() {
		String select = "select id,department,is_active from classroom where (is_active =false)";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		System.out.println(departmentList);
		return departmentList;
	}

	// --------- Attendance methods ------------

	public int addOrUpdatePresentByOne(Attendance attendance) {
		int userId = attendance.getUserId();
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		List<Attendance> attendanceList1 = attendanceList.stream().filter(userid -> userid.getUserId() == (userId))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Attendance attendanceModel1 : attendanceList1) {
			if (attendanceModel1 != null) {
				int daysAttended = attendanceModel1.getDaysAttended() + 1;
				int daysLeave = attendanceModel1.getDaysLeave();
				int totalDays = daysAttended + daysLeave;
				int attendancePercentage = (daysAttended / totalDays) * 100;
				String update = "update attendance set total_days=?,days_attended=?,days_leave=?,attendance=? where user_id=?";
				Object[] params = { totalDays, daysAttended, daysLeave, attendancePercentage, userId };
				int noOfRows = jdbcTemplate.update(update, params);
				System.out.println(noOfRows + " updated");
				return 1;
			} else {
				int daysAttended = 1;
				int daysLeave = 0;
				int totalDays = daysAttended + daysLeave;
				int attendancePercentage = (daysAttended / totalDays) * 100;
				String add = "insert into attendance(user_id,total_days,days_attended,days_leave,attendance) values(?,?,?,?,?)";
				Object[] params = { userId, totalDays, daysAttended, daysLeave, attendancePercentage };
				int noOfRows = jdbcTemplate.update(add, params);
				System.out.println(noOfRows + " updated");
				return 2;
			}
		}
		return 0;
	}

	public int addOrUpdateAbsentByOne(Attendance attendance) {
		int userId = attendance.getUserId();
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		List<Attendance> attendanceList1 = attendanceList.stream().filter(userid -> userid.getUserId() == (userId))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Attendance attendanceModel1 : attendanceList1) {
			if (attendanceModel1 != null) {
				int daysAttended = attendanceModel1.getDaysAttended();
				int daysLeave = attendanceModel1.getDaysLeave() + 1;
				int totalDays = daysAttended + daysLeave;
				int attendancePercentage = (daysAttended / totalDays) * 100;
				String update = "update attendance set total_days=?,days_attended=?,days_leave=?,attendance=? where user_id=?";
				Object[] params = { totalDays, daysAttended, daysLeave, attendancePercentage, userId };
				int noOfRows = jdbcTemplate.update(update, params);
				System.out.println(noOfRows + " updated");
				return 1;
			} else {
				int daysAttended = 0;
				int daysLeave = 1;
				int totalDays = daysAttended + daysLeave;
				int attendancePercentage = (daysAttended / totalDays) * 100;
				String add = "insert into attendance(user_id,total_days,days_attended,days_leave,attendance) values(?,?,?,?,?)";
				Object[] params = { userId, totalDays, daysAttended, daysLeave, attendancePercentage };
				int noOfRows = jdbcTemplate.update(add, params);
				System.out.println(noOfRows + " updated");
				return 2;
			}
		}
		return 0;
	}

	public int activateOrDeactivateAttendance(Attendance attendance) {
		// TODO Auto-generated method stub
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		List<Attendance> attendanceList1 = attendanceList.stream()
				.filter(userid -> userid.getUserId() == (attendance.getUserId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Attendance> attendanceList2 = attendanceList.stream()
				.filter(userid -> userid.getUserId() == (attendance.getUserId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Attendance attendanceModel1 : attendanceList1) {
			if (attendanceModel1 != null) {
				String deactivate = "update attendance set is_active =false where user_id=?";
				Object[] params = { attendance.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " user attendance are deactivated");
				return 1;
			}
		}
		for (Attendance attendanceModel2 : attendanceList2) {
			if (attendanceModel2 != null) {
				String activate = "update attendance set is_active =true where user_id=?";
				Object[] params = { attendance.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " user attendance are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<Attendance> attendanceList() {
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance where (is_active =true)";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		System.out.println(attendanceList);
		return attendanceList;
	}

	public List<Attendance> inactiveAttendanceList() {
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance where (is_active =false)";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		System.out.println(attendanceList);
		return attendanceList;
	}

	// --------- Semester methods ------------

	public int addSemester(Semester semester) {
		String add = "insert into semester(id) values(?)";
		Object[] params = { semester.getId() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			System.out.println(noOfRows + "Saved");
			return 1;
		} else
			return 0;
	}

	public int activateOrDeactivateSemester(Semester Semester) {
		// TODO Auto-generated method stub
		String select = "Select id,is_active from semester";
		List<Semester> semester = jdbcTemplate.query(select, new SemesterMapper());
		List<Semester> semester1 = semester.stream().filter(id -> id.getId() == (Semester.getId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Semester> semester2 = semester.stream().filter(id -> id.getId() == (Semester.getId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Semester semesterModel1 : semester1) {
			if (semesterModel1 != null) {
				String deactivate = "update semester set is_active =false where id=?";
				Object[] params = { Semester.getId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Semester are deactivated");
				return 1;
			}
		}
		for (Semester semesterModel2 : semester2) {
			if (semesterModel2 != null) {
				String activate = "update semester set is_active =true where id=?";
				Object[] params = { Semester.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Semester are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<Semester> semesterList() {
		String select = "Select id,is_active from semester where (is_active =true)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		System.out.println(semesterList);
		return semesterList;
	}

	public List<Semester> inactiveSemesterList() {
		String select = "Select id,is_active from semester where (is_active =false)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		System.out.println(semesterList);
		return semesterList;
	}

	// --------- Subject methods ------------

	public int addSubject(Subject subject) {
		String add = "insert into subjects(id,name,semester_id,department) values(?,?,?,?)";
		Object[] params = { subject.getId(), subject.getName(), subject.getSemesterId(), subject.getDepartment() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			System.out.println(noOfRows + "Saved");
			return 1;
		} else
			return 0;
	}

	public int activateOrDeactivateSubject(Subject Subject) {
		// TODO Auto-generated method stub
		String select = "Select id,name,semester_id,department,is_active from subjects";
		List<Subject> subject = jdbcTemplate.query(select, new SubjectMapper());
		List<Subject> subject1 = subject.stream().filter(id -> id.getId() == (Subject.getId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Subject> subject2 = subject.stream().filter(id -> id.getId() == (Subject.getId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Subject subjectModel1 : subject1) {
			if (subjectModel1 != null) {
				String deactivate = "update subjects set is_active =false where id=?";
				Object[] params = { Subject.getId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " subjects are deactivated");
				return 1;
			}
		}
		for (Subject subjectModel2 : subject2) {
			if (subjectModel2 != null) {
				String activate = "update subjects set is_active =true where id=?";
				Object[] params = { Subject.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " subjects are activated");
				return 2;
			}
		}
		return 0;
	}

	public Subject findByID(Subject subject) {
		String find = "select id,name,semester_id,department,is_active from subjects where (is_active =true and id =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectMapper(), subject.getId());
		System.out.println(subjectNameList);
		return subjectNameList;
	}

	public Subject findSubjectNameByDepartment(Subject subject) {
		String find = "select name from subjects where (is_active =true and department =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectNameMapper(), subject.getDepartment());
		System.out.println(subjectNameList);
		return subjectNameList;
	}

	public List<Subject> subjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =true)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		System.out.println(subjectList);
		return subjectList;
	}

	public List<Subject> inactivesubjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =false)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		System.out.println(subjectList);
		return subjectList;
	}

	// --------- Exam methods ------------

	public int addExam(Exam exam) {
		String add = "insert into exam(id,subject_id,name,type) values(?,?,?,?)";
		Object[] params = { exam.getId(), exam.getSubjectId(), exam.getName(), exam.getType() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			System.out.println(noOfRows + "Saved");
			return 1;
		} else
			return 0;
	}

	public int activateOrDeactivateExam(Exam Exam) {
		// TODO Auto-generated method stub
		String select = "Select id,subject_id,name,type,is_active from exam";
		List<Exam> exam = jdbcTemplate.query(select, new ExamMapper());
		List<Exam> exam1 = exam.stream().filter(id -> id.getId() == (Exam.getId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Exam> exam2 = exam.stream().filter(id -> id.getId() == (Exam.getId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Exam examModel1 : exam1) {
			if (examModel1 != null) {
				String deactivate = "update exam set is_active =false where id=?";
				Object[] params = { Exam.getId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Exams are deactivated");
				return 1;
			}
		}
		for (Exam examModel2 : exam2) {
			if (examModel2 != null) {
				String activate = "update exam set is_active =true where id=?";
				Object[] params = { Exam.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Exams are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Exam> examList() {
		String select = "select id,subject_id,name,type,is_active from exam where (is_active =true)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		System.out.println(examList);
		return examList;
	}

	public List<Exam> inactiveExamList() {
		String select = "select id,subject_id,name,type,is_active from exam where (is_active =false)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		System.out.println(examList);
		return examList;
	}

	// --------- Result methods ------------

	public int addOrUpdateResult(Result Result) {
		String select = "Select exam_id,user_id,marks,is_active from result";
		List<Result> result = jdbcTemplate.query(select, new ResultMapper());
		List<Result> result1 = result.stream().filter(examId -> examId.getExamId() == (Result.getExamId()))
				.filter(UserId -> UserId.getUserId() == (Result.getUserId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Result resultModel1 : result1) {
			if (resultModel1 != null) {
				System.out.println("Result already exist");
				String update = "update result set marks =? where (exam_id=? and user_id=?)";
				Object[] params = { Result.getExamId(), Result.getUserId() };
				int noOfRows = jdbcTemplate.update(update, params);
				System.out.println(noOfRows + " updated");
				return 1;
			} else {
				String add = "insert into result(exam_id,user_id,marks) values(?,?,?)";
				Object[] params = { Result.getExamId(), Result.getUserId(), Result.getMarks() };
				int noOfRows = jdbcTemplate.update(add, params);
				System.out.println(noOfRows + " Saved");
				return 2;
			}
		}
		return 0;
	}

	public int activateOrDeactivateOneResult(Result Result) {
		// TODO Auto-generated method stub
		String select = "Select exam_id,user_id,marks,is_active from result";
		List<Result> result = jdbcTemplate.query(select, new ResultMapper());
		List<Result> result1 = result.stream().filter(examId -> examId.getExamId() == (Result.getExamId()))
				.filter(UserId -> UserId.getUserId() == (Result.getUserId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Result> result2 = result.stream().filter(examId -> examId.getExamId() == (Result.getExamId()))
				.filter(UserId -> UserId.getUserId() == (Result.getUserId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Result resultModel1 : result1) {
			if (resultModel1 != null) {
				String deactivate = "update result set is_active =false where (exam_id=? and user_id=?)";
				Object[] params = { Result.getExamId(), Result.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where (exam_id=? and user_id=?)";
				Object[] params = { Result.getExamId(), Result.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Results are activated");
				return 2;
			}
		}
		return 0;
	}

	public int activateOrDeactivateWholeExamResult(Result Result) {
		// TODO Auto-generated method stub
		String select = "Select exam_id,user_id,marks,is_active from result";
		List<Result> result = jdbcTemplate.query(select, new ResultMapper());
		List<Result> result1 = result.stream().filter(examId -> examId.getExamId() == (Result.getExamId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Result> result2 = result.stream().filter(examId -> examId.getExamId() == (Result.getExamId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Result resultModel1 : result1) {
			if (resultModel1 != null) {
				String deactivate = "update result set is_active =false where exam_id=?";
				Object[] params = { Result.getExamId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where exam_id=?";
				Object[] params = { Result.getExamId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Results are activated");
				return 2;
			}
		}
		return 0;
	}

	public int activateOrDeactivateWholeUserResult(Result Result) {
		// TODO Auto-generated method stub
		String select = "Select exam_id,user_id,marks,is_active from result";
		List<Result> result = jdbcTemplate.query(select, new ResultMapper());
		List<Result> result1 = result.stream().filter(UserId -> UserId.getUserId() == (Result.getUserId()))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		List<Result> result2 = result.stream().filter(UserId -> UserId.getUserId() == (Result.getUserId()))
				.filter(isActive -> isActive.isActive() == (false)).collect(Collectors.toList());
		for (Result resultModel1 : result1) {
			if (resultModel1 != null) {
				String deactivate = "update result set is_active =false where user_id=?";
				Object[] params = { Result.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where user_id=?";
				Object[] params = { Result.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Results are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Result> resultList() {
		String select = "select exam_id,user_id,marks,is_active from result where (is_active =true)";
		List<Result> resultList = jdbcTemplate.query(select, new ResultMapper());
		System.out.println(resultList);
		return resultList;
	}

	public List<Result> inactiveResultList() {
		String select = "select exam_id,user_id,marks,is_active from result where (is_active =false)";
		List<Result> resultList = jdbcTemplate.query(select, new ResultMapper());
		System.out.println(resultList);
		return resultList;
	}

}
