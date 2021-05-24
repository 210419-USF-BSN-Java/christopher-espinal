package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;

public class FrontController extends DefaultServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get the uri and figure out where it's going
		// context path would be
		String path = request.getRequestURI().substring(request.getContextPath().length());
		HttpSession session = request.getSession(false);
		// System.out.println("User Session ID: " + session.getAttribute("role"));

		if (path.startsWith("/static/") || path.equals("") || path.equals("/") || path.equals("/index.html")) {
			// pw.write("<h1> In the static repo section </h1>");
			super.doGet(request, response);
		} else {
			MainRequestHelper.process(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
