package com.revature.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.DAO.StatusDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Group;
import com.revature.models.Status;

public class StatusDAOImpl implements StatusDAO {

	@Override
	public Status create(Status t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status getById(Integer id) {
		Status status = null;
		String sql = "select * from shop_schema.status where status_id = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				int status_id = id;
				String status_name = rs.getString("status_name");
				status = new Status(status_id, status_name);

			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int update(Status t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Status t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
