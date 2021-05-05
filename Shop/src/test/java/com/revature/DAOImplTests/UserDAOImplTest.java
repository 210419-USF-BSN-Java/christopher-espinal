package com.revature.DAOImplTests;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.UserDAOImpl;
import com.revature.models.Group;
import com.revature.models.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOImplTest {
	private static Logger log = LogManager.getRootLogger();
	private static UserDAO dao;
	private static Group group;
	private static User user;

	@BeforeClass
	public static void classSetUp() {
		dao = new UserDAOImpl();
		group = new Group(1, "customer");
		user = new User();
		user.setUsername("firstname lastname");
		user.setPassword("12341234");
		user.setEmail("email@co.com");
		user.setGroup(group);

	}

	@Test
	public void aTest() {
		user = dao.create(user);
		assertTrue(user.getUser_id() > 0);
		log.debug("create user test: " + user);
	}

	@Test
	public void bTest() {
		log.debug("get user test: " + user.getUser_id());
		User retrievedUser = dao.getById(user.getUser_id());
		retrievedUser.getUser_id();
		assertEquals(user, retrievedUser);
	}

	@Test
	public void cTest() {
		user.setPassword("23452345");
		log.debug("Update user test: " + user.getPassword());
		int affected = dao.update(user);
		assertTrue(affected > 0);
	}

	@Test
	public void dTest() {
		log.debug("delete user test: " + user.getUser_id());
		int affected = dao.delete(user);
		log.debug("delete user test: " + affected);
		assertTrue(affected > 0);
	}

	@AfterClass
	public static void breakDown() {
		dao = null;
	}
}
