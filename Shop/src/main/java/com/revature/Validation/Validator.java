package com.revature.Validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.UserDAOImpl;

public class Validator {
	private static Logger log = LogManager.getRootLogger();

	private String message;
	private String pattern;

	public Validator(String message) {
		super();
		this.message = message;
	}

	public Validator(String message, String pattern) {
		super();
		this.message = message;
		this.pattern = pattern;
	}

	public void validate(String input) throws UserException {
		if (!input.matches(this.pattern)) {
			throw new UserException(this.message);
		} else {
			log.debug("The validation pattern was matched");
		}
	}

	/*
	 * public void validateUnique(String field_name, String value) throws
	 * UserException { UserDAO uniqueChecker = new UserDAOImpl();
	 * 
	 * // could generalize this portion to other things boolean unique =
	 * uniqueChecker.checkAvailable(field_name, value); if (unique) { throw new
	 * UserException(this.message); } else { log.debug("The input is unique!"); } }
	 */

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
