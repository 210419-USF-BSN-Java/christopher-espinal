package com.revature.DAOImplTests;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.DAO.ItemDAO;
import com.revature.DAO.OfferDAO;
import com.revature.DAO.StatusDAO;
import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.ItemDAOImpl;
import com.revature.DAOImpl.OfferDAOImpl;
import com.revature.DAOImpl.StatusDAOImpl;
import com.revature.DAOImpl.UserDAOImpl;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Status;
import com.revature.models.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OfferDAOImplTest {
	private static Logger log = LogManager.getRootLogger();
	private static OfferDAO dao;
	private static Offer offer;
	private static ItemDAO itemDao;
	private static Item item;
	private static StatusDAO statusDao;
	private static Status status;
	private static UserDAO userDao;
	private static User user;

	@BeforeClass
	public static void setupBeforeClass() {
		dao = new OfferDAOImpl();
		offer = new Offer();
		itemDao = new ItemDAOImpl();
		item = itemDao.getById(1);
		offer.setItem(item);
		offer.setQuantity(2);
		offer.setOffer_price(3.00);
		userDao = new UserDAOImpl();
		user = userDao.getById(42);
		offer.setUser(user);
		statusDao = new StatusDAOImpl();
		status = statusDao.getById(1);
		offer.setStatus(status);
		offer.setOwnership(false);
		offer.setInstallments(1);
	}

	@AfterClass
	public static void afterTest() {
		dao = null;
		itemDao = null;
		userDao = null;
		statusDao = null;
	}

	// create
	@Test
	public void aTest() {
		log.debug("1 create test = before: " + offer.toString());
		offer = dao.create(offer);
		assertTrue(offer.getOffer_id() > 0);
		log.debug("1 create test = after: " + offer.getOffer_id());
	}

	// getById
	@Test
	public void bTest() {
		log.debug("2 getById test = before: " + offer.toString());
		Offer retrievedOffer = dao.getById(offer.getOffer_id());
		log.debug("2 getById test = retrieved: " + retrievedOffer.toString());
		assertEquals(retrievedOffer, offer);
	}

	// update
	@Test
	public void cTest() {
		log.debug("3 update test = before: " + offer.toString());
		offer.setQuantity(20);
		int affected = dao.update(offer);
		assertTrue(affected > 0);
		log.debug("3 update test = after: " + offer.toString());
	}

	// delete
	@Test
	public void dTest() {
		log.debug("4 delete test = before: " + offer.getOffer_id());
		int affected = dao.delete(offer);
		assertTrue(affected > 0);
		log.debug("4 delete test = after - affected?: " + affected);
	}

}
