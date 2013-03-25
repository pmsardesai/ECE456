import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class RoomMaintAndBilling {
	public static boolean printBillsForDepartures(Connection conn, String date) throws ParseException {
		try {
			// get the last guest ID from the table
			String sql = "SELECT b.hotelID, b.roomNo, b.guestID, b.startDate, b.endDate, r.price " +
						 "FROM booking b " +
						 "    INNER JOIN Room r ON r.hotelID = b.hotelID AND r.roomNo = b.roomNo " +
						 "WHERE endDate = '" + date + "'";
			PreparedStatement ps = conn.prepareStatement(sql);			
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Billing Log");
			System.out.println("-----------");
			
			while(rs.next()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				sdf.setLenient(false);
				
				Date startDate = sdf.parse(rs.getString(4));
				Date endDate = sdf.parse(rs.getString(5));
				
				int numDays = (int)( (endDate.getTime() - startDate.getTime() ) / (1000 * 60 * 60 * 24));
				double totalPrice = Integer.parseInt(rs.getString(6)) * numDays;
				
				//check if we can parse the string input into a date yyyy-MM-dd format				
				System.out.println("Hotel ID: " + rs.getString(1) +
								   "\nGuest ID: " + rs.getString(3) +
								   "\nRoom number: " + rs.getString(2) +
								   "\nTotal number of days: " + numDays +
								   "\nTotal price: " + totalPrice + "\n\n"
								  ); 
				
				// get the last guest ID from the table
				sql = "SELECT b.hotelID, b.roomNo, b.guestID, b.startDate, b.endDate, r.price " +
							 "FROM booking b " +
							 "    INNER JOIN Room r ON r.hotelID = b.hotelID AND r.roomNo = b.roomNo " +
							 "WHERE endDate = '" + date + "'";
				ps = conn.prepareStatement(sql);			
				ps.executeQuery(); // put in billing table
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean displayArrivalsAndDepartures(Connection conn, String date) {
		try {
			// display number of days
			// room price
			// log into billing log
			
			// get the last guest ID from the table
			String sql = "SELECT hotelID, roomNo, guestID, startDate, endDate FROM booking " +
						 "WHERE startDate = '" + date + "'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Arrivals:");
			System.out.println("--------");
			while(rs.next()){
				System.out.println("Hotel ID: " + rs.getString(1) +
								   "\nRoom Number: " + rs.getString(2) +
								   "\nGuest ID: " + rs.getString(3) +
								   "\nStart Date: " + rs.getString(4) +
								   "\nEnd Date: " + rs.getString(5) + "\n\n"
								  ); 
			}
			
			
			// get the last guest ID from the table
			sql = "SELECT hotelID, roomNo, guestID, startDate, endDate FROM booking " +
						 "WHERE endDate = '" + date + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			System.out.println("Departures:");
			System.out.println("----------");
			while(rs.next()){
				System.out.println("Hotel ID: " + rs.getString(1) +
								   "\nRoom Number: " + rs.getString(2) +
								   "\nGuest ID: " + rs.getString(3) +
								   "\nStart Date: " + rs.getString(4) +
								   "\nEnd Date: " + rs.getString(5) + "\n\n"
								  ); 
			}
			
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
}
