package com.revature.ServicesImplTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.DAO.OfferDAO;
import com.revature.DAOImpl.OfferDAOImpl;
import com.revature.Services.CustomerServices;
import com.revature.Services.EmployeeServices;
import com.revature.Services.UserServices;
import com.revature.ServicesImpl.CustomerServicesImpl;
import com.revature.ServicesImpl.EmployeeServicesImpl;
import com.revature.ServicesImpl.UserServicesImpl;
import com.revature.models.Group;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.Status;
import com.revature.models.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTests {
	private static Logger log = LogManager.getRootLogger();
	private static EmployeeServicesImpl eService;
	private static Item item;
	private static CustomerServicesImpl cService;
	private static User user;
	private static User user2;
	private static UserServicesImpl uService;
	private static Offer offer;
	private static Offer offer2;
	private static OfferDAO offerDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		eService = new EmployeeServicesImpl();
		cService = new CustomerServicesImpl();
		uService = new UserServicesImpl();
		offerDao = new OfferDAOImpl();
	}

	@AfterClass
	public static void breakDown() {
		uService.delete(user);
		offerDao.delete(offer2);
	}

	// add item
	@Test
	public void aTest() {
		Map<String, Object> itemArgs = new HashMap<>();
		itemArgs.put("item_name", "a test item");
		itemArgs.put("item_price", 30.00d);
		item = eService.addItem(itemArgs);
		assertTrue(item.getItem_id() > 0);
	}

	// delete item
	@Test
	public void bTest() {
		int affected = eService.removeItem(item);
		assertTrue(affected > 0);
	}

	// add item => customer wants to see items
	@Test
	public void cTest() {
		Map<String, Object> itemArgs = new HashMap<>();
		itemArgs.put("item_name", "a test item");
		itemArgs.put("item_price", 30.00d);
		item = eService.addItem(itemArgs);
		assertTrue(item.getItem_id() > 0);

		List<Item> itemsOnSale = cService.seeItemsOnSale();
		assertTrue(itemsOnSale.size() > 0);
	}

	// create user
	@Test
	public void caTest() {
		Group group = new Group(1, "customer");

		Map<String, Object> userCreationArgs = new HashMap<>();
		userCreationArgs.put("username", "Test Username 3");
		userCreationArgs.put("password", "12341234");
		userCreationArgs.put("email", "emailtest3@mail.co");
		userCreationArgs.put("group", group);
		log.debug(userCreationArgs.get("username"));
		log.debug(userCreationArgs.get("password"));
		log.debug(userCreationArgs.get("email"));
		log.debug(userCreationArgs.get("group"));
		user = uService.create(userCreationArgs);

		assertTrue(user.getUser_id() > 0);
	}

	// create the same user to reject
	@Test
	public void caaTest() {
		Group group = new Group(1, "employee");
		User tempUser;
		Map<String, Object> userCreationArgs = new HashMap<>();
		userCreationArgs.put("username", "Test Username 3");
		userCreationArgs.put("password", "12341234");
		userCreationArgs.put("email", "emailtest3@mail.co");
		userCreationArgs.put("group", group);
		tempUser = uService.create(userCreationArgs);

		assertFalse(tempUser.getUser_id() > 0);
	}

	// login
	@Test
	public void cbTest() {
		User tempUser = uService.login(user);
		assertEquals(user, tempUser);
	}

	// customer wants to make an offer on an item
	@Test
	public void dTest() {
		offer = new Offer();
		Map<String, Object> makeOfferArgs = new HashMap<>();
		makeOfferArgs.put("item", item);
		makeOfferArgs.put("quantity", 1);
		makeOfferArgs.put("offer_price", 20.00d);
		makeOfferArgs.put("user", user);
		makeOfferArgs.put("installments", 2);
		offer = cService.makeOffer(makeOfferArgs);
		assertTrue(offer.getOffer_id() > 0);
	}

	// another customer makes an offer
	@Test
	public void daaTest() {
		user2 = uService.getById(42);
		offer2 = new Offer();
		Map<String, Object> makeOfferArgs = new HashMap<>();
		makeOfferArgs.put("item", item);
		makeOfferArgs.put("quantity", 1);
		makeOfferArgs.put("offer_price", 25.00d);
		makeOfferArgs.put("user", user2);
		makeOfferArgs.put("installments", 2);
		offer2 = cService.makeOffer(makeOfferArgs);
		assertTrue(offer2.getOffer_id() > 0);

	}

	// try to make a payment before employee accepts the offer
	@Test
	public void daTest() {
		// because make offer only shows if it successfully adds to the database
		// it doesn't return an offer object unfortunately
		Map<String, Object> makePaymentArgs = new HashMap<>();
		makePaymentArgs.put("offer", offer);
		makePaymentArgs.put("user", user);
		makePaymentArgs.put("amount", offer.getBalance());
		int affected = cService.makePayment(makePaymentArgs);
		assertFalse(affected > 0);
	}

	// Employee accepts the offer
	@Test
	public void eTest() {
		int affected = eService.respondToOffer(offer, "accept");
		assertTrue(affected > 0);
	}

	// check that the second offer was automatically changed to rejected
	// when the first offer was accepted
	@Test
	public void eaTest() {
		log.debug("Offer to be rejected: " + offer2);
		offer2 = offerDao.getById(offer2.getOffer_id());
		assertTrue(offer2.getStatus().equals(new Status(3, "rejected")));
	}

	// Customer checks out the unPaidPayments
	@Test
	public void fTest() {
		List<Payment> payments = cService.viewUnPaidPayments(user, offer);
		assertTrue(payments.size() > 0);
		payments.stream().forEach(p -> log.info(p.toString()));
	}

	// make a payment
	@Test
	public void gTest() {
		Map<String, Object> makePaymentArgs = new HashMap<>();
		makePaymentArgs.put("offer", offer);
		makePaymentArgs.put("user", user);
		makePaymentArgs.put("amount", offer.getBalance());
		int affected = cService.makePayment(makePaymentArgs);
		assertTrue(affected > 0);

	}

	// test to see if offer balance updated!
	@Test
	public void gaTest() {
		Offer updatedOffer = offerDao.getById(offer.getOffer_id());
		assertTrue(updatedOffer.getBalance() == (offer.getBalance() / offer.getInstallments()));
		assertEquals(updatedOffer, offer);
		log.debug("Check offer update properly : old" + offer);
		// both of these are equal.....is this because it's passed by value and not by
		// reference
		log.debug("Check offer update properly : new" + updatedOffer);
	}

	// check owned items
	@Test
	public void hTest() {
		List<Item> ownedItems = cService.seeOwnedItems(user);
		assertTrue(ownedItems.size() > 0);
		ownedItems.stream().forEach(owned -> log.info(owned));
	}
}
