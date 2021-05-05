package com.revature.ServicesImpl;

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
import com.revature.Services.EmployeeServices;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.Status;

import jdk.internal.org.jline.utils.Log;

public class EmployeeServicesImpl {
	private static Logger log = LogManager.getRootLogger();
	private static ItemDAO itemDao = new ItemDAOImpl();
	private static OfferDAO offerDao = new OfferDAOImpl();
	private static PaymentDAO paymentDao = new PaymentDAOImpl();

	public Item addItem(Map<String, Object> itemArgs) {
		// TODO Auto-generated method stub
		Item item = new Item();
		item.setItem_name((String) itemArgs.get("item_name"));
		item.setItem_price((Double) itemArgs.get("item_price"));
		return itemDao.create(item);
	}

	public int removeItem(Item item) {
		return itemDao.delete(item);
	}

	public Item getItemById(Integer id) {
		return itemDao.getById(id);
	}

	public int respondToOffer(Offer offer, String response) {
		Status status = new Status();
		if (response.equals("reject")) {
			status.setStatus_id(3);
			status.setStatus_name("rejected");
			offer.setStatus(status);
			return offerDao.update(offer);
		} else if (response.equals("accept")) {
			offer.setOwnership(true);
			offer.setBalance(offer.getOffer_price());
			int affected = offerDao.update(offer);
			if (affected > 0) {
				List<Offer> offers = offerDao.getAll();
				for (Offer otherOffer : offers) {
					// if (item is the same) && (offer is different)
					if (otherOffer.getItem().equals(offer.getItem()) && !otherOffer.equals(offer)) {
						status.setStatus_id(3);
						status.setStatus_name("rejected");
						otherOffer.setStatus(status);

						// update the other offers
						log.debug("Rejected Offer : " + otherOffer);
						int rejections = offerDao.update(otherOffer);
						log.debug("Rejected Offer : " + rejections);
					}
				}
			}
			return affected;
		}
		return 0;
	}

	public List<Offer> seeAllOffers() {
		List<Offer> offers = offerDao.getAll();
		// I only want to see ones that are pending
		// will control that in the presentation layer
		return offers;
	}

	public List<Payment> getAllPayments() {
		return paymentDao.getAll();
	}

	public List<Item> seeItemsOnSale() {
		List<Item> items = itemDao.getAll();
		return items;
	}

	public Offer getOfferById(Integer item_id) {
		// TODO Auto-generated method stub
		return offerDao.getById(item_id);
	}

}
