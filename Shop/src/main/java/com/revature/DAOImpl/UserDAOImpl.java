package com.revature.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.DAO.GroupDAO;
import com.revature.DAO.UserDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Group;
import com.revature.models.User;

public class UserDAOImpl implements UserDAO {
	private Logger log = LogManager.getRootLogger();

	// transaction based
	@Override 
	public User create(User user) {
		// TODO Auto-generated method stub
		// the service will create the User object to pass in
		// when it's created, will need to get the user_id name that's automatically
		// generated
		Connection conn = null;

		// must follow the transaction approach to ensure that there's no
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);
			// the username will need to be checked
			String createUserSQL = "INSERT INTO shop_schema.users (username, password, email, group_id) VALUES (?, ?, ?, ?)";

			PreparedStatement createUserPS = conn.prepareStatement(createUserSQL, Statement.RETURN_GENERATED_KEYS);

			createUserPS.setString(1, user.getUsername());
			createUserPS.setString(2, user.getPassword());
			createUserPS.setString(3, user.getEmail());
			createUserPS.setInt(4, user.getGroup().getGroup_id());

			int affected = createUserPS.executeUpdate();

			if (affected > 0) {
				ResultSet rs = createUserPS.getGeneratedKeys();
				rs.next();
				int user_id = rs.getInt("user_id");
				user.setUser_id(user_id);
				conn.commit();
			} else {
				conn.rollback();
			}

			return user;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException d) {
				d.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public User getById(Integer id) {
		User user = null;
		String sql = "select * from shop_schema.users where user_id = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			// user_id, username, password, email, group_id
			if (rs.next()) {
				int user_id = id;
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				int group_id = rs.getInt("group_id");

				// get the department_id
				GroupDAO gDao = new GroupDAOImpl();
				Group group = gDao.getById(group_id);

				user = new User();
				user.setUser_id(id);
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.setGroup(group);

			} else {
				log.debug("User wasn't retrieved!");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		// user_id, username, password, email, group_id
		String sql = "UPDATE shop_schema.users SET username = ?, password = ?, email = ?, group_id = ? WHERE user_id = ?";

		int affectedrows = 0;

		try (Connection c = PostgresConnection.getConnectionFromEnv();
				PreparedStatement pstmt = c.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setInt(4, user.getGroup().getGroup_id());
			pstmt.setInt(5, user.getUser_id());

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}

	@Override
	public int delete(User user) {
		// TODO Auto-generated method stub
		// user_id, username, password, email, group_id
		String sql = "DELETE from shop_schema.users WHERE user_id = ?";

		int affectedrows = 0;
		Connection c = null;
		try {
			c = PostgresConnection.getConnectionFromEnv();
			c.setAutoCommit(false);

			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, user.getUser_id());

			affectedrows = pstmt.executeUpdate();
			if (affectedrows > 0) {
				log.debug("User Deletion Successful!");
				c.commit();
			} else {
				log.debug("User Deletion Successful!");
				c.rollback();
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException d) {
				d.printStackTrace();
			}
		}
		log.debug("deleted rows: " + affectedrows);
		return affectedrows;
	}

	@Override
	public User getByUsername(String username) {
		User user = null;
		String sql = "select * from shop_schema.users where username = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setString(1, username);
			ResultSet rs = s.executeQuery();

			// user_id, username, password, email, group_id
			if (rs.next()) {
				int user_id = rs.getInt("user_id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				int group_id = rs.getInt("group_id");

				// get the department_id
				GroupDAO gDao = new GroupDAOImpl();
				Group group = gDao.getById(group_id);

				user = new User();
				user.setUser_id(user_id);
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.setGroup(group);

			} else {
				log.debug("User wasn't retrieved!");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getByEmail(String email) {
		User user = null;
		String sql = "select * from shop_schema.users where email = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setString(1, email);
			ResultSet rs = s.executeQuery();

			// user_id, username, password, email, group_id
			if (rs.next()) {
				int user_id = rs.getInt("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int group_id = rs.getInt("group_id");

				// get the department_id
				GroupDAO gDao = new GroupDAOImpl();
				Group group = gDao.getById(group_id);

				user = new User();
				user.setUser_id(user_id);
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.setGroup(group);

			} else {
				log.debug("User wasn't retrieved!");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
