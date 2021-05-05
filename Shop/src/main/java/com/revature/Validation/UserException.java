package com.revature.Validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserException extends Exception {
	private static Logger log = LogManager.getRootLogger();

	private String message;

	public UserException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
