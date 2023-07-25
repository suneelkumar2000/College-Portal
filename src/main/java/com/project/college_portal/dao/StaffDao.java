package com.project.college_portal.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.project.college_portal.connection.ConnectionUtil;
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
import com.project.college_portal.interfaces.StaffInterface;
import com.project.college_portal.mapper.ApprovingMapper;
import com.project.college_portal.mapper.AttendanceMapper;
import com.project.college_portal.mapper.DepartmentMapper;
import com.project.college_portal.mapper.ExamMapper;
import com.project.college_portal.mapper.ExamTypeMapper;
import com.project.college_portal.mapper.ExamnameMapper;
import com.project.college_portal.mapper.ExamIdMapper;
import com.project.college_portal.mapper.ResultMapper;
import com.project.college_portal.mapper.SemesterMapper;
import com.project.college_portal.mapper.SubjectIdMapper;
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
public class StaffDao implements StaffInterface {
	JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();
	Logger logger = LoggerFactory.getLogger(StaffDao.class);

	// --------- Students methods ------------

	public int checkHigherAuthority(int staffId) throws HigherAuthorityException {
		String selectStaff = "Select id,roll,status,is_active from user";
		List<User> user = jdbcTemplate.query(selectStaff, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (staffId))
				.filter(roll -> roll.getRoll().equals("staff")).filter(status -> status.getStatus().equals("approved"))
				.collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				return 0;
			}
		}
		throw new HigherAuthorityException("HigherAuthority Exception");
	}
	
	public List<User> findStudentById(int UserId,Model model) {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,semester,status,image,is_active from user where (roll='student' and id=?)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper(),UserId);
		return userList;
	}

	public User findStudentDepartmentById(int UserId) {
		String select = "select department from user where (roll='student' and id=?)";
		User userDepartment = jdbcTemplate.queryForObject(select, new UserDepartmentMapper(), UserId);
		return userDepartment;
	}

	public List<User> studentList(Model model) throws JsonProcessingException {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,semester,status,image,is_active from user where (roll='student' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		ObjectMapper object = new ObjectMapper();
		String user = object.writeValueAsString(userList);
		model.addAttribute("listOfStudents", user);
		return userList;
	}

	public int approve(int staffId, User approveUser) throws UserIdException, HigherAuthorityException {
		// TODO Auto-generated method stub
		String selectStaff = "Select id,roll,status,is_active from user";
		List<User> user = jdbcTemplate.query(selectStaff, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (staffId))
				.filter(roll -> roll.getRoll().equals("staff")).filter(status -> status.getStatus().equals("approved"))
				.collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {

				String select = "Select id,roll,status ,is_active from user";
				List<User> user2 = jdbcTemplate.query(select, new ApprovingMapper());
				List<User> user3 = user2.stream().filter(id -> id.getUserId() == (approveUser.getUserId()))
						.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
				for (User userModel2 : user3) {
					if (userModel2 != null) {
						String approve = "update user set status='approved'  where (roll='student' and id=?)";
						Object[] params = { approveUser.getUserId() };
						int noOfRows = jdbcTemplate.update(approve, params);
						logger.info(noOfRows + " student are approved");
						return 1;
					}
				}
				throw new UserIdException("User Id dosen't exist");
			}
		}
		throw new HigherAuthorityException("HigherAuthority Exception");

	}

	public List<User> approvedStudentList(Model model) throws JsonProcessingException {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,semester,status,image,is_active from user where (roll='student' and status='approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		ObjectMapper object = new ObjectMapper();
		String user = object.writeValueAsString(userList);
		model.addAttribute("listOfApprovedStudents", user);
		return userList;
	}

	public List<User> notApprovedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,semester,status,image,is_active from user where (roll='student' and status='not approved' and is_active =true)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		return userList;
	}

	public int activateOrDeactivateStudent(User User) {
		// TODO Auto-generated method stub
		String select = "Select id,roll,status,is_active from user";
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
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,year_of_joining,semester,status,image,is_active from user where (roll='student' and is_active =false)";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		return userList;
	}

	// --------- Department methods ------------

	public int addDepartment(int staffId, Department department)
			throws ExistDepartmentNameException, HigherAuthorityException {
		String selectStaff = "Select id,roll,status,is_active from user";
		List<User> user = jdbcTemplate.query(selectStaff, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (staffId))
				.filter(roll -> roll.getRoll().equals("staff")).filter(status -> status.getStatus().equals("approved"))
				.collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				String name = department.getDepartment();
				String select = "Select id,department,is_active from classroom";
				List<Department> department1 = (jdbcTemplate.query(select, new DepartmentMapper())).stream()
						.filter(dep -> ((dep.getDepartment()).toLowerCase()).equals((name).toLowerCase()))
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
		}
		throw new HigherAuthorityException("HigherAuthority Exception");
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
		ObjectMapper object = new ObjectMapper();
		String department = object.writeValueAsString(departmentList);
		model.addAttribute("listOfDepartment", department);
		return departmentList;
	}

	public List<Department> inactiveDepartmentList(Model model) throws JsonProcessingException {
		String select = "select id,department,is_active from classroom where (is_active =false and department !='not selected')";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		ObjectMapper object = new ObjectMapper();
		String department = object.writeValueAsString(departmentList);
		model.addAttribute("listOfDepartment", department);
		return departmentList;
	}

	// --------- Attendance methods ------------

	public int addOrUpdatePresentByOne(int userId) throws UserIdException {
		String select1 = "Select id,roll,status,is_active from user";
		List<User> user = (jdbcTemplate.query(select1, new ApprovingMapper())).stream()
				.filter(id -> id.getUserId() == userId).filter(roll1 -> roll1.getRoll().equals("student"))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (User userModel1 : user) {
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
		String select1 = "Select id,roll,status,is_active from user";
		List<User> user = (jdbcTemplate.query(select1, new ApprovingMapper())).stream()
				.filter(id -> id.getUserId() == userId).filter(roll1 -> roll1.getRoll().equals("student"))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (User userModel1 : user) {
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

	public int addSemester(Semester semester) throws ExistSemesterIdException {
		int semesterId = semester.getId();
		String select = "Select id,is_active from semester";
		List<Semester> semester1 = (jdbcTemplate.query(select, new SemesterMapper())).stream()
				.filter(id -> ((id.getId()) == (semesterId))).collect(Collectors.toList());
		for (Semester semesterModel1 : semester1) {
			if (semesterModel1 != null) {
				throw new ExistSemesterIdException("Exist Semester Exception");
			}
		}

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

	public int activeOrInactiveSemester() {
		// TODO Auto-generated method stub
		String select = "Select id,is_active from semester";
		List<Semester> semester = jdbcTemplate.query(select, new SemesterMapper());

		for (Semester semesterModel1 : semester) {
			if (semesterModel1 != null) {
				int SemesterId = semesterModel1.getId();
				LocalDate currentDate = LocalDate.now();
				int month = currentDate.getMonthValue();
				if (month > 5 && month < 12) {
					if (SemesterId % 2 == 0) {
						String deactivate = "update semester set is_active =false where id=?";
						Object[] params = { SemesterId };
						int noOfRows = jdbcTemplate.update(deactivate, params);
						logger.info(noOfRows + " even Semester are deactivated");
					} else {
						String deactivate = "update semester set is_active =true where id=?";
						Object[] params = { SemesterId };
						int noOfRows = jdbcTemplate.update(deactivate, params);
						logger.info(noOfRows + " odd Semester are activated");
					}
				} else {
					if (SemesterId % 2 == 0) {
						String deactivate = "update semester set is_active =true where id=?";
						Object[] params = { SemesterId };
						int noOfRows = jdbcTemplate.update(deactivate, params);
						logger.info(noOfRows + " even Semester are activated");
					} else {
						String deactivate = "update semester set is_active =false where id=?";
						Object[] params = { SemesterId };
						int noOfRows = jdbcTemplate.update(deactivate, params);
						logger.info(noOfRows + " odd Semester are deactivated");
					}
				}

			}
		}
		return 0;

	}

	public List<Semester> semesterList(Model model) throws JsonProcessingException {
		String select = "Select id,is_active from semester";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		ObjectMapper object = new ObjectMapper();
		String semester = object.writeValueAsString(semesterList);
		model.addAttribute("listOfSemester", semester);
		return semesterList;
	}

	public List<Semester> activeSemesterList(Model model) throws JsonProcessingException {
		String select = "Select id,is_active from semester where (is_active =true)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		ObjectMapper object = new ObjectMapper();
		String semester = object.writeValueAsString(semesterList);
		model.addAttribute("listOfSemester", semester);
		return semesterList;
	}

	public List<Semester> inactiveSemesterList(Model model) throws JsonProcessingException {
		String select = "Select id,is_active from semester where (is_active =false)";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		ObjectMapper object = new ObjectMapper();
		String semester = object.writeValueAsString(semesterList);
		model.addAttribute("listOfSemester", semester);
		return semesterList;
	}

	// --------- Subject methods ------------

	public int addSubject(Subject subject) throws SemesterIdException,DepartmentException, ExistSubjectNameException {
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

						String subjectName = subject.getDepartment();
						String select2 = "Select id,name,semester_id,department,is_active from subjects";
						List<Subject> sub = jdbcTemplate.query(select2, new SubjectMapper());
						List<Subject> subjectName1 = sub.stream()
								.filter(name -> name.getName().equals(subject.getName()))
								.filter(dep -> dep.getDepartment().equals(department))
								.filter(sem -> sem.getSemesterId() == (semesterId)).collect(Collectors.toList());
						for (Subject subjectModel1 : subjectName1) {
							if (departmentModel1 != null) {
								throw new ExistSubjectNameException("Subject Alredy exist");
							}
						}

						String add = "insert into subjects(name,semester_id,department) values(?,?,?)";
						Object[] params = { subject.getName(), semesterId, department };
						int noOfRow = jdbcTemplate.update(add, params);
						String update = "update subjects set id=(concat(SUBSTR(department, 1, 2),SUBSTR(name, 1, 2),semester_id)) where (name=? and semester_id=? and department=?)";
						Object[] param = { subject.getName(), semesterId, department };
						int noOfRows = jdbcTemplate.update(update, param);
						if (noOfRows > 0) {
							logger.info(noOfRows + "Saved");
							return 1;
						} else
							return 0;
					}

				}
				throw new DepartmentException("Department dosen't exist");
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
	
	public List<Subject> findSubjectID(String department, int semester, String name) {
		String find = "select id from subjects where (is_active =true and department =? and semester_id=? and name=?)";
		List<Subject> subjectNameList = jdbcTemplate.query(find, new SubjectIdMapper(),department,semester,name );
		return subjectNameList;
	}

	public List<Subject> findSubjectNameByDepartmentSemester(String department, int semester) {
		String find = "select name from subjects where (is_active =true and department =? and semester_id=?)";
		List<Subject> subjectNameList = jdbcTemplate.query(find, new SubjectNameMapper(), department, semester);
		return  subjectNameList;
	}

	public List<Subject> findSubjectIdByName(String name) {
		String find = "select id from subjects where (is_active =true and name =?)";
		List<Subject> subjectIdList = jdbcTemplate.query(find, new SubjectIdMapper(), name);
		return subjectIdList;
	}

	public List<Subject> findSubjectListBySemester(int semesterId, Model model) throws JsonProcessingException {
		String find = "select id,name,semester_id,department,is_active from subjects where (is_active =true and semester_id =?)";
		List<Subject> subjectList = jdbcTemplate.query(find, new SubjectMapper(), semesterId);
		ObjectMapper object = new ObjectMapper();
		String subject = object.writeValueAsString(subjectList);
		model.addAttribute("listOfSubjectbySemesterId", subject);
		return subjectList;
	}

	public List<Subject> findSubjectList(int semesterId, String department, Model model)
			throws JsonProcessingException {
		String find = "select id,name,semester_id,department,is_active from subjects where (is_active =true and semester_id =? and department=?)";
		List<Subject> subjectList = jdbcTemplate.query(find, new SubjectMapper(), semesterId, department);
		ObjectMapper object = new ObjectMapper();
		String subject = object.writeValueAsString(subjectList);
		model.addAttribute("listOfSubjectbySemesterId", subject);
		return subjectList;
	}

	public List<Subject> subjectList(Model model) throws JsonProcessingException {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =true)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		ObjectMapper object = new ObjectMapper();
		String subject = object.writeValueAsString(subjectList);
		model.addAttribute("listOfSubject", subject);
		return subjectList;
	}

	public List<Subject> inactivesubjectList(Model model) throws JsonProcessingException {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active =false)";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		ObjectMapper object = new ObjectMapper();
		String subject = object.writeValueAsString(subjectList);
		model.addAttribute("listOfSubject", subject);
		return subjectList;
	}

	// --------- Exam methods ------------

	public int addExam(Exam exam) throws SubjectIdException, ExistExamException {
		String subjectId = exam.getSubjectId();
		String select = "Select id,name,semester_id,department,is_active from subjects";
		List<Subject> subject = jdbcTemplate.query(select, new SubjectMapper());
		List<Subject> subject1 = subject.stream().filter(id -> id.getId().equals(subjectId))
				.filter(isActive -> isActive.isActive() == (true)).collect(Collectors.toList());
		for (Subject subjectModel1 : subject1) {
			if (subjectModel1 != null) {
				String select1 = "Select id,subject_id,name,date_,type,is_active from exam";
				List<Exam> exam1 = jdbcTemplate.query(select1, new ExamMapper());
				List<Exam> exam2 = exam1.stream().filter(subjectid -> (subjectid.getSubjectId()).equals(subjectId))
						.filter(name -> name.getName().equals(exam.getName()))
						.filter(type -> type.getType().equals(exam.getType())).collect(Collectors.toList());
				for (Exam examModel1 : exam2) {
					if (examModel1 != null) {
						throw new ExistExamException("Exist Exam Exception");
					}
				}
				String add = "insert into exam(subject_id,name,date_,type) values(?,?,?,?)";
				Object[] params = { subjectId, exam.getName(), exam.getDate(), exam.getType() };
				int noOfRows = jdbcTemplate.update(add, params);
				if (noOfRows > 0) {
					System.out.println(noOfRows + "Saved");
					return 1;
				} else {
					return 0;
				}
			}
		}
		throw new SubjectIdException("Subject Id dosen't exist");
	}

	public int activateOrDeactivateExam(Exam Exam) {
		// TODO Auto-generated method stub
		String select = "Select id,subject_id,name,date_,type,is_active from exam";
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
		String select = "select id,subject_id,name,date_,type,is_active from exam where (is_active =true)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		ObjectMapper object = new ObjectMapper();
		String exam = object.writeValueAsString(examList);
		model.addAttribute("listOfExam", exam);
		return examList;
	}

	public List<Exam> inactiveExamList(Model model) throws JsonProcessingException {
		String select = "select id,subject_id,name,date_,type,is_active from exam where (is_active =false)";
		List<Exam> examList = jdbcTemplate.query(select, new ExamMapper());
		ObjectMapper object = new ObjectMapper();
		String exam = object.writeValueAsString(examList);
		model.addAttribute("listOfExam", exam);
		return examList;
	}

	public List<Exam> findExamNameBySubjectID(String subjectID) {
		String find = "select name from exam where (subject_id=?)";
		List<Exam> examNameList = jdbcTemplate.query(find, new ExamnameMapper(), subjectID);
		return examNameList;
	}
	
	public List<Exam> findExamTypeBySubjectID(String subjectID) {
		String find = "select type from exam where (subject_id=?)";
		List<Exam> examNameList = jdbcTemplate.query(find, new ExamTypeMapper(), subjectID);
		return examNameList;
	}
	
	public List<Exam> findExam(String name,String type,String subjectID) {
		String find = "select id from exam where (name=? and type=? and subject_id=?)";
		List<Exam> examNameList = jdbcTemplate.query(find, new ExamIdMapper(),name,type,subjectID );
		return examNameList;
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
				String select1 = "Select id,roll,status,is_active from user";
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
		ObjectMapper object = new ObjectMapper();
		String result = object.writeValueAsString(resultList);
		model.addAttribute("listOfResult", result);
		return resultList;
	}

	public List<Result> inactiveResultList(Model model) throws JsonProcessingException {
		String select = "select exam_id,user_id,marks,is_active from result where (is_active =false)";
		List<Result> resultList = jdbcTemplate.query(select, new ResultMapper());
		ObjectMapper object = new ObjectMapper();
		String result = object.writeValueAsString(resultList);
		model.addAttribute("listOfResult", result);
		return resultList;
	}

}
