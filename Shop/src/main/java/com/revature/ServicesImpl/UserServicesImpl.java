package com.revature.ServicesImpl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.UserDAOImpl;
import com.revature.models.Group;
import com.revature.models.User;

public class UserServicesImpl {
	public static UserDAO userDao = new UserDAOImpl();
	public static Logger log = LogManager.getRootLogger();

	public User create(Map<String, Object> createUserArgs) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername((String) createUserArgs.get("username"));
		user.setPassword((String) createUserArgs.get("password"));
		user.setEmail((String) createUserArgs.get("email"));
		user.setGroup((Group) createUserArgs.get("group"));
		log.debug(user + " " + user.getGroup());
		
		if (userDao.getByEmail(user.getEmail()) != null) {
			log.info("Sorry this email was already taken!");
			return user;
		} else if (userDao.getByUsername(user.getUsername()) != null) {
			log.info("Sorry, this username was already taken!");
			return user;
		}
		
		user = userDao.create(user);
		return user;
	}

	public Integer update(User user) {
		User userCopy = new User();
		// create a copy of a user
		// verify that the new details aren't new

		return userDao.update(user);
	}

	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

	public Integer delete(User t) {
		// TODO Auto-generated method stub
		return userDao.delete(t);
	}

	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean check(User user) {
		// TODO Auto-generated method stub
		if (userDao.getByEmail(user.getEmail()) != null) {
			log.info("Sorry this email was already taken!");
			return false;
		} else if (userDao.getByUsername(user.getUsername()) != null) {
			log.info("Sorry, this username was already taken!");
			return false;
		} else {
			log.info("Success!");
			return true;
		}
	}

	public Boolean checkUsername(String username) {
		// TODO Auto-generated method stub

		if (userDao.getByUsername(username) != null) {
			log.info("Sorry, this username was already taken!");
			return false;
		} else {
			log.info("Success!");
			return true;
		}
	}

	public Boolean checkEmail(String email) {
		// TODO Auto-generated method stub

		if (userDao.getByEmail(email) != null) {
			log.info("Sorry, this email was already taken!");
			return false;
		} else {
			log.info("Success!");
			return true;
		}
	}

	// still need to check the int value
	public User login(User user) {
		// TODO Auto-generated method stub
		User tempUser = userDao.getByEmail(user.getEmail());
		if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) {
			log.info("\nSuccessfully Logged In!\n");
			return tempUser;
		}

		tempUser = userDao.getByUsername(user.getUsername());
		if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) {
			log.info("\nSuccessfully Logged In!\n");
			return tempUser;
		}

		log.info("\nYou haven't successfully login! Please check your username or password!\n");
		return user;
	}

}
