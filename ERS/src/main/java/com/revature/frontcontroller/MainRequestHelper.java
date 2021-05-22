package com.revature.frontcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.maincontrollers.ManagerFrontController;
import com.revature.maincontrollers.UserFrontController;

public class MainRequestHelper {

	private MainRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// this should direct it to the appropriate place
		// will get a URI

		String path = request.getRequestURI().substring(request.getContextPath().length());
		PrintWriter pw = response.getWriter();
		// pw.write("<h1> Reached Main Request Helper </h1>");
		// user
		// session - if there's no session then move to register or login
		HttpSession session = request.getSession(false);

		/*
		 * if (path.startsWith("/user")) { // i want to redirect this login
		 * UserFrontController.process(request, response); } else if
		 * (session.getAttribute("role") == "employee") {
		 * pw.write("<h2> Reached Employee Dashboard </h2>"); } else if
		 * (session.getAttribute("role") == "manager") {
		 * pw.write("<h2> Reached Manager Dashboard </h2>"); }
		 */

		if (path.startsWith("/user")) {
			// i want to redirect this login
			UserFrontController.process(request, response);
		} else if (path.startsWith("/employee")) {
			pw.write("<h2> Reached Employee Dashboard </h2>");
		} else if (path.startsWith("/manager")) {
			ManagerFrontController.getInstance().process(request, response);
		}

	}
}
