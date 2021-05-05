package com.revature.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.DAO.GroupDAO;
import com.revature.dbutils.PostgresConnection;
import com.revature.models.Group;

public class GroupDAOImpl implements GroupDAO {

	@Override
	public Group create(Group t) {
		Connection conn = null;
 
		// must follow the transaction approach to ensure that there's no
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			// the username will need to be checked
			String createSQL = "INSERT INTO shop_schema.usergroups(group_name) VALUES(?)";

			PreparedStatement createPS = conn.prepareStatement(createSQL, Statement.RETURN_GENERATED_KEYS);

			createPS.setString(1, t.getGroup_name());
			int affected = createPS.executeUpdate();

			int group_id;
			if (affected > 0) {
				ResultSet rs = createPS.getGeneratedKeys();
				group_id = rs.getInt("group_id");
				t.setGroup_id(group_id);

			} else {
			}

			return t;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Group getById(Integer id) {
		// TODO Auto-generated method stub
		Group group = null;
		String sql = "select * from shop_schema.usergroups where group_id = ?;";

		try {
			Connection c = PostgresConnection.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			// user_id, username, password, email, group_id
			if (rs.next()) {
				int group_id = id;
				String group_name = rs.getString("group_name");
				group = new Group(group_id, group_name);

			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return group;

	}

	@Override
	public int update(Group t) {
		Connection conn = null;

		// must follow the transaction approach to ensure that there's no
		try {
			conn = PostgresConnection.getConnectionFromEnv();
			// the username will need to be checked
			String sql = "UPDATE shop_schema.usergroups SET group_name = ? WHERE group_id = ?";

			PreparedStatement createPS = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			createPS.setString(1, t.getGroup_name());
			createPS.setInt(2, t.getGroup_id());
			int affected = createPS.executeUpdate();

			int group_id;
			if (affected > 0) {
				ResultSet rs = createPS.getGeneratedKeys();
				group_id = rs.getInt("group_id");
				t.setGroup_id(group_id);

			} else {
			}
			return affected;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delete(Group t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
