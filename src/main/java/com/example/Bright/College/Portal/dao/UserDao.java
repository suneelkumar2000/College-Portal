package com.example.Bright.College.Portal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.Bright.College.Portal.model.User;
import com.example.Bright.College.Portal.validation.Validation;
import com.example.Bright.College.Portal.mapper.UserMapper;
import com.example.Bright.College.Portal.mapper.PasswordMapper;

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
			String sql = "insert into user(User_ID,first_name,last_name,DOB,gender,phone_number,Email,Password,roll) values(?,?,?,?,?,?,?,?,?)";
			Object[] params = { saveUser.getUserId(), saveUser.getFirstName(), saveUser.getLastName(),
					saveUser.getDOB(), saveUser.getGender(), saveUser.getPhone(), saveUser.getEmail(),
					saveUser.getPassword(), saveUser.getRoll() };

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

	public int login(User saveUser) {
		Validation val = new Validation();
		List<User> listUsers = listUsers();

		String userList = listUsers.toString();
		String email = saveUser.getEmail();
		boolean emailContains = userList.contains(email);
		System.out.println(emailContains);
		boolean emailval = val.emailValidation(saveUser.getEmail());
		if (emailval == true) {
			if (emailContains == true) {
				String sql = "select Password,roll from user where Email=?";
				User user = jdbcTemplate.queryForObject(sql, new PasswordMapper(), saveUser.getEmail());
				String password=user.getPassword();
				String roll=user.getRoll();
				System.out.println(password);
				System.out.println(roll);
				if (password.equals(saveUser.getPassword())) {
					if (roll.equals("student")){
						return 1;
					} else if (roll.equals("staff")) {
						return 2;
					} else
						return 0;
				} else
					return 0;
			} else
				return 0;
		} else
			return 0;
	}

	public List<User> listUsers() {
		String sql = "select * from user";
		List<User> userList = jdbcTemplate.query(sql, new UserMapper());
		System.out.println(userList);
		return userList;
	}
}
