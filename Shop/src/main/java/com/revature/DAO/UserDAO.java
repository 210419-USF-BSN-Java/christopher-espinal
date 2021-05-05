package com.revature.DAO;

import com.revature.models.User;

public interface UserDAO extends GenericDAO<User> {
	public User getByUsername(String user_name);

	public User getByEmail(String email);
}
