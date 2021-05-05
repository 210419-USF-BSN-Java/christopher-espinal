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
import com.revature.DAOImpl.ItemDAOImpl;
import com.revature.models.Item;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemDAOImplTest {
	private static Logger log = LogManager.getRootLogger();
	private static Item item;
	private static ItemDAO dao;

	@BeforeClass
	public static void setUpClass() {
		item = new Item();
		item.setItem_name("test item 1");
		item.setItem_price(5.00);
		dao = new ItemDAOImpl();
	}

	@AfterClass
	public static void breakDown() {
		dao = null;
	}

	/*
	 * @Before public void beforeEach() { log.debug("Item state = " +
	 * item.toString()); }
	 * 
	 * @After public void afterEach() { log.debug("After Item State = " +
	 * item.toString()); }
	 */

	// create
	@Test
	public void aTest() {
		log.debug("1 create test = " + item.toString());
		item = dao.create(item);
		assertTrue(item.getItem_id() > 0);
		log.debug("2 create test = " + item.toString());

	}

	// getById
	@Test
	public void bTest() {
		log.debug("2 getById test = " + item.toString());
		Item retrievedItem = dao.getById(item.getItem_id());
		assertEquals(item, retrievedItem);
		log.debug("2 getById test = retrieved: " + retrievedItem.toString());
	}

	// update
	@Test
	public void cTest() {
		log.debug("3 update test = " + item.toString());
		item.setItem_name("test item 1 - update");
		assertTrue(dao.update(item) > 0);
		log.debug("3 update test = after: " + item.toString());
	}

	// delete
	@Test
	public void dTest() {
		int affected = dao.delete(item);
		log.debug("4 delete test = " + item.toString());
		assertTrue(affected > 0);
		log.debug("4 delete test = " + affected);
	}

}
