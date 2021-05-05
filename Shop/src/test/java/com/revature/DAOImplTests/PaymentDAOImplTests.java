package com.revature.DAOImplTests;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revature.DAO.OfferDAO;
import com.revature.DAO.PaymentDAO;
import com.revature.DAO.UserDAO;
import com.revature.DAOImpl.OfferDAOImpl;
import com.revature.DAOImpl.PaymentDAOImpl;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentDAOImplTests {
	private static Logger log = LogManager.getRootLogger();
	private static OfferDAO offerDao;
	private static Offer offer;
	private static Payment payment;
	private static PaymentDAO dao;
	private static UserDAO userDao;
	private static User user;

	// deleted the offers to go with this....therefore can't use it
	@BeforeClass
	public static void setUp() {
		offerDao = new OfferDAOImpl();
		offer = new Offer();
		offer = offerDao.getById(5);
		offer.setInstallments(2);
		offerDao.update(offer);
		offer = offerDao.getById(5);

		log.debug("Current offer: " + offer.toString());
		dao = new PaymentDAOImpl();
		payment = new Payment();
		Double amount = offer.getOffer_price();
		Double paymentAmount = amount / offer.getInstallments();
		payment.setAmount(paymentAmount);
		payment.setOffer(offer);
		user = offer.getUser();
		payment.setUser(user);
	}

	@AfterClass
	public static void breakDown() {
		offer.setOwnership(false);
		offer.setInstallments(0);
		offerDao.update(offer);
	}

	// create payment
	@Test
	public void aTest() {
		log.debug("1 create test - no ownership: " + payment.toString());
		payment = dao.create(payment);
		assertNull(payment.getPayment_id());
	}

	// update the ownership status to True - not yet
	@Test
	public void bTest() {
		log.debug("2 test - update offer ownership: " + offer.toString());
		offer.setOwnership(true);
		int affected = offerDao.update(offer);
		offer = offerDao.getById(offer.getOffer_id());
		log.debug("2 test - new Offer Obj reference: " + offer.toString());
		log.debug("2 test - equals?: " + (offer.getOwnership() && payment.getOffer().getOwnership()));
		assertEquals(offer.getOwnership(), payment.getOffer().getOwnership());
		// if these aren't equal then we need to set offer to payment again
		// payment(other variables, offer), updated offer......???
	}

	// process the payment
	@Test
	public void cTest() {
		payment = dao.create(payment);
		assertTrue(payment.getPayment_id() > 0);
		log.debug("3 test - payment: " + payment.toString());
	}

	@Test
	public void dTest() {
		Payment retrievedPayment = dao.getById(payment.getPayment_id());
		log.debug("4 test - getById old: " + payment.toString());
		log.debug("4 test - getById new: " + retrievedPayment.toString());
		assertEquals(payment, retrievedPayment);
	}

	// delete payment test
	@Test
	public void eTest() {
		int affected = dao.delete(payment);
		assertTrue(affected > 0);
	}
}
