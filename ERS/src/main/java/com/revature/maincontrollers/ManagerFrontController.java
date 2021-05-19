package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerFrontController {
	private static ManagerFrontController mfc = new ManagerFrontController();
	
	private ManagerFrontController() {
	}

	public static ManagerFrontController getInstance() {
		return mfc;
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// all of the user requests will go through here
		String path = request.getRequestURI().substring((request.getContextPath() + "/manager").length());
		PrintWriter pw = response.getWriter();
		pw.write("<h1> Reached Manager Front Controller </h1>");
		pw.write("<h2> Context Path: " + path + " </h2>");
		pw.write("<h2> Context Path: " + request.getContextPath() + " </h2>");

		// should then send to the user request helper
		// perhaps can include a if check statement to make other checks
		if (path.equals("/")) {
			pw.write("<h2> Going to Manager Dashboard </h2>");
			ViewDelegate.getInstance().processView(request, response);
		} else {
			ManagerRequestHelper.process(request, response);
		}
	}

}
