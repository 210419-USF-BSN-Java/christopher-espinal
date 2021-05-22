package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.User;
import com.revature.services.UserServices;
import com.revature.services.UserServicesImpl;

public class UserControllerImpl implements UserController {
	private static UserController uc = new UserControllerImpl();
	private static ViewDelegate vd = new ViewDelegate();
	private static UserServices userService = new UserServicesImpl();

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
//			pw.write("<h1> Logging you in! </h1>");

			// for now login through get but change it later
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			pw.write("<h1> username: " + username + " </h1>");

			User user = userService.loginByUsername(username, password);

			if (user != null && user.getPassword().equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("role", user.getRole().rname());
				response.setStatus(200);
				// need to send a response to the client

			} else {
//				pw.write("<h1> Sorry, but we couldn't log you in </h1>");
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
		// TODO Auto-generated method stub
		String method = request.getMethod();
		PrintWriter pw = response.getWriter();

		switch (method) {
		case "GET":
//			pw.write("<h2> Logging out </h2>");
			HttpSession session = request.getSession(false);
			session.invalidate();
//			pw.write("<h2> Logged out</h2>");
//			pw.write("<h2> Here's evidence they logged in. </h2>");
//			pw.write("<h3> Session creation time: " + session.getCreationTime() + " </h3>");
			// don't want to forward until set up POST method in the html page
//			RequestDispatcher rd = request.getRequestDispatcher("");
//			rd.include(request, response);
			break;

		case "POST":
			break;
		}

	}

	@Override
	public void register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void seeAccount(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
