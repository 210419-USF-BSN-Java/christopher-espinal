package com.revature.Services;

import java.util.List;
import java.util.Map;

public interface ModelServices<T> {
	// when creating a user for example, there should be requirements
	// create(verification, access)
	//		verification - try - what happens?
	//		access - true false
	
	public T create(Map<String, Object> createModelObjectArgs);
	
	// updating group_id, etc
	// must start off without id
	public Integer update(T t);
	
	// refreshing data
	public T getById(Integer id);
	
	// deleting data
	public Integer delete(T t);
	
	// getting lists of data
	public List<T> getAll();
	
	// verifying information
	// like a login password
	public Boolean check(T t);
}
