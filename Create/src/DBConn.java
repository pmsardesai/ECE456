import java.sql.*;

public class DBConn {
	
	public static Connection getConnection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver"); 
		String url = "jdbc:mysql://localhost:3306/hoteldatabase"; 
		String username = "root"; 
		String pwd = "mysql"; 
		
		Connection conn = DriverManager.getConnection(url, username, pwd);
	
		return conn; 
	}
}
