package com.revature.main;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.View.CustomerLayer;
import com.revature.View.EmployeeLayer;
import com.revature.View.UserLayer;
import com.revature.models.Group;
import com.revature.models.User;

public class Main {
	public static Logger log = LogManager.getRootLogger();
	public static Scanner sc = new Scanner(System.in);
	public static User user;
	public static Group customer = new Group(1, "customer");
	public static Group employee = new Group(3, "employee");

	public static void main(String[] args) {
		boolean run = true;
		log.info("Welcome to the NYC Grocery Store\n");
		do {
			// TODO Auto-generated method stub
			UserLayer userPrompt = new UserLayer();

			if (user == null) {
				log.info("NYC Grocery Store HomePage\n");
				log.info("Choose an option:");
				log.info("		Press 1 to Create an Account");
				log.info("		Press 2 to Login to an Account");
				log.info("		Press anything else to exit");
				String choice = sc.nextLine();
				switch (choice) {
				case "1":
					userPrompt.userCreationPrompt(sc);
					break;
				case "2":
					user = userPrompt.userLoginPrompt(sc);
					// log.debug(user.getGroup().equals(employee));
					break;
				default:
					log.info("\nGoodbye from NYC Grocery Store!\n");
					run = false;
					break;
				}
				// not a secure way to do this
			} else if (user.getGroup().equals(customer)) {
				CustomerLayer customerPrompt = new CustomerLayer();
				customerPrompt.mainCustomerDashboard(user, customer, sc);
			} else if (user.getGroup().equals(employee)) {
				EmployeeLayer employeePrompt = new EmployeeLayer();
				employeePrompt.mainEmployeeDashboard(user, employee, sc);
			}

			log.info("Choose an option:");
			log.info("		Press 1 to Continue");
			log.info("		Press anything else to exit");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				break;
			default:
				log.info("\nGoodbye from NYC Grocery Store!\n");
				run = false;
				break;
			}

		} while (run);
		log.debug("Exited Program");
	}

}
