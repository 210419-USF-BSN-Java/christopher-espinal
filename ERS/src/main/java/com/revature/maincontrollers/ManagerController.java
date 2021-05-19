package com.revature.maincontrollers;

import javax.servlet.http.HttpServletRequest;

public interface ManagerController {
	// dashboard
	public void getDashboard(HttpServletRequest request);

	public void manageReims(HttpServletRequest request);

	public void viewPendingReims(HttpServletRequest request);

	public void viewResolvedReims(HttpServletRequest request);

	public void viewReceipts(HttpServletRequest request);

	public void manageEmployees(HttpServletRequest request);

	public void viewEmployeeReims(HttpServletRequest request);

}
