package com.example.Bright.College.Portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bright.College.Portal.dao.StaffDao;
import com.example.Bright.College.Portal.model.Department;
import com.example.Bright.College.Portal.model.User;

@Controller
public class StaffController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	StaffDao staffDao;

	// method to get student list
	@GetMapping(path = "/listofusers")
	public String getAllUser(Model model) {
		System.out.println("getting datas");
		List<User> users = staffDao.studentList();
		model.addAttribute("USER_LIST", users);
		return "listusers";
	}

	// method to approve student
	@GetMapping(path = "/approve")
	public void approve(@RequestParam("userID") int userID) {
		User user = new User();
		user.setUserId(userID);
		staffDao.approve(user);
	}

	// method to get department list
	@GetMapping(path = "/departmentList")
	public String departmentList(Model model) {
		model.addAttribute("departmentList", staffDao.departmentList());
		return "department";
	}

	// method to get inactiveDepartmentList list
	@GetMapping(path = "/inactiveDepartmentList")
	public String inactiveDepartmentList(Model model) {
		model.addAttribute("departmentList", staffDao.inactiveDepartmentList());
		return "department";
	}

	// method to add department
	@GetMapping(path = "/insertDepartment")
	public String addDepartment(@RequestParam("department") String department, Model model) {
		Department depart = new Department();
		depart.setDepartment(department);
		int value = staffDao.addDepartment(depart);
		if (value == 1) {
			model.addAttribute("departmentList", staffDao.departmentList());
			return "department";
		} else
			return "departmentForm";
	}

	// method to activate/DeactivateDepartment
	@GetMapping(path = "/activateOrDeactivateDepartment/{name}")
	public String activateOrDeactivateDepartment(@PathVariable(value = "name") String name, Model model) {
		Department department = new Department();
		department.setDepartment(name);
		int value = staffDao.activateOrDeactivateDepartment(department);
		if (value == 1) {
			model.addAttribute("departmentList", staffDao.departmentList());
			return "department";
		} else
			return "departmentForm";
	}
}
