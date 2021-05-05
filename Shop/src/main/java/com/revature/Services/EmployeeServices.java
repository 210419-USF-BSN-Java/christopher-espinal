package com.revature.Services;

import java.util.List;
import java.util.Map;

import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;

public interface EmployeeServices {
//  employee - add items
	public Item addItem(Map<String, Object> itemArgs);

//  employee - remove items 
	public int removeItem(Item item);

//  employee - reject/ accept an offer
	// this is the same thing as updating an offer
	public int respondToOffer(Offer offer, String response);

//  employee - see all payments
	public List<Payment> getAllPayments();

}			
