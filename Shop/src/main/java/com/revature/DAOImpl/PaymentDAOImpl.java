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

import com.revature.DAO.OfferDAO;
import com.revature.DAO.PaymentDAO;
import com.revature.DAO.StatusDAO;
import com.revature.DAO.UserDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Payment;
import com.revature.models.Status;
// add some payment balance logic
// should that be a part of the business logic? Verification can happen there!
// each payment is attached to an offer. The business service layer will update
// the balance according to offers and payments

public class PaymentDAOImpl implements PaymentDAO {
	public static Logger log = LogManager.getRootLogger();

	@Override
	public Payment create(Payment t) {
		// TODO Auto-generated method stub
		// start payment
		Connection conn = null;
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);
			String sqlOne = "SELECT * FROM shop_schema.offers WHERE offer_id = ?";
			PreparedStatement psOne = conn.prepareStatement(sqlOne);
			psOne.setInt(1, t.getOffer().getOffer_id());
			ResultSet rsOne = psOne.executeQuery();

			if (rsOne.next()) {
				StatusDAO statusDao = new StatusDAOImpl();
				Status status = statusDao.getById(rsOne.getInt("status_id"));
				Boolean ownership = rsOne.getBoolean("ownership");

				// checks if a payment can be made based on the offer status
				// pending completed rejected
				// false true false
				// true true false
				String status_name = status.getStatus_name();
				if (status_name != "pending" && !ownership) {
					conn.rollback();
					if (status_name.equals("pending")) {
						log.info("Payment unsuccessful! The current owner hasn't accepted your offer!");
					} else if (status_name.equals("rejected")) {
						log.info("Payment unsuccessful! Your offer was rejected");
					} else if (status_name.equals("completed")) {
						log.info("Payment unsuccessful! This transaction has already been paid off");
					}
					return t;
				}
			}

			// proceeds if the offer has been accepted and ownership has been changed
			String sqlTwo = "INSERT INTO shop_schema.payments (offer_id, amount, user_id) VALUES (?,?,?);";
			PreparedStatement psTwo = conn.prepareStatement(sqlTwo, Statement.RETURN_GENERATED_KEYS);
			psTwo.setInt(1, t.getOffer().getOffer_id());
			psTwo.setDouble(2, t.getAmount());
			psTwo.setInt(3, t.getUser().getUser_id());
			int affected = psTwo.executeUpdate();

			// add some payment balance logic
			// should that be a part of the business logic? Verification can happen there!
			// each payment is attached to an offer. The business service layer will update
			// the balance according to offers and payments

			if (affected > 0) {
				ResultSet rsTwo = psTwo.getGeneratedKeys();
				rsTwo.next();
				t.setPayment_int(rsTwo.getInt("payment_id"));
				t.setPayment_date(rsTwo.getDate("payment_date"));
				conn.commit();
				log.info("Payment Successful!");
			} else {
				conn.rollback();
				log.info("Payment Unsuccessful!");
			}
			return t;
		} catch (SQLException | ClassNotFoundException e) {
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

		// payment should roll back if the offer is still pending

		return null;
	}

	@Override
	public Payment getById(Integer id) {
		Connection conn = null;
		String sql = "SELECT * FROM shop_schema.payments WHERE payment_id = ?";
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Payment payment = new Payment();
			if (rs.next()) {
				payment.setPayment_int(rs.getInt("payment_id"));
				OfferDAO offerDao = new OfferDAOImpl();
				payment.setOffer(offerDao.getById(rs.getInt("offer_id")));
				payment.setAmount(rs.getDouble("amount"));
				payment.setPayment_date(rs.getDate("payment_date"));
				UserDAO userDao = new UserDAOImpl();
				payment.setUser(userDao.getById(rs.getInt("user_id")));
				return payment;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int update(Payment t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Payment t) {
		// TODO Auto-generated method stub
		Connection conn = null;
		String sql = "DELETE FROM shop_schema.payments WHERE payment_id = ?";

		try {
			conn = PostgresConnection.getConnectionFromEnv();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getPayment_id());
			int affected = ps.executeUpdate();

			if (affected > 0) {
				log.debug("Payment successfully deleted!");
				conn.commit();
			} else {
				log.debug("Payment unsuccessful! Please try again!");
				conn.rollback();
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

	public List<Payment> getAll() {
		Connection conn = null;
		String sql = "SELECT * FROM shop_schema.payments";
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Payment> payments = new ArrayList<>();

			while (rs.next()) {
				Payment payment = new Payment();
				payment.setPayment_int(rs.getInt("payment_id"));
				OfferDAO offerDao = new OfferDAOImpl();
				payment.setOffer(offerDao.getById(rs.getInt("offer_id")));
				payment.setAmount(rs.getDouble("amount"));
				payment.setPayment_date(rs.getDate("payment_date"));
				UserDAO userDao = new UserDAOImpl();
				payment.setUser(userDao.getById(rs.getInt("user_id")));
				payments.add(payment);
			}

			return payments;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
