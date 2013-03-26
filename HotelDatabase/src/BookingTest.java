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
	public void GetBooking() throws SQLException {
		String start = "2013-01-01"; 
		String end = "2013-01-01"; 
		String name = "Hilton"; 
		String city = "Waterloo"; 
		String price = null; 
		String type = "Single"; 
		
		String s = null ;
		
		s = Booking.findRoom(conn, start, end, name, city, price, type); 
		assertSame(s,"Found Room"); 
	}

	@Test
	public void GetNoBooking() throws SQLException {
		String start = "2013-01-01"; 
		String end = "2013-01-01"; 
		String name = "Hilton"; 
		String city = "Toronto"; //shouldn't find any results 
		String price = null; 
		String type = "Single"; 
		
		String s = null ;
		
		s = Booking.findRoom(conn, start, end, name, city, price, type); 
		assertSame(s,"No Rooms Found"); 
	}

	@Test
	public void checkBookingDoesntExists() throws SQLException {
		String startDate = "2013-12-01"; 
		String endDate = "2013-12-10"; 
		String hotelID = "0001"; 
		String roomNo = "0001"; 
		
		Boolean s = null ;
		
		s = Booking.checkBookingExists(conn, hotelID, roomNo, startDate, endDate); 
		assertTrue("No Conflict", !s); 
	}

	@Test
	public void checkBookingExists() throws SQLException {
		String startDate = "2013-08-01"; 
		String endDate = "2013-08-10"; 
		String hotelID = "0001"; 
		String roomNo = "0001"; 
		
		Boolean s = null ;
		
		s = Booking.checkBookingExists(conn, hotelID, roomNo, startDate, endDate); 
		assertTrue("Conflict Found", s); 
	}
	
	
}
