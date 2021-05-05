package com.revature.DAO;

import java.util.List;

import com.revature.models.Item;

public interface ItemDAO extends GenericDAO<Item> {
	public List<Item> getAll();
}
