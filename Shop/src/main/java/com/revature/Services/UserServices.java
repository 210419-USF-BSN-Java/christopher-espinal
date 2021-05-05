package com.revature.Services;

import com.revature.models.User;

public interface UserServices extends ModelServices<User> {
	public User login(User user);
	
}

/*
 * user - create account (returns boolean) 
 * user - login (returns User or null)
 * user - register customer account (change status)
 */

/*
 * employee - add items 
 * employee - remove items 
 * employee - reject/ accept an offer 
 * employee - see all payments
 */

/*
 * customer - sees items that he or she owns 
 * customer - sees items on sale
 * customer - makes offers 
 * customer - makes payments 
 * customer - view remaining
 * payments
 */
