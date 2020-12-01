package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	// Static reference to itself
	private static ConnectionFactory instance = new ConnectionFactory();

	public static final String DRIVER_CLASS = "org.postgresql.Driver";
	public static final String URL = "jdbc:postgresql://ec2-54-172-219-218.compute-1.amazonaws.com:5432/dcnd7o5q6i10s1";
	public static final String USER = "pxjuyegfzmgkhy";
	public static final String PASSWORD = "2b1deada5ec6e2d3263b48b44b46595ff743f241d6641c28ff8a7366e9034fc2";
	

	
	// private constructor
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}

	public static Connection getConnection() {
		return instance.createConnection();
	}
}