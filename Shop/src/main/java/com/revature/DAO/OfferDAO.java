package com.revature.DAO;

import java.util.List;

import com.revature.models.Offer;

public interface OfferDAO extends GenericDAO<Offer> {

	List<Offer> getAll();

}
