package com.example.Bright.College.Portal.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.example.Bright.College.Portal.model.User;
import com.example.Bright.College.Portal.validation.Validation;
import com.example.Bright.College.Portal.mapper.UserMapper;
import com.example.Bright.College.Portal.mapper.LoginMapper;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int save(User saveUser) {
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
			String sql = "insert into user(first_name,last_name,DOB,gender,phone_number,Email,Password,roll) values(?,?,?,?,?,?,?,?)";
			Object[] params = { saveUser.getFirstName(), saveUser.getLastName(), saveUser.getDOB(),
					saveUser.getGender(), saveUser.getPhone(), saveUser.getEmail(), saveUser.getPassword(),
					saveUser.getRoll() };

			boolean emailval = val.emailValidation(saveUser.getEmail());
			boolean phoneval = val.phoneNumberValidation(saveUser.getPhone());
			boolean firstNameVal = val.nameValidation(saveUser.getFirstName());
			if (emailval == true && phoneval == true && firstNameVal == true) {

				int noOfRows = jdbcTemplate.update(sql, params);
				System.out.println(noOfRows + "Saved");
				return 1;
			} else {
				System.out.println("Incorrect value");
				return 0;
			}
		}

	}

	public int login(User loginUser) {
		Validation val = new Validation();
		List<User> listUsers = listUsers();

		String userList = listUsers.toString();
		String email = loginUser.getEmail();

		String password = loginUser.getPassword();

		String login = "Select Email,Password,roll from user";
		List<User> userLogin = jdbcTemplate.query(login, new LoginMapper());

		List<User> user1 = userLogin.stream().filter(email1 -> email1.getEmail().equals(email))
				.filter(password1 -> password1.getPassword().equals(password))
				.filter(roll1 -> roll1.getRoll().equals("student")).collect(Collectors.toList());
		List<User> user2 = userLogin.stream().filter(email2 -> email2.getEmail().equals(email))
				.filter(password2 -> password2.getPassword().equals(password))
				.filter(roll2 -> roll2.getRoll().equals("staff")).collect(Collectors.toList());
		
		for (User userModel1 : user1) {
			if (userModel1 != null) {
				return 1;
			}

		}
		for (User userModel2 : user2) {
			if (userModel2 != null) {
				return 2;
			}

		}
		return 0;
	}

	public List<User> listUsers() {
		String sql = "select * from user";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}
}
