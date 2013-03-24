import java.sql.*;

public class DBConn {
	
	public void getConnection() {
		System.out.println("Connecting to JDBC...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Successfully connected.");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: No Driver!");
			e.printStackTrace();
			return;
		}
	 
		Connection connection = null;
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/", "root", "mysql");
		} catch (SQLException e) {
			System.out.println("Error: Cannot connect!");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Cannot find database.");
		}
		
	}
}
