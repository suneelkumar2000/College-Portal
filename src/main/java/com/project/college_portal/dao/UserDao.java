package com.project.college_portal.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.project.college_portal.mapper.ForgotPasswordMapper;
import com.project.college_portal.mapper.LoginMapper;
import com.project.college_portal.mapper.UserMapper;
import com.project.college_portal.model.User;
import com.project.college_portal.validation.Validation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int save(User saveUser) {
		String password = saveUser.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);

		Validation val = new Validation();
		List<User> listUsers = listUsers();
		System.out.println(listUsers);

		String userList = listUsers.toString();
		String email = saveUser.getEmail();
		boolean emailContains = userList.contains(email);
		System.out.println(emailContains);

		if (emailContains == true) {
			System.out.println("Email Already Exist");
			return 0;
		} else {
			String sql = "insert into user(first_name,last_name,dob,gender,phone_number,email,Password,roll) values(?,?,?,?,?,?,?,?)";
			Object[] params = { saveUser.getFirstName(), saveUser.getLastName(), saveUser.getDOB(),
					saveUser.getGender(), saveUser.getPhone(), email, encodedPassword, saveUser.getRoll() };

			boolean emailval = val.emailValidation(email);
			boolean phoneval = val.phoneNumberValidation(saveUser.getPhone());
			boolean firstNameVal = val.nameValidation(saveUser.getFirstName());
			boolean adminval = val.adminEmailValidation(email);
			if (emailval == true && phoneval == true && firstNameVal == true) {
				int noOfRows = jdbcTemplate.update(sql, params);
				if (adminval == true) {
					String approve = "update user set status ='approved'  where email=?";
					Object[] params1 = { email };
					int noOfRows1 = jdbcTemplate.update(approve, params1);
					System.out.println(noOfRows1 + " Hod Saved");
					return 1;
				}
				System.out.println(noOfRows + "Saved");
				return 1;
			} else {
				System.out.println("Incorrect value");
				return 0;
			}
		}

	}

	public int login(User loginUser) {
		String email = loginUser.getEmail();

		String password = loginUser.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String login = "Select Email,Password,roll from user";
		List<User> userLogin = jdbcTemplate.query(login, new LoginMapper());

		List<User> user1 = userLogin.stream().filter(email1 -> email1.getEmail().equals(email))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());

		List<User> user2 = userLogin.stream().filter(email2 -> email2.getEmail().equals(email))
				.filter(roll2 -> roll2.getRoll().equals("staff")).collect(Collectors.toList());

		for (User userModel1 : user1) {
			if (userModel1 != null) {
				String dbpass = userModel1.getPassword();
				boolean match = encoder.matches(password, dbpass);
				if (match)
					return 1;
			}

		}
		for (User userModel2 : user2) {
			if (userModel2 != null) {
				String dbpass = userModel2.getPassword();
				boolean match = encoder.matches(password, dbpass);
				if (match)
					return 2;
			}

		}
		return 0;
	}

	public List<User> listUsers() {
		String sql = "select id,first_name,last_name,dob,gender,phone_number,email,password,roll,department,parent_name,status,is_active from user";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}

	public int forgotPassword(User user) {
		// TODO Auto-generated method stub
		String email = user.getEmail();
		long phone = user.getPhone();
		String password = user.getPassword();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(password);

		String select = "Select Email,Password,phone_number,roll from user";
		List<User> userLogin = jdbcTemplate.query(select, new ForgotPasswordMapper());

		List<User> user1 = userLogin.stream().filter(email1 -> email1.getEmail().equals(email))
				.filter(phone1 -> phone1.getPhone().equals(phone)).filter(roll1 -> roll1.getRoll().equals("student"))
				.collect(Collectors.toList());

		List<User> user2 = userLogin.stream().filter(email2 -> email2.getEmail().equals(email))
				.filter(phone2 -> phone2.getPhone().equals(phone)).filter(roll2 -> roll2.getRoll().equals("staff"))
				.collect(Collectors.toList());

		for (User userModel1 : user1) {
			if (userModel1 != null) {
				String changePassword = "update user set Password =?  where Email=?";
				Object[] params = { encodePassword, email };
				int noOfRows = jdbcTemplate.update(changePassword, params);
				System.out.println(noOfRows + "Saved");
				return 1;
			}
		}
		for (User userModel2 : user2) {
			if (userModel2 != null) {
				String changePassword = "update user set Password =?  where email=?";
				Object[] params = { encodePassword, email };
				int noOfRows = jdbcTemplate.update(changePassword, params);
				System.out.println(noOfRows + "Saved");
				return 2;
			}

		}
		return 0;
	}
}
