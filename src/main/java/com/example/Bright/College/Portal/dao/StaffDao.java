package com.example.Bright.College.Portal.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.Bright.College.Portal.mapper.UserMapper;
import com.example.Bright.College.Portal.mapper.ApprovingMapper;
import com.example.Bright.College.Portal.mapper.DepartmentMapper;
import com.example.Bright.College.Portal.mapper.SubjectMapper;
import com.example.Bright.College.Portal.mapper.SubjectNameMapper;
import com.example.Bright.College.Portal.mapper.UserDepartmentMapper;
import com.example.Bright.College.Portal.mapper.SemesterMapper;
import com.example.Bright.College.Portal.model.Department;
import com.example.Bright.College.Portal.model.Exam;
import com.example.Bright.College.Portal.model.Semester;
import com.example.Bright.College.Portal.model.Subject;
import com.example.Bright.College.Portal.model.User;

@Repository
public class StaffDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	// --------- Students methods ------------
	
	public User findDepartmentById(User user) {
		String select = "select department from user where (roll='student' and id=?)";
		User userDepartment = jdbcTemplate.queryForObject(select, new UserDepartmentMapper (),user.getUserId());
		System.out.println(userDepartment);
		return userDepartment;
	}

	public List<User> studentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and is_active ='true')";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public List<User> approvedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and status='approved' and is_active ='true')";
		List<User> userList = jdbcTemplate.query(select, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public List<User> notApprovedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and status='not approved' and is_active ='true')";
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

	public int deactivateStudent(User deactivateUser) {
		// TODO Auto-generated method stub
		String select = "Select id,roll from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (deactivateUser.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				String deactivate = "update user set is_active ='false'  where (roll='student' and id=?)";
				Object[] params = { deactivateUser.getUserId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " student are deactivated");
				return 1;
			}
		}
		return 0;
	}

	public int activateStudent(User activateUser) {
		// TODO Auto-generated method stub
		String select = "Select id,roll from user";
		List<User> user = jdbcTemplate.query(select, new ApprovingMapper());
		List<User> user1 = user.stream().filter(id -> id.getUserId() == (activateUser.getUserId()))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
		for (User userModel : user1) {
			if (userModel != null) {
				String activate = "update user set is_active ='true'  where (roll='student' and id=?)";
				Object[] params = { activateUser.getUserId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " student are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<User> deactivatedStudentList() {
		String select = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user where (roll='student' and is_active ='false')";
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

	public int deactivateDepartment(Department deactivateDepartment) {
		// TODO Auto-generated method stub
		String select = "Select id,department,is_active from classroom";
		List<Department> department = jdbcTemplate.query(select, new DepartmentMapper());
		List<Department> department1 = department.stream()
				.filter(dep -> dep.getDepartment().equals(deactivateDepartment.getDepartment()))
				.collect(Collectors.toList());
		for (Department departmentModel : department1) {
			if (departmentModel != null) {
				String deactivate = "update classroom set is_active ='false' where department=?";
				Object[] params = { deactivateDepartment.getDepartment() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " department are deactivated");
				return 1;
			}
		}
		return 0;
	}

	public int activateDepartment(Department activateDepartment) {
		// TODO Auto-generated method stub
		String select = "Select id,department,is_active from classroom";
		List<Department> department = jdbcTemplate.query(select, new DepartmentMapper());
		List<Department> department1 = department.stream()
				.filter(dep -> dep.getDepartment().equals(activateDepartment.getDepartment()))
				.collect(Collectors.toList());
		for (Department departmentModel : department1) {
			if (departmentModel != null) {
				String activate = "update classroom set is_active ='true' where department=?";
				Object[] params = { activateDepartment.getDepartment() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " department are activated");
				return 1;
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

	public List<Department> deactivatedDepartmentList() {
		String select = "select id,department,is_active from classroom where (is_active =false)";
		List<Department> departmentList = jdbcTemplate.query(select, new DepartmentMapper());
		System.out.println(departmentList);
		return departmentList;
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

	public int deactivateSemester(Semester deactivateSemester) {
		// TODO Auto-generated method stub
		String select = "Select id,is_active from semester";
		List<Semester> semester = jdbcTemplate.query(select, new SemesterMapper());
		List<Semester> semester1 = semester.stream().filter(id -> id.getId() == (deactivateSemester.getId()))
				.collect(Collectors.toList());
		for (Semester semesterModel : semester1) {
			if (semesterModel != null) {
				String deactivate = "update semester set is_active ='false' where id=?";
				Object[] params = { deactivateSemester.getId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " Semester are deactivated");
				return 1;
			}
		}
		return 0;
	}

	public int activateSemester(Semester activateSemester) {
		// TODO Auto-generated method stub
		String select = "Select id,is_active from semester";
		List<Semester> semester = jdbcTemplate.query(select, new SemesterMapper());
		List<Semester> semester1 = semester.stream().filter(id -> id.getId() == (activateSemester.getId()))
				.collect(Collectors.toList());
		for (Semester semesterModel : semester1) {
			if (semesterModel != null) {
				String activate = "update semester set is_active ='true' where id=?";
				Object[] params = { activateSemester.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " Semester are activated");
				return 1;
			}
		}
		return 0;
	}

	public List<Semester> semesterList() {
		String select = "Select id,is_active from semester where (is_active ='true')";
		List<Semester> semesterList = jdbcTemplate.query(select, new SemesterMapper());
		System.out.println(semesterList);
		return semesterList;
	}

	public List<Semester> deactivatedSemesterList() {
		String select = "Select id,is_active from semester where (is_active ='false')";
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

	public int deactivateSubject(Subject deactivateSubject) {
		// TODO Auto-generated method stub
		String select = "Select id,name,semester_id,department,is_active from subjects";
		List<Subject> subject = jdbcTemplate.query(select, new SubjectMapper());
		List<Subject> subject1 = subject.stream().filter(id -> id.getId() == (deactivateSubject.getId()))
				.collect(Collectors.toList());
		for (Subject subjectModel : subject1) {
			if (subjectModel != null) {
				String deactivate = "update subjects set is_active ='false' where id=?";
				Object[] params = { deactivateSubject.getId() };
				int noOfRows = jdbcTemplate.update(deactivate, params);
				System.out.println(noOfRows + " subjects are deactivated");
				return 1;
			}
		}
		return 0;
	}

	public int activateSubject(Subject activateSubject) {
		// TODO Auto-generated method stub
		String select = "Select id,name,semester_id,department,is_active from subjects";
		List<Subject> subject = jdbcTemplate.query(select, new SubjectMapper());
		List<Subject> subject1 = subject.stream().filter(id -> id.getId() == (activateSubject.getId()))
				.collect(Collectors.toList());
		for (Subject subjectModel : subject1) {
			if (subjectModel != null) {
				String activate = "update subjects set is_active ='true' where id=?";
				Object[] params = { activateSubject.getId() };
				int noOfRows = jdbcTemplate.update(activate, params);
				System.out.println(noOfRows + " subjects are activated");
				return 1;
			}
		}
		return 0;
	}
	public Subject findByID(Subject subject) {
		String find = "select id,name,semester_id,department,is_active from subjects where (is_active ='true' and id =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectMapper(),subject.getId());
		System.out.println(subjectNameList);
		return subjectNameList;
	}
	
	public Subject findSubjectNameByDepartment(Subject subject) {
		String find = "select name from subjects where (is_active ='true' and department =?)";
		Subject subjectNameList = jdbcTemplate.queryForObject(find, new SubjectNameMapper(),subject.getDepartment());
		System.out.println(subjectNameList);
		return subjectNameList;
	}

	public List<Subject> subjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active ='true')";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		System.out.println(subjectList);
		return subjectList;
	}

	public List<Subject> deactivatedsubjectList() {
		String select = "select id,name,semester_id,department,is_active from subjects where (is_active ='false')";
		List<Subject> subjectList = jdbcTemplate.query(select, new SubjectMapper());
		System.out.println(subjectList);
		return subjectList;
	}
	
	

	// --------- Exam methods ------------
	
	public int addExam(Exam exam) {
		String add = "insert into exam(id,subject_id,name,type) values(?,?,?,?)";
		Object[] params = { exam.getId(), exam.getSubjectId(),exam.getName(), exam.getType() };
		int noOfRows = jdbcTemplate.update(add, params);
		if (noOfRows > 0) {
			System.out.println(noOfRows + "Saved");
			return 1;
		} else
			return 0;
	}

}
