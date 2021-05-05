package com.revature.DAO;

import java.util.List;

import com.revature.models.Payment;

public interface PaymentDAO extends GenericDAO<Payment> {
	public List<Payment> getAll();
}
