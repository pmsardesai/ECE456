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
			createDatabase(conn);
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
	public static void createDatabase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE DATABASE hoteldatabase2";
		    stmt.executeUpdate(sql);
		} catch (Exception e) {
			
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
				  "	  INDEX `hotelID_idx` (hotelID ASC), " +
				  "   CONSTRAINT `fk_hotelID_Room` " +
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
				  "   hotelID CHAR(4) NOT NULL, " +
				  "   guestID CHAR(4) NOT NULL, " +
				  "   roomNo CHAR(4) NOT NULL, " +
				  "   startDate DATE NOT NULL, " +
				  "   endDate DATE NULL, " +
				  "	PRIMARY KEY (hotelID, guestID, roomNo, startDate), " +
			      " INDEX `fk_hotelID_bookinglog_idx` (hotelID ASC), " +
				  " INDEX `fk_guestID_bookinglog_idx` (guestID ASC), " +
				  " INDEX `fk_roomNo_bookinglog_idx` (hotelID ASC, roomNo ASC), " +
				  " CONSTRAINT `fk_hotelID_booking` " +
				  "		FOREIGN KEY (hotelID)" +
				  "		REFERENCES Hotel (hotelID) " +
				  "		ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  " CONSTRAINT `fk_guestID_booking` " +
				  "		FOREIGN KEY (guestID) " +
				  "     REFERENCES Guest (guestID) " +
				  "     ON DELETE NO ACTION " +
				  "		ON UPDATE NO ACTION, " +
				  " CONSTRAINT `fk_roomNo_booking` " +
				  " FOREIGN KEY (hotelID, roomNo) " +
				  "     REFERENCES Room (hotelID, roomNo) " +
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
				  "  FOREIGN KEY (hotelID, roomNo, guestID) REFERENCES Booking(hotelID, roomNo, guestID)" +
				  " );";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
		}		
	}	
}
