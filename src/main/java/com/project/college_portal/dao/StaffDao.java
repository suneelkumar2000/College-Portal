package com.project.college_portal.dao;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.project.college_portal.connection.ConnectionUtil;
import com.project.college_portal.exception.ExamIdException;
import com.project.college_portal.exception.ExistDepartmentNameException;
import com.project.college_portal.exception.ExistMailIdException;
import com.project.college_portal.exception.MarkException;
import com.project.college_portal.exception.SemesterIdException;
import com.project.college_portal.exception.SubjectIdException;
import com.project.college_portal.exception.UserIdException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class StaffDao {
	JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();
	Logger logger = LoggerFactory.getLogger(StaffDao.class);

	// --------- Students methods ------------

	public User findDepartmentById(int UserId) {
		String select = "select department from user where (roll='student' and id=?)";
		User userDepartment = jdbcTemplate.queryForObject(select, new UserDepartmentMapper(), UserId);
		return userDepartment;
	}

	public List<User> studentList(Model model) throws JsonProcessingException {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,status,is_active from user where (roll='student' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		ObjectMapper object=new ObjectMapper();
	       String user=object.writeValueAsString(userList);
		model.addAttribute("listOfStudents", user);
		return userList;
	}

	public int approve(User approveUser) {
		// TODO Auto-generated method stub
		String select = "Select id,roll,is_active from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (approveUser.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				String approve = "update user set status='approved'  where (roll='student' and id=?)";
				Object[] params = { approveUser.getUserId() };
				int noOfRows = jdbcTemplate.update(approve, params);
				logger.info(noOfRows + " student are approved");
				return 1;
			}
		}
		return 0;
	}

	public List<User> approvedStudentList(Model model) throws JsonProcessingException {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,status,is_active from user where (roll='student' and status='approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		ObjectMapper object=new ObjectMapper();
	       String user=object.writeValueAsString(userList);
		model.addAttribute("listOfApprovedStudents", user);
		return userList;
	}

	public List<User> notApprovedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,status,is_active from user where (roll='student' and status='not approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		return userList;
	}

	public int activateOrDeactivateStudent(User User) {
		// TODO Auto-generated method stub
		String select = "Select id,roll,is_active from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (User.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isActive() == (true))
				.collect(Collectors.toList());
		List<User> user2 = user.stream().filter(id -> id.getUserId() == (User.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isActive() == (false))
				.collect(Collectors.toList());
		for (User userModel1 : user1) {
			if (userModel1 != null) {
				String deactivate = "update user set is_active = false  where (roll='student' and id=?)";
				Object[] params = { User.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				logger.warn(noOfRows + " student are deactivated");
				return 1;
			}
		}
		for (User userModel2 : user2) {
			if (userModel2 != null) {
				String activate = "update user set is_active = true where (roll='student' and id=?)";
				Object[] params = { User.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " student are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<User> inactiveStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,status,is_active from user where (roll='student' and is_active =false)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		return userList;
	}

	// --------- Department methods ------------

	public int addDepartment(Department department) throws ExistDepartmentNameException {
		String name = department.getDepartment();
		String select = "Select id,department,is_active from classroom";
		List<Department> depart = jdbcTemplate.query(select, new DepartmentMapper());
		List<Department> department1 = depart.stream().filter(dep -> ((dep.getDepartment()).toLowerCase()).equals((name).toLowerCase()))
				.collect(Collectors.toList());
		for (Department departmentModel1 : department1) {
			if (departmentModel1 != null) {
				throw new ExistDepartmentNameException("Exist Department Exception");
			}
		}
		String add = "insert into classroom(department) values(?)";
		Object[] params = { name };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			logger.info(noOfRows + "Saved");
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
				logger.warn(noOfRows + " department are deactivated");
				return 1;
			}
		}
		for (Department departmentModel2 : department2) {
			if (departmentModel2 != null) {
				String activate = "update classroom set is_active =true where department=?";
				Object[] params = { Department.getDepartment() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " department are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Department> departmentList(Model model) throws JsonProcessingException {
		String select = "select id,department,is_active from classroom where (is_active =true and department !='not selected')";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		ObjectMapper object=new ObjectMapper();
	       String department=object.writeValueAsString(departmentList);
		model.addAttribute("listOfDepartment", department);
		return departmentList;
	}

	public List<Department> inactiveDepartmentList(Model model) throws JsonProcessingException {
		String select = "select id,department,is_active from classroom where (is_active =false and department !='not selected')";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		ObjectMapper object=new ObjectMapper();
	       String department=object.writeValueAsString(departmentList);
		model.addAttribute("listOfDepartment", department);
		return departmentList;
	}

	// --------- Attendance methods ------------

	public int addOrUpdatePresentByOne(int userId) throws UserIdException {
		String select1 = "Select id,roll,is_active from user";
		List<User> user = jdbcTemplate.query(select1, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == userId)
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isActive() == (true))
				.collect(Collectors.toList());
		for (User userModel1 : user1) {
			if (userModel1 != null) {

				String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance";
				List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
				List<Attendance> attendanceList1 = attendanceList.stream()
						.filter(userid -> userid.getUserId() == (userId))
						.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
				for (Attendance attendanceModel1 : attendanceList1) {
					if (attendanceModel1 != null) {
						int daysAttended = attendanceModel1.getDaysAttended() + 1;
						int daysLeave = attendanceModel1.getDaysLeave();
						int totalDays = daysAttended + daysLeave;
						double attendancePercentage = (daysAttended / totalDays) * 100;
						String update = "update attendance set total_days=?,days_attended=?,days_leave=?,attendance=? where user_id=?";
						Object[] params = { totalDays, daysAttended, daysLeave, attendancePercentage, userId };
						int noOfRows = jdbcTemplate.update(update, params);
						logger.info(noOfRows + " updated");
						return 1;
					}
				}
				int daysAttended = 1;
				int daysLeave = 0;
				int totalDays = daysAttended + daysLeave;
				double attendancePercentage = (daysAttended / totalDays) * 100;
				String add = "insert into attendance(user_id,total_days,days_attended,days_leave,attendance) values(?,?,?,?,?)";
				Object[] params = { userId, totalDays, daysAttended, daysLeave, attendancePercentage };
				int noOfRows = jdbcTemplate.update(add, params);
				logger.info(noOfRows + " inserted");
				return 1;
			}
		}
		throw new UserIdException("User Id dosen't exist");
	}

	public int addOrUpdateAbsentByOne(int userId) throws UserIdException {
		String select1 = "Select id,roll,is_active from user";
		List<User> user = jdbcTemplate.query(select1, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == userId)
				.filter(roll1 -> roll1.getRoll().equals("student")).filter(isActive -> isActive.isActive() == (true))
				.collect(Collectors.toList());
		for (User userModel1 : user1) {
			if (userModel1 != null) {

				String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance";
				List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
				List<Attendance> attendanceList1 = attendanceList.stream()
						.filter(userid -> userid.getUserId() == (userId))
						.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
				for (Attendance attendanceModel1 : attendanceList1) {
					if (attendanceModel1 != null) {
						int daysAttended = attendanceModel1.getDaysAttended();
						int daysLeave = attendanceModel1.getDaysLeave() + 1;
						int totalDays = daysAttended + daysLeave;
						double attendancePercentage = (daysAttended / totalDays) * 100;
						String update = "update attendance set total_days=?,days_attended=?,days_leave=?,attendance=? where user_id=?";
						Object[] params = { totalDays, daysAttended, daysLeave, attendancePercentage, userId };
						int noOfRows = jdbcTemplate.update(update, params);
						logger.info(noOfRows + " updated");
						return 1;
					}
				}
				int daysAttended = 0;
				int daysLeave = 1;
				int totalDays = daysAttended + daysLeave;
				double attendancePercentage = (daysAttended / totalDays) * 100;
				String add = "insert into attendance(user_id,total_days,days_attended,days_leave,attendance) values(?,?,?,?,?)";
				Object[] params = { userId, totalDays, daysAttended, daysLeave, attendancePercentage };
				int noOfRows = jdbcTemplate.update(add, params);
				logger.info(noOfRows + " insert");
				return 1;
			}
		}
		throw new UserIdException("User Id dosen't exist");
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
				logger.info(noOfRows + " user attendance are deactivated");
				return 1;
			}
		}
		for (Attendance attendanceModel2 : attendanceList2) {
			if (attendanceModel2 != null) {
				String activate = "update attendance set is_active =true where user_id=?";
				Object[] params = { attendance.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " user attendance are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<Attendance> attendanceList() {
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance where (is_active =true)";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		return attendanceList;
	}

	public List<Attendance> inactiveAttendanceList() {
		String select = "Select user_id,total_days,days_attended,days_leave,attendance,is_active from attendance where (is_active =false)";
		List<Attendance> attendanceList = jdbcTemplate.query(select, new AttendanceMapper());
		return attendanceList;
	}

	// --------- Semester methods ------------

	public int addSemester(Semester semester) {
		String add = "insert into semester(id) values(?)";
		Object[] params = { semester.getId() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			logger.info(noOfRows + "Saved");
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
				logger.info(noOfRows + " Semester are deactivated");
				return 1;
			}
		}
		for (Semester semesterModel2 : semester2) {
			if (semesterModel2 != null) {
				String activate = "update semester set is_active =true where id=?";
				Object[] params = { Semester.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " Semester are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Semester> semesterList(Model model) throws JsonProcessingException {
		String select = "Select id,is_active from semester where (is_active =true)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		ObjectMapper object=new ObjectMapper();
	       String semester=object.writeValueAsString(semesterList);
		model.addAttribute("listOfSemester", semester);
		return semesterList;
	}

	public List<Semester> inactiveSemesterList(Model model) throws JsonProcessingException {
		String select = "Select id,is_active from semester where (is_active =false)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		ObjectMapper object=new ObjectMapper();
	       String semester=object.writeValueAsString(semesterList);
		model.addAttribute("listOfSemester", semester);
		return semesterList;
	}

	// --------- Subject methods ------------

	public int addSubject(Subject subject) throws SemesterIdException, ExistDepartmentNameException {
		int semesterId = subject.getSemesterId();
		String select = "Select id,is_active from semester";
		List<Semester> semester = jdbcTemplate.query(select, new SemesterMapper());
		List<Semester> semester1 = semester.stream().filter(id -> id.getId() == (semesterId))
				.collect(Collectors.toList());
		for (Semester semesterModel1 : semester1) {
			if (semesterModel1 != null) {

				String department = subject.getDepartment();
				String select1 = "Select id,department,is_active from classroom";
				List<Department> depart = jdbcTemplate.query(select1, new DepartmentMapper());
				List<Department> department1 = depart.stream().filter(dep -> dep.getDepartment().equals(department))
						.collect(Collectors.toList());
				for (Department departmentModel1 : department1) {
					if (departmentModel1 != null) {

						String add = "insert into subjects(id,name,semester_id,department) values(?,?,?,?)";
						Object[] params = { subject.getId(), subject.getName(), semesterId, department };
						int noOfRows = jdbcTemplate.update(add, params);
						if (noOfRows > 0) {
							logger.info(noOfRows + "Saved");
							return 1;
						} else
							return 0;
					}
				}
				throw new ExistDepartmentNameException("Department dosen't exist");
			}
		}
		throw new SemesterIdException("Semester Id dosen't exist");
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
				logger.info(noOfRows + " subjects are deactivated");
				return 1;
			}
		}
		for (Subject subjectModel2 : subject2) {
			if (subjectModel2 != null) {
				String activate = "update subjects set is_active =true where id=?";
				Object[] params = { Subject.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " subjects are activated");
				return 2;
			}
		}
		return 0;
	}

	public Subject findByID(int id) {
		String find = "select id,name,semester_id,department,is_active from subjects where (is_active =true and id =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectMapper(), id);
		return subjectNameList;
	}

	public Subject findSubjectNameByDepartment(String department) {
		String find = "select name from subjects where (is_active =true and department =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectNameMapper(), department);
		return subjectNameList;
	}

	public List<Subject> subjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =true)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		return subjectList;
	}

	public List<Subject> inactivesubjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =false)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		return subjectList;
	}

	// --------- Exam methods ------------

	public int addExam(Exam exam) throws SubjectIdException {
		int subjectId = exam.getSubjectId();
		String select = "Select id,name,semester_id,department,is_active from subjects";
		List<Subject> subject = jdbcTemplate.query(select, new SubjectMapper());
		List<Subject> subject1 = subject.stream().filter(id -> id.getId() == (subjectId))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Subject subjectModel1 : subject1) {
			if (subjectModel1 != null) {
				String add = "insert into exam(id,subject_id,name,type) values(?,?,?,?)";
				Object[] params = { exam.getId(), subjectId, exam.getName(), exam.getType() };
				int noOfRows = jdbcTemplate.update(add, params);
				if (noOfRows > 0) {
					System.out.println(noOfRows + "Saved");
					return 1;
				} else
					return 0;
			}
		}
		throw new SubjectIdException("Subject Id dosen't exist");
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
				logger.info(noOfRows + " Exams are deactivated");
				return 1;
			}
		}
		for (Exam examModel2 : exam2) {
			if (examModel2 != null) {
				String activate = "update exam set is_active =true where id=?";
				Object[] params = { Exam.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " Exams are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Exam> examList(Model model) throws JsonProcessingException {
		String select = "select id,subject_id,name,type,is_active from exam where (is_active =true)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		ObjectMapper object=new ObjectMapper();
	       String exam=object.writeValueAsString(examList);
		model.addAttribute("listOfExam", exam);
		return examList;
	}

	public List<Exam> inactiveExamList(Model model) throws JsonProcessingException {
		String select = "select id,subject_id,name,type,is_active from exam where (is_active =false)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		ObjectMapper object=new ObjectMapper();
	       String exam=object.writeValueAsString(examList);
		model.addAttribute("listOfExam", exam);
		return examList;
	}

	// --------- Result methods ------------

	public int addOrUpdateResult(Result Result) throws MarkException, UserIdException, ExamIdException {
		int examid = Result.getUserId();
		String select = "Select id,subject_id,name,type,is_active from exam";
		List<Exam> exam = jdbcTemplate.query(select, new ExamMapper());
		List<Exam> exam1 = exam.stream().filter(id -> id.getId() == (examid))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Exam examModel1 : exam1) {
			if (examModel1 != null) {

				int userId = Result.getUserId();
				String select1 = "Select id,roll,is_active from user";
				List<User> user = jdbcTemplate.query(select1, new ApprovingMapper());
				List<User> user1 = user.stream().filter(id -> id.getUserId() == userId)
						.filter(roll1 -> roll1.getRoll().equals("student"))
						.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
				for (User userModel1 : user1) {
					if (userModel1 != null) {

						int marks = Result.getMarks();
						if (marks >= 0 && marks <= 100) {
							String select2 = "Select exam_id,user_id,marks,is_active from result";
							List<Result> result = jdbcTemplate.query(select2, new ResultMapper());
							List<Result> result1 = result.stream()
									.filter(examId -> examId.getExamId() == (Result.getExamId()))
									.filter(UserId -> UserId.getUserId() == (Result.getUserId()))
									.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
							for (Result resultModel1 : result1) {
								if (resultModel1 != null) {
									System.out.println("Result already exist");
									String update = "update result set marks =? where (exam_id=? and user_id=?)";
									Object[] params = { marks, Result.getExamId(), userId };
									int noOfRows = jdbcTemplate.update(update, params);
									logger.info(noOfRows + " updated");
									return 1;
								}
							}
							String add = "insert into result(exam_id,user_id,marks) values(?,?,?)";
							Object[] params = { Result.getExamId(), Result.getUserId(), marks };
							int noOfRows = jdbcTemplate.update(add, params);
							logger.info(noOfRows + " Saved");
							return 2;
						} else {
							throw new MarkException("Invalid Marks");
						}
					}
				}
				throw new UserIdException("User Id dosen't exist");
			}
		}
		throw new ExamIdException("Exam Id dosen't exist");
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
				logger.info(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where (exam_id=? and user_id=?)";
				Object[] params = { Result.getExamId(), Result.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " Results are activated");
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
				logger.info(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where exam_id=?";
				Object[] params = { Result.getExamId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " Results are activated");
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
				logger.info(noOfRows + " Results are deactivated");
				return 1;
			}
		}
		for (Result resultModel2 : result2) {
			if (resultModel2 != null) {
				String activate = "update result set is_active =true where user_id=?";
				Object[] params = { Result.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				logger.info(noOfRows + " Results are activated");
				return 2;
			}
		}
		return 0;
	}

	public List<Result> resultList(Model model) throws JsonProcessingException {
		String select = "select exam_id,user_id,marks,is_active from result where (is_active =true)";
		List<Result> resultList = jdbcTemplate.query(select, new ResultMapper());
		ObjectMapper object=new ObjectMapper();
	       String result=object.writeValueAsString(resultList);
		model.addAttribute("listOfResult", result);
		return resultList;
	}

	public List<Result> inactiveResultList(Model model) throws JsonProcessingException {
		String select = "select exam_id,user_id,marks,is_active from result where (is_active =false)";
		List<Result> resultList = jdbcTemplate.query(select, new ResultMapper());
		ObjectMapper object=new ObjectMapper();
	       String result=object.writeValueAsString(resultList);
		model.addAttribute("listOfResult", result);
		return resultList;
	}

}
