import static org.junit.Assert.*;

import java.sql.*; 

import org.junit.Test;
import com.mysql.jdbc.Connection;


public class BookingTest {

	static Connection conn;
	
	
	public BookingTest() throws Exception{
		conn = (Connection) DBConn.getConnection();
	}
	
	@Test
	public void GetBooking() {
		String start = "2013-01-01"; 
		String end = "2013-01-10"; 
		String name = "Hilton"; 
		String city = "Waterloo"; 
		String price = null; 
		String type = "Single"; 
		
		ResultSet rs = null ;
		
		try {
			rs = Booking.findRoom(conn, start, end, name, city, price, type);
		} catch (SQLException e) {
			fail("findRoom() did not finish executing");  
		} 
		assertNotNull("findRoom() returned results", rs); 
	}

}
