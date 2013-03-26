import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;


public class GuestTest {

	static Connection conn;
	
	// Set connection
	public GuestTest() throws Exception{
		conn = (Connection) DBConn.getConnection();
	}
	
	@Test
	// This test case should be able to add guest with valid name and address.
	public void add_guest_with_valid_name_and_address() {
		String name = "Bob Smith";
		String address = "456 Random Street";
		
		ResultSet rs = null;
		try {
			rs = Guest.add(conn, name, address);
		} catch (Exception e) {
			fail("Error: Could not add guest.");
		}
		
		assertNotNull("add() returned results", rs);
	}
	
	@Test
	// This test case should be able to add guest with empty address.
	public void add_guest_with_empty_address() {
		String name = "Bob Smith";
		String address = "";
		
		ResultSet rs = null;
		try {
			rs = Guest.add(conn, name, address);
		} catch (Exception e) {
			fail("Error: Could not add guest.");
		}
		
		assertNotNull("add() returned results", rs);
	}
	
	@Test
	// This test case should not be able to add guest with empty name.
	public void add_guest_with_empty_name() {
		String name = "";
		
		if (!Guest.isNameValid(name)) {
			assertTrue("Prompt user again so that a valid name is entered", true);
		} else {
			fail("Error: Does not check for empty guest name correctly.");
		}
	}
	
	@Test
	// This test cases checks to see if you can delete a guest with a valid id
	public void test_delete_guest_with_valid_guest_id() {
		String id = "1";
		
		try {
			Guest.delete(conn, id);
		} catch (Exception e) {
			fail("Error: Could not delete guest.");
		}
		
		assertTrue("Successfully deleted guest.", true);
	}
	
	@Test
	// This test case checks to see if you can delete a guest with an invalid id
	public void test_delete_guest_with_invalid_guest_id() {
		String id = "aa";
		
		try {
			Guest.delete(conn, id);
		} catch (Exception e) {
			fail("Error: Could not delete guest.");
		}
		
		assertTrue("Successfully deleted guest.", true);
	}
	
	@Test
	// This test case checks to see if you can update a guest with a valid id
	public void test_update_guest_with_valid_guest_id() {
		String id = "aa";
		
		try {
			Guest.delete(conn, id);
		} catch (Exception e) {
			fail("Error: Could not delete guest.");
		}
		
		assertTrue("Successfully deleted guest.", true);
	}
	
	@Test
	// This test case checks to see if you can update a guest with an invalid id
	public void test_update_guest_with_invalid_guest_id() {
		fail("Not yet implemented");
	}
	
}
