import static org.junit.Assert.*;

import org.junit.Test;

import com.mysql.jdbc.Connection;


public class MaintTest {

	
	static Connection conn;
	
	
	public MaintTest() throws Exception{
		conn = (Connection) DBConn.getConnection();
	}
	
	@Test
	public void PrintBills() throws Exception{
		
		Boolean b = RoomMaintAndBilling.printBillsForDepartures(conn); 
		assertTrue("Printed Bills", b); 
	}
	
	
	@Test
	public void RoomList(){
		Boolean b = RoomMaintAndBilling.displayArrivalsAndDepartures(conn); 
		assertTrue("Found Departures", b); 
	}
	

}
