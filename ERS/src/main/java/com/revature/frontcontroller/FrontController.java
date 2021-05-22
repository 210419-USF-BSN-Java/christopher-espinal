package com.revature.frontcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// PrintWriter pw = response.getWriter();
		// pw.write("<h1> path: " + path + " </h1>");

		// RequestDispatcher sends a request to the website but starts all over again
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
