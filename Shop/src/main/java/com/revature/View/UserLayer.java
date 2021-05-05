package com.revature.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.ServicesImpl.UserServicesImpl;
import com.revature.models.Group;
import com.revature.models.User;

public class UserLayer {
	private static UserServicesImpl uService = new UserServicesImpl();

	public static Logger log = LogManager.getRootLogger();

	public User userCreationPrompt(Scanner sc) {
		User user = null;
		boolean run = true;
		do {
			Map<String, Object> userCreationArgs = new HashMap<>();

			log.info("Make a user profile.");
			log.info("\nType in a username: \n");
			String username = sc.nextLine();
			userCreationArgs.put("username", username);

			log.info("\nType in a password: \n");
			String password = sc.nextLine();
			userCreationArgs.put("password", password);

			log.info("\nType in an email: \n");
			String email = sc.nextLine();
			userCreationArgs.put("email", email);

			// the group can be chosen later
			String group = sc.nextLine();
			userCreationArgs.put("group", new Group(1, "customer"));

			user = uService.create(userCreationArgs);
			run = (user == null) ? true : false;
		} while (run);

		return user;
	}

	public User userLoginPrompt(Scanner sc) {
		User user = new User();
		boolean run = true;
		do {
			log.info("Log in.");
			log.info("\nType in a username: \n");
			String username = sc.nextLine();
			user.setUsername(username);

			log.info("\nType in a password: \n");
			String password = sc.nextLine();
			user.setPassword(password);

			user = uService.login(user);
			log.debug("After logging in: " + user.getGroup());
			run = (user.getUser_id() <= 0) ? true : false;
		} while (run);

		return user;
	}
}
