import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class Database {
	static Connection conn; 
	static BufferedReader bufferRead;
	
	public static void main(String args[]) {
		try {
			conn = (Connection)getConnection();
			if (conn != null) {
				System.out.println("Connection successful.");
			} else {
				System.out.println("Connection not successful.");
				return;
			}
			
			String name = "HotelDatabase2";
			
			createDatabase(conn, name);
			conn.close();
			
			// create new connection
			conn = getConnectionWithNewDatabase(name);
			populateTables(conn);
		} catch (Exception e1) {
			System.out.println("Error: No Database Connection. Exiting...");
			return; 
		}   
	}
	
	public static Connection getConnection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver"); 
		String url = "jdbc:mysql://localhost/"; 
		String username = "root"; 
		String pwd = "mysql"; 
		
		Connection conn = (Connection) DriverManager.getConnection(url, username, pwd);
		return conn; 
	}	
	
	public static Connection getConnectionWithNewDatabase(String name) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver"); 
		String url = "jdbc:mysql://localhost:3306/" + name; 
		String username = "root"; 
		String pwd = "mysql"; 
		
		Connection conn = (Connection) DriverManager.getConnection(url, username, pwd);
		return conn; 
	}
	
	public static void createDatabase(Connection conn, String name) {
		try {
			System.out.println("Creating database...");
			Statement stmt = conn.createStatement();
			String sql = "CREATE DATABASE " + name;
		    stmt.executeUpdate(sql);
		    System.out.println("Done.");
		} catch (Exception e) {
			System.out.println("Failed to make a database.");
		}
	}
	
	public static void populateTables(Connection conn) {
		try {
			System.out.println("Creating tables...");
			Statement stmt = conn.createStatement();
			
			// create Hotel table
			String sql = "CREATE TABLE Hotel ( " +
						 "	  hotelID CHAR(4) NOT NULL , " +
						 "	  hotelName VARCHAR(30) NULL , " +
						 "	  city VARCHAR(30) NULL CHECK(city IN ('Guelph','Waterloo','Kitchener')), " +
					     "    PRIMARY KEY (hotelID) " +
						 ");"; 
			stmt.executeUpdate(sql);
			
			// create Room table
			sql = " CREATE TABLE Room ( " +
			      "	  roomNo CHAR(4) NOT NULL , " + 
				  "   hotelID CHAR(4) NOT NULL , " +
			      "	  price DECIMAL(5,2) NULL CHECK(price BETWEEN 50.00 AND 250.00), " +
				  "   type CHAR(6) NULL CHECK (type IN ('Single','Double','Queen','King')), " +
				  "	  PRIMARY KEY (hotelID, roomNo), " +
				  "   FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID) " +
				  "      ON DELETE NO ACTION " +
				  "		 ON UPDATE NO ACTION " +
				  " );";
			stmt.executeUpdate(sql);
			
			// create Guest table
			sql = "CREATE TABLE Guest (" +
				  "   guestID CHAR(4) NOT NULL, " +
				  "   guestName VARCHAR(30) NULL, " +
				  "   guestAddress VARCHAR(50) NULL, " +
				  "   guestAffiliation VARCHAR(30) NULL, " +
				  "   PRIMARY KEY (guestID) " +
				  ");";
			stmt.executeUpdate(sql);
			
			// create Booking table
			sql = "CREATE TABLE Booking ( " +
			      "   bookingID CHAR(4) NOT NULL, " +
				  "   hotelID CHAR(4) NOT NULL, " +
				  "   guestID CHAR(4) NOT NULL, " +
				  "   roomNo CHAR(4) NOT NULL, " +
				  "   startDate DATE NOT NULL, " +
				  "   endDate DATE NULL, " +
				  "	PRIMARY KEY (bookingID, hotelID, guestID, roomNo, startDate), " +
				  "	FOREIGN KEY (hotelID) REFERENCES Hotel (hotelID) " +
				  "		ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  "	FOREIGN KEY (guestID) REFERENCES Guest (guestID) " +
				  "     ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  " FOREIGN KEY (hotelID, roomNo) REFERENCES Room (hotelID, roomNo) " +
				  "     ON DELETE NO ACTION " +
				  "     ON UPDATE NO ACTION);";
			stmt.executeUpdate(sql);
			
			// create BillingLog table
			sql = "CREATE TABLE BillingLog ( " +
				  "  billingID CHAR(4) NOT NULL, " +
				  "  hotelID CHAR(4) NOT NULL, " +
				  "  roomNo CHAR(4) NOT NULL, " +
				  "  guestID CHAR(4) NOT NULL, " +
				  "  totalDays INT NOT NULL, " +
				  "  totalPrice DECIMAL NOT NULL, " +
				  "  PRIMARY KEY (billingID), " +
				  "	 FOREIGN KEY (hotelID) REFERENCES Hotel (hotelID) " +
				  "		ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  "	 FOREIGN KEY (guestID) REFERENCES Guest (guestID) " +
				  "     ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  "  FOREIGN KEY (hotelID, roomNo) REFERENCES Room (hotelID, roomNo) " +
				  "     ON DELETE NO ACTION " +
				  "     ON UPDATE NO ACTION " +
				  ");";
			stmt.executeUpdate(sql);
			
			System.out.println("Done.");
		} catch (SQLException e) {
			System.out.println("Failed to make the tables.");
		}		
	}	
}
