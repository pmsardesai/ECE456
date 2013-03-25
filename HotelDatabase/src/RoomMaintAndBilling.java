import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;

public class RoomMaintAndBilling {
	public static boolean printBillsForDepartures(Connection conn, String date) throws ParseException {
		try {
			// get booking and room details
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
				
				// print departures				
				System.out.println("Hotel ID: " + rs.getString(1) +
								   "\nGuest ID: " + rs.getString(3) +
								   "\nRoom number: " + rs.getString(2) +
								   "\nTotal number of days: " + numDays +
								   "\nTotal price: " + totalPrice + "\n\n"
								  ); 
				
				// get the last guest ID from the table
				ps = conn.prepareStatement(
						"SELECT billingID FROM BillingLog ORDER BY billingID DESC LIMIT 1");
				ResultSet rs2 = ps.executeQuery();

				int lastBillingID = 1; // if there are no guests in the table
				// if there are guests in the table,
				if(rs2.next()) {
					// Increment
					lastBillingID = Integer.parseInt(rs2.getString(1)) + 1;
				} 
				
				// Insert into billing table
				Statement s = conn.createStatement();
				s.executeUpdate("INSERT INTO BillingLog (billingID, hotelID, guestID, roomNo, totalDays, totalPrice) " +
						        "VALUES ( '" + formatID(lastBillingID) + "'," +
						        "         '" + formatID(Integer.getInteger(rs.getString(1))) + "'," + 
						        "         '" + formatID(Integer.getInteger(rs.getString(3))) + "'," +
						        "         '" + rs.getString(2) + "'," + 
						        "         '" + totalPrice + "')");
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
	
// ----------------------------------- Format
	public static String formatID(int id) {
		String idStr = "";
		
		int first = (int)(id/1000)*1000;
		int second = (int)((id - first)/100)*100;
		int third = (int)((id - first - second)/10)*10;
		int fourth = (int)(id - first - second - third);
		
		if ((int)(id/1000) == 0) {
			idStr = idStr + "0";
		} else {
			idStr = idStr + (id/1000);
		}
		
		if ((int)((id - first)/100) == 0) {
			idStr = idStr + "0";
		} else {
			idStr = idStr + (int)((id - first)/100);
		}
		
		if ((int)((id - first - second)/10) == 0) {
			idStr = idStr + "0";
		} else {
			idStr = idStr + (int)((id - first - second)/10);
		}
		
		if ((int)(id - first - second - third) == 0) {
			idStr = idStr + "0";
		} else {
			idStr = idStr + (int)(id - first - second - third);
		}
		
		return idStr;
	}
	
//-----------------------VALIDATION
	public static Boolean isDateValid(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		sdf.setLenient(false);
		
		//check if we can parse the string input into a date yyyy-MM-dd format
		try{
			System.out.println(sdf.parse(date));  
			
		}catch(Exception e){
			return false; 
		}
		return true; 
	}
	
}
