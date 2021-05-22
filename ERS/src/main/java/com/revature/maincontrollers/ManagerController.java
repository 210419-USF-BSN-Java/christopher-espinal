package com.revature.maincontrollers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ManagerController {
	// dashboard
	public void getDashboard(HttpServletRequest request, HttpServletResponse response);

	public void viewPendingReims(HttpServletRequest request, HttpServletResponse response);

	public void viewResolvedReims(HttpServletRequest request, HttpServletResponse response);

	public void viewReceipts(HttpServletRequest request, HttpServletResponse response);

	public void viewEmployeeReims(HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void viewEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
