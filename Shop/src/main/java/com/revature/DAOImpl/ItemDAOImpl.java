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

import com.revature.DAO.ItemDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Item;

public class ItemDAOImpl implements ItemDAO {

	private Logger log = LogManager.getRootLogger();

	@Override
	public Item create(Item t) {
		Connection conn = null;
		String createSQL = "INSERT INTO shop_schema.items (item_name, item_price) VALUES (?, ?)";

		// must follow the transaction approach to ensure that there's no
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			PreparedStatement createPS = conn.prepareStatement(createSQL, Statement.RETURN_GENERATED_KEYS);

			createPS.setString(1, t.getItem_name());
			createPS.setDouble(2, t.getItem_price());
			int affected = createPS.executeUpdate();

			int item_id;
			if (affected > 0) {
				ResultSet rs = createPS.getGeneratedKeys();
				rs.next();
				item_id = rs.getInt("item_id");
				t.setItem_id(item_id);
			} else {
				log.info("Not successfully updated");
			}

			return t;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// the username will need to be checked
		return t;

	}

	@Override
	public Item getById(Integer id) {
		// TODO Auto-generated method stub
		Item item = null;
		String sql = "select * from shop_schema.items where item_id = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				int item_id = id;
				String item_name = rs.getString("item_name");
				Double item_price = rs.getDouble("item_price");
				item = new Item();
				item.setItem_id(id);
				item.setItem_name(item_name);
				item.setItem_price(item_price);
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;

	}

	@Override
	public int update(Item t) {
		// TODO Auto-generated method stub
		// user_id, username, password, email, group_id
		String sql = "UPDATE shop_schema.items SET item_name = ?, item_price = ? where item_id = ?;";

		int affectedrows = 0;

		try (Connection c = PostgresConnection.getConnectionFromEnv();
				PreparedStatement pstmt = c.prepareStatement(sql)) {

			pstmt.setString(1, t.getItem_name());
			pstmt.setDouble(2, t.getItem_price());
			pstmt.setInt(3, t.getItem_id());

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}

	@Override
	public int delete(Item t) {
		// TODO Auto-generated method stub
		String sql = "delete from shop_schema.items where item_id = ?;";
		int affected = 0;
		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, t.getItem_id());
			affected = s.executeUpdate();

			// user_id, username, password, email, group_id
			if (affected > 0) {
				log.info("\nItem Deleted Successfully\n");
			} else {
				log.info("\nItem Not Successfully Deleted\n");
			}
			return affected;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public List<Item> getAll() {
		List<Item> items = null;
		String sql = "select * from shop_schema.items;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			items = new ArrayList<>();
			while (rs.next()) {
				Item item = new Item();
				Integer item_id = rs.getInt("item_id");
				String item_name = rs.getString("item_name");
				Double item_price = rs.getDouble("item_price");
				item = new Item();
				item.setItem_id(item_id);
				item.setItem_name(item_name);
				item.setItem_price(item_price);
				items.add(item);
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	

}
