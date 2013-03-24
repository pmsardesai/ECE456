import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.Connection;

public class Booking {

	
	public static void findRoom(Connection conn, String start, String end, 
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
		System.out.println(query); 
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
	}
	
	public static Boolean isDateValid(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		//check if we can parse the string input into a date yyyy-MMM-dd format
		try{
			sdf.parse(date); 
		}catch(Exception e){
			return false; 
		}
		return true; 
	}
	
	public static Boolean isPriceValid(String price){
		//check if we can parse to ##.## or ###.## (since values are between 
		// 200.00 and 50.00 only
		if(price.matches("\\d{2-3}\\.\\d{2}"))
		    return true;
		return false;
	}
	
}
