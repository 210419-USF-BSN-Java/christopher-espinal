package com.revature.maincontrollers;

import javax.servlet.http.HttpServletRequest;

public class ManagerControllerImpl implements ManagerController {
	private static ManagerController mc = new ManagerControllerImpl();

	private ManagerControllerImpl() {
	}

	public static ManagerController getInstance() {
		return mc;
	}

	@Override
	public void getDashboard(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void manageReims(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewPendingReims(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewResolvedReims(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewReceipts(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void manageEmployees(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewEmployeeReims(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
