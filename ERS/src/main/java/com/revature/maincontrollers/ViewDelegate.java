package com.revature.maincontrollers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDelegate extends HttpServlet {
	private static ViewDelegate vd = new ViewDelegate();

	public ViewDelegate() {
	}

	public static ViewDelegate getInstance() {
		return vd;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void processView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		pw.write("<h2> Current path: " + path + "</h2>");
		if (path.startsWith("/user/login")) {
			request.getRequestDispatcher("/static/login.html").forward(request, response);
			return;
		} else if (path.startsWith("/manager")) {
			request.getRequestDispatcher("/static/managerOld.html").forward(request, response);
			return;
		} else if (path.startsWith("/employee")) {
			request.getRequestDispatcher("/static/employeeDB.html").forward(request, response);
			return;
		}

	}

}
