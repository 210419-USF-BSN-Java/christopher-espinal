package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerRequestHelper {
	private ManagerRequestHelper() {
	}

	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// this should direct it to the appropriate place
		// will get a URI

		String path = request.getRequestURI().substring((request.getContextPath() + "/manager").length());
		PrintWriter pw = response.getWriter();
		pw.write("<h1> Reached Manager Helper </h1>");
		pw.write("<h2> Context Path: " + path + " </h2>");
		pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");
		// user
		// session - if there's no session then move to register or login

		if (path.startsWith("/logout")) {
			pw.write("<h2> Going to Logout Page </h2>");
			UserControllerImpl.getInstance().logout(request, response);

		} else if (path.startsWith("/register")) {
			pw.write("<h2> Going to Register Page </h2>");
			UserControllerImpl.getInstance().register(request, response);

		}

	}

}
