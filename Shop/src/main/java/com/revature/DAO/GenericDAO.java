package com.revature.DAO;

// CRUD create read update delete
public interface GenericDAO<T> {
	public abstract T create(T t); // create

	public abstract T getById(Integer id); // read

	public abstract int update(T t); // update

	public abstract int delete(T t); // delete
}
