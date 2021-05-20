package com.revature.maincontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.ManagerServices;
import com.revature.services.ManagerServicesImpl;

public class ManagerControllerImpl extends HttpServlet implements ManagerController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController mc = new ManagerControllerImpl();
//	private static ManagerServices ms = new ManagerServicesImpl();

	private ManagerControllerImpl() {
	}

	public static ManagerController getInstance() {
		return mc;
	}

	@Override
	public void getDashboard(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewPendingReims(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewReceipts(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewEmployeeReims(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
//		List<User> employees = ms.viewAllEmployees();
		// convert to JSON
		// just to test the front end:
		List<User> employees = mockEmployees();
		if (employees == null) {
			response.setStatus(404);
		} else {
			response.setStatus(200);
			ObjectMapper mapper = new ObjectMapper();
			String employeesJSON = mapper.writeValueAsString(mapper);
			response.getWriter().write(employeesJSON);
		}
	}

	private List<User> mockEmployees() {
		// TODO Auto-generated method stub
		List<User> employees = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
			User employee = new User();
			employee.setEmail("employee" + i + "@company.com");
			employee.setUsername("employee" + i);
			employee.setFirst_name("FName" + i);
			employee.setLast_name("LName" + i);
			employee.setRole(Role.EMPLOYEE);
			employees.add(employee);
		}

		return employees;
	}

}
