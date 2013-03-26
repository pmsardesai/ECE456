import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
			return;
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
			return;
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
			return;
		}
	}
	
	@Test
	// This test case should not be able to delete a guest with an invalid id
	public void delete_guest_with_invalid_guest_id() {
		String id = "aa";
		
		if (!Guest.delete(conn, id)) {
			assertTrue("As expected, user not deleted", true);
		} else {
			fail("Error: User is deleted....");
		}
	}
	
	@Test
	// This test case should be able to update a guest with a valid id
	public void test_update_guest_with_valid_guest_id() {
		String id = "2";
		String name = "Bob White";
		String address = "457 University Ave.";
		
		if (Guest.update(conn, id, name, address)) {
			assertTrue("As expected, user is updated", true);
		} else {
			fail("Error: User is updated....");
		}
	}
	
	@Test
	// This test case should not be able to update a guest with a valid id
	public void test_update_guest_with_invalid_guest_id() {
		String id = "asd";
		String name = "Bob White";
		String address = "457 University Ave.";
		
		if (!Guest.update(conn, id, name, address)) {
			assertTrue("As expected, update throws an error", true);
		} else {
			fail("Error: User is updated....");
		}
	}
	
	@Test
	// This test case should not update the name of the guest to an empty string
	public void test_update_guest_with_name_as_null() {
		// guest ID: 1 should exist in the database.
		String id = "1";
		String name = null;
		String address = "457 University Ave.";
		
		if (Guest.update(conn, id, name, address)) {
			// check to see if the name is updated in the database
			try {
				// get the last guest ID from the table
				PreparedStatement ps = conn.prepareStatement(
						"SELECT guestName FROM guest WHERE guestID=1");
				ResultSet rs = ps.executeQuery();

				// if name exists in the database
				if(rs.next()) {
					assertNotNull("Name is not updated in the database", rs.getString(1));
				} 
			} catch (Exception e) {
				fail("Error: Guest name should not update to null...");
			}
		} else {
			fail("Error: Guest info is not updated...");
		}
	}
}
