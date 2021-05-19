package com.revature.services;

import java.util.List;

import com.revature.dao.DaoFactory;
import com.revature.dao.UserDao;
import com.revature.models.User;

public class UserServicesImpl implements UserServices {
	private static UserDao udao = DaoFactory.getDaoFactory().getUserDaoImpl();

	@Override
	public User add(User t) {
		// TODO Auto-generated method stub
		return udao.add(t);
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loginByEmail(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loginByUsername(String username, String password) {
		// TODO Auto-generated method stub
		User user = udao.getByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		
		return null;
	}

	@Override
	public User updateInformation(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
