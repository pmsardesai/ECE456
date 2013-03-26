import static org.junit.Assert.*;
import org.junit.Test;

import com.mysql.jdbc.Connection;


public class GuestTest {

	static Connection conn;
	
	// Set connection
	public GuestTest() throws Exception{
		conn = (Connection) DBConn.getConnection();
	}
	
	@Test
	// This test case checks to see if you can add a valid guest name and address.
	public void test_add_guest_with_valid_name_and_address() {
		fail("Not yet implemented");
	}
	
	@Test
	// This test case checks to see if you can add an address with an empty field.
	public void test_add_guest_with_empty_address() {
		fail("Not yet implemented");
	}
	
	@Test
	// This test cases checks to see if you can delete a guest with a valid id
	public void test_delete_guest_with_valid_guest_id() {
		fail("Not yet implemented");
	}
	
	@Test
	// This test case checks to see if you can delete a guest with an invalid id
	public void test_delete_guest_with_invalid_guest_id() {
		fail("Not yet implemented");
	}
	
	@Test
	// This test case checks to see if you can update a guest with a valid id
	public void test_update_guest_with_valid_guest_id() {
		fail("Not yet implemented");
	}
	
	@Test
	// This test case checks to see if you can update a guest with an invalid id
	public void test_update_guest_with_invalid_guest_id() {
		fail("Not yet implemented");
	}
	
}
