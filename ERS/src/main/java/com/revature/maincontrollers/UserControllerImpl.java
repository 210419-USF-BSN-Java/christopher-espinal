package com.revature.maincontrollers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.frontcontroller.FrontController;
import com.revature.models.User;
import com.revature.models.UserLogger;
import com.revature.services.UserServices;
import com.revature.services.UserServicesImpl;

public class UserControllerImpl implements UserController {
	private static UserController uc = new UserControllerImpl();
	private static ViewDelegate vd = new ViewDelegate();
	private static UserServices us = new UserServicesImpl();
	private static ObjectMapper om = new ObjectMapper();

	private UserControllerImpl() {
	}

	public static UserController getInstance() {
		return uc;
	}

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method = request.getMethod();
		PrintWriter pw = response.getWriter();

		switch (method) {
			case "GET":
				vd.processView(request, response);
				break;

			case "POST":
				// pw.write("<h1> Logging you in! </h1>");
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = request.getReader();
				try {
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line).append('\n');
					}
				} finally {
					reader.close();
				}

				System.out.println("StringBuilder data: " + sb);
				ObjectMapper om = new ObjectMapper();
				UserLogger ul = om.readValue(sb.toString(), UserLogger.class);
				// HARDCODED
				User user = us.loginByUsername(ul.getUsername(), ul.getPassword());
				System.out.println(user);

				if (user != null) {
					HttpSession session = request.getSession();
					session.setAttribute("role", user.getRole().toString());
					System.out.println(session.getCreationTime());

					response.setStatus(200);
				} else {
					// pw.write("<h1> Sorry, but we couldn't log you in </h1>");
					response.setStatus(401);
				}
				break;
			case "DELETE":
				break;
			default:
				break;
		}

	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// // TODO Auto-generated method stub
		// String method = request.getMethod();
		// PrintWriter pw = response.getWriter();
		System.out.println("Logout - Received request");
		if (request.getMethod().equals("POST")) {
			try {
				HttpSession session = request.getSession(false);
				session.invalidate();
				response.setStatus(200);
			} catch (NullPointerException e) {
				System.out.println("logout - no session");
			} finally {
				response.setStatus(200);
			}
		}
	}

	@Override
	public void register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void seeAccount(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String path = request.getRequestURI().substring((request.getContextPath() + "/user/account/").length());
		System.out.println(path);
		Integer id = Integer.parseInt(path);
		User user = us.getById(id);
		if (user == null) {
			response.setStatus(404);
		} else {
			List<User> fakeArray = new ArrayList<>(); // not for iteration. Just for compatibility with JS Function
														// which expects a list
			fakeArray.add(user);
			String json = om.writeValueAsString(fakeArray);
			response.getWriter().write(json);
		}

	}

	@Override
	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}

		// HARDCODED
		User employee = us.getById(1);

		User userJSON = om.readValue(sb.toString(), User.class);

		String username = userJSON.getUsername();
		String password = userJSON.getPassword();
		String email = userJSON.getEmail();

		if (!username.equals("") && (username != null)) {
			employee.setUsername(username);
		}

		if (!email.equals("") && (email != null)) {
			employee.setEmail(email);
		}

		if (!password.equals("") && (password != null)) {
			employee.setPassword(password);
		}

		int successful = us.update(employee);

		if (successful > 0) {
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}

	}

}
