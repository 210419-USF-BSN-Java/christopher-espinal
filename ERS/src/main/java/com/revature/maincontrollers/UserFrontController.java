package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFrontController {
	private UserFrontController() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// all of the user requests will go through here
		String path = request.getRequestURI().substring((request.getContextPath() + "/user").length());
		// PrintWriter pw = response.getWriter();
		// pw.write("<h1> Reached User Front Controller </h1>");
		// pw.write("<h2> Context Path: " + path + " </h2>");
		// pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");

		if (path.length() == -1) {
			// pw.write("<p> Send back a page where they could log in with buttons </p>");
		} else {
			// pw.write("<p> UserRequestHelper </p>");
			UserRequestHelper.process(request, response);
		}
	}
}
