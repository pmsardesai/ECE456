import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.Connection;

public class Booking {

//-----------------------------QUESTION 2
	public static ResultSet findRoom(Connection conn, String start, String end, 
			String name, String city, String price, String type) throws SQLException{
		
		String select = "SELECT H.hotelID, H.hotelName, H.city, R.roomNo, R.price, R.type "; 
		String from = "FROM Hotel H INNER JOIN ROOM R ON H.hotelID = R.hotelID "; 
		String where = "WHERE "; 
		String subquery = ""; 
		Boolean w = true; //tracks if AND should be added (if w = true)
		
		if(name != null){
			if(!w)
				where = where + "AND ";
			
			where = where + "H.hotelName = '" + name + "' "; 
			w = false; 
		}
		
		if(city != null){
			if(!w)
				where = where + "AND "; 
			
			where = where + "H.city = '" + city + "' ";
			w = false; 
		}
		
		if(price != null){
			if(!w)
				where = where + "AND ";
			
			where = where + "R.price = " + price + " "; 
			w = false; 
		}
		
		if(type != null){
			if(!w)
				where = where + "AND "; 
			
			where = where + "R.type = '" + type + "' "; 
			w = false;
		}
		
		if(start != null && end != null){
			if(!w)
				where = where + "AND "; 			
			
			subquery = where + "(R.hotelID, R.roomNo) NOT IN (SELECT hotelID, roomNo FROM Booking " +
					"WHERE startDate BETWEEN '" + start + "' AND '" + end +
					"' OR endDate BETWEEN '" + start + "' AND '" + end +
					"'OR (startDate < '" + start + "' AND endDate > '" + end + "'))"; 

		}
				
		String query = select + from + subquery; 
		//System.out.println(query); 
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query); 
				
		while(rs.next()){
			System.out.println("HotelID: " + rs.getString(1) +
					"\nHotelName: " + rs.getString(2) +
					"\nCity: " + rs.getString(3) +
					"\nRoomNo: " + rs.getString(4) +
					"\nPrice: " + rs.getString(5) + 
					"\nType: " + rs.getString(6) + "\n\n"
					); 
		}
		return rs;  		
	}
 //-----------------------VALIDATION
	public static Boolean isDateValid(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		sdf.setLenient(false);
		
		//check if we can parse the string input into a date yyyy-MM-dd format
		try{
			sdf.parse(date);  
			
		}catch(Exception e){
			return false; 
		}
		return true; 
	}
	
	public static Boolean checkBookingExists(Connection conn, String hotelID, 
			String roomNo, String startDate, String endDate){
		
		// find bookings with the same hotel, and room + overlapping startDate and endDate
		String query = "SELECT * FROM BOOKING WHERE " +
				formatID(Integer.parseInt(hotelID)) + "', '" + 
				formatID(Integer.parseInt(roomNo)) + "', '" +
				"startDate BETWEEN '" + startDate + "' AND '" + endDate +
					"' OR endDate BETWEEN '" + startDate + "' AND '" + endDate +
					"'OR (startDate < '" + startDate + 
					"' AND endDate > '" + endDate + "')"; 
		//System.out.println(query); 
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(query); 
			
			if(rs.next()) //results exist - there is booking conflict
				return true; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false; 
		} 
	
		return false; 
	}
	
	
	public static Boolean isPriceValid(String price){
		//check if we can parse to ##.## or ###.## (since values are between 
		// 200.00 and 50.00 only
		if(price.matches("\\d{2-3}\\.\\d{2}"))
		    return true;
		return false;
	}
	
//-------------------------------QUESTION 3 - BOOKING A ROOM
	public static String bookRoom(Connection conn, String hotelID, String roomNo, 
			String guestID, String startDate, String endDate){

		int lastBookingID = 1; // if there are no bookings in the table
		
		if(!checkBookingExists(conn, hotelID, roomNo, startDate, endDate)){
		
			try {
				// get the last guest ID from the table
				PreparedStatement ps = conn.prepareStatement(
						"SELECT bookingID FROM Booking ORDER BY bookingID DESC LIMIT 1");
				ResultSet rs = ps.executeQuery();
	
				// if there are bookings in the table,
				if(rs.next()) {
					// Increment
					lastBookingID = Integer.parseInt(rs.getString(1)) + 1;
				} 
				
				// Insert booking into table
				Statement s = conn.createStatement();
				String query = "INSERT INTO Booking (bookingID, hotelID, guestID, roomNo, startDate, endDate) " +
						        "VALUES ('" + formatID(lastBookingID) + "', '" + 
						formatID(Integer.parseInt(hotelID)) + "', '" + 
						formatID(Integer.parseInt(guestID)) + "', '" +
						formatID(Integer.parseInt(roomNo)) + "', '" +
						startDate + "', '" + endDate + "');"; 
				s.execute(query); 
				//System.out.println(query);
			} catch (SQLException e) {
				System.out.println("Error: Could not add booking - error.");
				//e.printStackTrace();
				return "Error: Could not add booking."; 
			}
			System.out.println("Successfully booked room: " + String.valueOf(lastBookingID));
			return String.valueOf(lastBookingID);
		}
		System.out.println("Error: Could not add booking - conflict");
		return "Error: Could not add booking."; 
	}
	
	
	//------------------------ FORMAT
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

}


