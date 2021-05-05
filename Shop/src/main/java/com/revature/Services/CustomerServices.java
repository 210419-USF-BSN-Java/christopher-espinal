package com.revature.Services;

import java.util.List;
import java.util.Map;

import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.models.User;

public interface CustomerServices {
	public List<Item> seeOwnedItems(User user);

//	  customer - sees items on sale
	public List<Item> seeItemsOnSale();

//	  customer - makes offers (should calculate payments, etc)
	public int makeOffer(Map<String, Object> makeOfferArgs);

//	  customer - makes payments 
	public int makePayment(Map<String, Object> makeOfferArgs);

//	  customer - view remaining payments
	public List<Payment> viewUnPaidPayments(User user, Offer offer);

}
