package store.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//
	private static ConnectionFactory instance;
/*
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER_NAME = "scott";
	private static final String PASSWORD = "tiger";
	*/
	private static final String DRIVER_NAME = "org.gjt.mm.mysql.Driver";
	   private static final String URL = "jdbc:mysql://localhost:3306/fan_see_star";
	   private static final String USER_NAME = "root";
	   private static final String PASSWORD = "root";

	private ConnectionFactory() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

	public Connection createConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
	}
}
