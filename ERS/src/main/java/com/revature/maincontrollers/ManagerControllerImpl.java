package com.revature.maincontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.services.ManagerServices;
import com.revature.services.ManagerServicesImpl;
import com.revature.services.ReimServices;
import com.revature.services.ReimServicesImpl;
import com.revature.services.UserServices;
import com.revature.services.UserServicesImpl;

public class ManagerControllerImpl extends HttpServlet implements ManagerController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController mc = new ManagerControllerImpl();
	private static ManagerServices ms = new ManagerServicesImpl();
	private static UserServices us = new UserServicesImpl();
	private static ObjectMapper om = new ObjectMapper();
	private static ReimServices rm = new ReimServicesImpl();

	private ManagerControllerImpl() {
	}

	public static ManagerController getInstance() {
		return mc;
	}

	@Override
	public void viewPendingReims(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<Reimbursement> reims = ms.viewPendingReims();
		if (reims == null) {
			// pw.write("nada");
			response.setStatus(404);
		} else {
			String json = om.writeValueAsString(reims);
			response.setStatus(200);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(json);
		}

	}

	@Override
	public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String path = request.getRequestURI().substring((request.getContextPath() + "/manager/resolvedReims").length());
		// PrintWriter pw = response.getWriter();
		// pw.write("\nView employee reims method, passed id: " + employeeId + "\n");

		List<Reimbursement> reims = ms.viewResolvedReims();
		if (reims == null) {
			// pw.write("nada");
			response.setStatus(404);
		} else {
			String json = om.writeValueAsString(reims);
			response.setStatus(200);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(json);
		}

	}

	@Override
	public void viewReceipts(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewEmployeeReims(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		String path = request.getRequestURI().substring((request.getContextPath() + "/manager/employeeReims").length());
		// PrintWriter pw = response.getWriter();
		// since the user doesn't explicitly choose the id value, this shouldn't matter
		Integer employeeId = Integer.parseInt(path.substring(1)); // must remove the forward slash
		// pw.write("\nView employee reims method, passed id: " + employeeId + "\n");
		User employee = us.getById(employeeId);

		List<Reimbursement> employeeReims = ms.viewEmployeeReims(employee);
		if (employeeReims == null) {
			// pw.write("nada");
			response.setStatus(404);
		} else {
			String json = om.writeValueAsString(employeeReims);
			response.setStatus(200);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(json);
		}

	}

	@Override
	public void viewEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// List<User> employees = ms.viewAllEmployees();
		// convert to JSON
		// just to test the front end:
		List<User> employees = ms.viewAllEmployees();
		if (employees == null) {
			response.setStatus(404);
		} else {
			response.setStatus(200);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(employees);
			// response.setHeader("employees", employeesJSON);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(json);

		}
	}

	@Override
	public void acceptReim(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		String path = request.getRequestURI().substring((request.getContextPath() + "/manager/acceptReim").length());
		// PrintWriter pw = response.getWriter();
		// since the user doesn't explicitly choose the id value, this shouldn't matter
		Integer reimId = Integer.parseInt(path.substring(1)); // must remove the forward slash
		// pw.write("\nView employee reims method, passed id: " + employeeId + "\n");
		System.out.println(reimId);
		Reimbursement reim = rm.getById(reimId);
		// need to get the manager's details
		// String token = request.getHeader("Authentication");
		// String[] userData = token.split(":");
		// Integer managerId = Integer.parseInt(userData[0]);
		// User manager = us.getById(managerId);
		User manager = us.getById(2);

		int affected = ms.manageReim(reim, manager, Status.APPROVED);

		if (affected > 0) {
			// pw.write("nada");
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}

	}

	@Override
	public void rejectReim(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		String path = request.getRequestURI().substring((request.getContextPath() + "/manager/rejectReim").length());
		// PrintWriter pw = response.getWriter();
		// since the user doesn't explicitly choose the id value, this shouldn't matter
		Integer reimId = Integer.parseInt(path.substring(1)); // must remove the forward slash
		// pw.write("\nView employee reims method, passed id: " + employeeId + "\n");
		System.out.println(reimId);
		Reimbursement reim = rm.getById(reimId);
		// need to get the manager's details
		// String token = request.getHeader("Authentication");
		// String[] userData = token.split(":");

		// !!!!HARD CODE!!!!
		// Integer managerId = Integer.parseInt(userData[0]);
		// User manager = us.getById(managerId);
		User manager = us.getById(2);

		int affected = ms.manageReim(reim, manager, Status.DENIED);

		if (affected > 0) {
			// pw.write("nada");
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}

	}

	private List<User> mockEmployees() {
		// TODO Auto-generated method stub
		List<User> employees = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			User employee = new User();
			employee.setId(i);
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
