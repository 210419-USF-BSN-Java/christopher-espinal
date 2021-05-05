package com.revature.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

import com.revature.DAO.ItemDAO;
import com.revature.DAO.OfferDAO;
import com.revature.DAO.StatusDAO;
import com.revature.DAO.UserDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Status;
import com.revature.models.User;

public class OfferDAOImpl implements OfferDAO {
	private static Logger log = LogManager.getRootLogger();

	@Override
	public Offer create(Offer t) {
		Connection conn = null;
		// must follow the transaction approach to ensure that there's no
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);
			// the username will need to be checked
			String createUserSQL = "INSERT INTO shop_schema.offers(item_id, quantity, offer_price, user_id, installments) VALUES(?, ?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(createUserSQL, Statement.RETURN_GENERATED_KEYS);

			// 1 - pending, 2 - completed, 3 - rejected
			ps.setInt(1, t.getItem().getItem_id());
			ps.setInt(2, t.getQuantity());
			ps.setDouble(3, t.getOffer_price());
			ps.setInt(4, t.getUser().getUser_id());
			ps.setInt(5, t.getInstallments());

			int affected = ps.executeUpdate();

			if (affected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				t.setOffer_id(rs.getInt("offer_id"));
				t.setOffer_date(rs.getDate("offer_date"));
				StatusDAO statusDao = new StatusDAOImpl();
				Status status = statusDao.getById(rs.getInt("status_id"));
				t.setStatus(status);
				t.setOwnership(rs.getBoolean("ownership"));
				t.setBalance(rs.getDouble("balance"));
				t.setInstallments(rs.getInt("installments"));
				conn.commit();
			} else {
				conn.rollback();
			}

			return t;
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

		return t;
	}

	@Override
	public Offer getById(Integer id) {
		// TODO Auto-generated method stub
		Offer offer = null;
		String sql = "select * from shop_schema.offers where offer_id = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				int offer_id = id;
				int item_id = rs.getInt("item_id");
				int quantity = rs.getInt("quantity");
				double offer_price = rs.getDouble("offer_price");
				Date offer_date = rs.getDate("offer_date");
				int user_id = rs.getInt("user_id");
				int status_id = rs.getInt("status_id");
				boolean ownership = rs.getBoolean("ownership");
				int installments = rs.getInt("installments");
				double balance = rs.getDouble("balance");

				StatusDAO statusDao = new StatusDAOImpl();
				Status status = statusDao.getById(status_id);

				UserDAO userDao = new UserDAOImpl();
				User user = userDao.getById(user_id);

				ItemDAO itemDao = new ItemDAOImpl();
				Item item = itemDao.getById(item_id);

				offer = new Offer();
				offer.setOffer_id(id);
				offer.setItem(item);
				offer.setQuantity(quantity);
				offer.setOffer_price(offer_price);
				offer.setOffer_date(offer_date);
				offer.setUser(user);
				offer.setStatus(status);
				offer.setOwnership(ownership);
				offer.setInstallments(installments);
				offer.setBalance(balance);
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offer;

	}

	@Override
	public int update(Offer t) {
		Connection conn = null;
		// if the field that's updated is the ownership field (accepting an offer)
		// then all of the other offers should be rejected
		// updates ownership for one offer
		// updates statuses of all other offers as rejected
		// the above should happen at the service level - this should happen at the
		// service level
		int affected = 0;
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);
			// the username will need to be checked
			String createUserSQL = "UPDATE shop_schema.offers SET item_id = ?, quantity = ?, offer_price = ?, user_id = ?, status_id = ?, ownership = ?, installments = ?, balance = ? WHERE offer_id = ?";

			PreparedStatement ps = conn.prepareStatement(createUserSQL);

			// 1 - pending, 2 - completed, 3 - rejected
			ps.setInt(1, t.getItem().getItem_id());
			ps.setInt(2, t.getQuantity());
			ps.setDouble(3, t.getOffer_price());
			ps.setInt(4, t.getUser().getUser_id());
			ps.setInt(5, t.getStatus().getStatus_id());
			ps.setBoolean(6, t.getOwnership());
			ps.setInt(7, t.getInstallments());
			ps.setDouble(8, t.getBalance());
			ps.setInt(9, t.getOffer_id());
			affected = ps.executeUpdate();

			if (affected > 0) {
				conn.commit();
				log.debug("Update Successful");

			} else {
				conn.rollback();
				log.debug("Update Unsuccessful!!");
			}

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

		return affected;
	}

	@Override
	public int delete(Offer t) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM shop_schema.offers WHERE offer_id = ?";
		Connection conn = null;
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(SQL);
			log.debug("Offer delete method - affected: " + t.getOffer_id());
			ps.setInt(1, t.getOffer_id());
			int affected = ps.executeUpdate();
			log.debug("Offer delete method - affected: " + affected);
			if (affected > 0) {
				conn.commit();
				log.debug("Deletion Successful");
			} else {
				conn.rollback();
				log.debug("Deletion Unsuccessful");
			}

			return affected;

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

		return 0;
	}

	public List<Offer> getAll() {
		String sql = "SELECT * FROM shop_schema.offers";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			List<Offer> offers = new ArrayList<>();

			while (rs.next()) {
				Offer offer = new Offer();

				int offer_id = rs.getInt("offer_id");
				int item_id = rs.getInt("item_id");
				int quantity = rs.getInt("quantity");
				double offer_price = rs.getDouble("offer_price");
				Date offer_date = rs.getDate("offer_date");
				int user_id = rs.getInt("user_id");
				int status_id = rs.getInt("status_id");
				int installments = rs.getInt("installments");
				boolean ownership = rs.getBoolean("ownership");
				double balance = rs.getDouble("balance");

				StatusDAO statusDao = new StatusDAOImpl();
				Status status = statusDao.getById(status_id);

				UserDAO userDao = new UserDAOImpl();
				User user = userDao.getById(user_id);

				ItemDAO itemDao = new ItemDAOImpl();
				Item item = itemDao.getById(item_id);

				offer.setOffer_id(offer_id);
				offer.setItem(item);
				offer.setQuantity(quantity);
				offer.setOffer_price(offer_price);
				offer.setOffer_date(offer_date);
				offer.setUser(user);
				offer.setStatus(status);
				offer.setInstallments(installments);
				offer.setOwnership(ownership);
				offer.setBalance(balance);

				offers.add(offer);
			}

			return offers;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
