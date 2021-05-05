package com.revature.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnection {
	private static Connection connection = null;

	// a static method is the best way to do this because the same connection can be
	// used across the entire program
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/postgres";
			String username = "postgres";
			String password = "H0mel3ss!!!";
			connection = DriverManager.getConnection(url, username, password);
			return connection;
		} else {
			return connection;
		}

	}

	public static Connection getConnectionFromEnv() throws ClassNotFoundException, SQLException {

		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}

		return connection;
	}
}
