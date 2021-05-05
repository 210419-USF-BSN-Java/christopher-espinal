package com.revature.ServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.ItemDAO;
import com.revature.DAO.OfferDAO;
import com.revature.DAO.PaymentDAO;
import com.revature.DAOImpl.ItemDAOImpl;
import com.revature.DAOImpl.OfferDAOImpl;
import com.revature.DAOImpl.PaymentDAOImpl;
import com.revature.Services.CustomerServices;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.Status;
import com.revature.models.User;

public class CustomerServicesImpl {
	private static Logger log = LogManager.getRootLogger();
	private static OfferDAO offerDao = new OfferDAOImpl();
	private static ItemDAO itemDao = new ItemDAOImpl();
	private static PaymentDAO paymentDao = new PaymentDAOImpl();

	public List<Item> seeOwnedItems(User user) {
		List<Offer> offers = offerDao.getAll();
		offers.removeIf(offer -> !offer.getUser().getUsername().equals(user.getUsername()));
		
		List<Item> items = new ArrayList<>();
		for (Offer offer : offers) {
			if ((offer.getOwnership() == true)) {
				items.add(offer.getItem());
			}
		}
		return items;
	}

	public List<Offer> seeAllOwnedOffers(User user) {
		List<Offer> offers = offerDao.getAll();
		offers.removeIf(offer -> !offer.getUser().equals(user));
		// offers.removeIf(offer -> !offer.getOwnership());
		return offers;
	}

	public List<Item> seeItemsOnSale() {
		List<Item> items = itemDao.getAll();
		return items;
	}

	public Offer makeOffer(Map<String, Object> makeOfferArgs) {
		// User user, Item item, Double offer_price
		Offer offer = new Offer();

		// this is making me think that I need a

		offer.setItem((Item) makeOfferArgs.get("item"));
		offer.setQuantity((Integer) makeOfferArgs.get("quantity"));
		offer.setOffer_price((Double) makeOfferArgs.get("offer_price"));
		offer.setUser((User) makeOfferArgs.get("user"));
		offer.setInstallments((Integer) makeOfferArgs.get("installments"));

		offer = offerDao.create(offer);

		// will return an ID that is greater than 0 or not null
		// if it returns null, then that means that an offer wasn't made
		return offer;
	}

	// the offer will have information on the installments
	// each payment should reduce the balance on the offer object and data
	// - choose the offer to make a payment on
	// - submit the payment based on conditions such as the offer's status and
	// ownership rights
	// - update the offer's balance
	// -
	public int makePayment(Map<String, Object> makePaymentArgs) {

		Offer offer = (Offer) makePaymentArgs.get("offer"); // verification logic
		//log.debug("make payment service - " + offer);
		int installments = offer.getInstallments();
		if (installments <= 0) {
			log.info("Sorry, payments can't be made on this offer!");
			offer.setStatus(new Status(2, "completed"));
			offerDao.update(offer);
			return 0;
		}

		// create a payment
		Payment payment = new Payment();
		payment.setUser((User) makePaymentArgs.get("user"));
		payment.setOffer(offer);

		Double amount = ((Double) makePaymentArgs.get("amount") / installments);
		payment.setAmount(amount);
		payment = paymentDao.create(payment);

		if (payment.getPayment_id() != null) {
			//log.debug("Make Payment - Payment Id: " + payment);

			// update number of installments left
			int oldInstallments = offer.getInstallments();
			offer.setInstallments(oldInstallments - 1);
			if (oldInstallments - 1 == 0) {
				Status complete = new Status();
				complete.setStatus_id(2);
				complete.setStatus_name("completed");
				offer.setStatus(complete);
			}

			Double oldBalance = offer.getBalance();
			offer.setBalance(oldBalance - amount);
			offerDao.update(offer);
			return payment.getPayment_id();
		} else {
			log.info("Payment Unsuccessful");
			return 0;
		}
	}

	public List<Payment> viewUnPaidPayments(User user, Offer offer) {
		// view offers with balances and installments left
		// List<Offer> offers = offerDao.getAll();
		// log.debug("View Unpaid Payments Method = Installments: " +
		// offer.getInstallments());
		List<Payment> payments = new ArrayList<>();
		for (int i = 1; i <= offer.getInstallments(); i++) {
			Payment payment = new Payment();
			payment.setOffer(offer);
			// need to figure out how the date works
			payment.setAmount(offer.getBalance() / offer.getInstallments());
			payment.setUser(user);
			payments.add(payment);
		}
		// log.debug("View Unpaid Payments Method = Installments: " + payments);

		return payments;
	}

	public Item getItemById(Integer id) {
		return itemDao.getById(id);
	}

	public Offer getOfferById(Integer item_id) {
		// TODO Auto-generated method stub
		return offerDao.getById(item_id);
	}

}
