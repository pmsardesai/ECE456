import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.sql.*;
import com.mysql.jdbc.*;

public class Assignment3 {

	public static void main(String args[]){
		
		Connection conn = null; 
		//get database connection 
		try {
			conn = (Connection) DBConn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("No Database Connection...exiting...");
			//return; 
		} 
		
		
		// console IO
		System.out.println("Welcome to the Hotel Database");
		System.out.println("Menu: \n(A) Add Guest \n(B) Delete Guest \n(C) Update Guest");
		 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    
		    char a = s.toUpperCase().charAt(0); 
		    System.out.println("Selected Option:" + a);

		    switch(a){
		    case 'A':
		    	//Get guestname, address
		    	System.out.println("Please enter the guest's name (Firstname Lastname)");
		        String name = bufferRead.readLine();
		        
		        System.out.println("Please enter the guest's address");
		        String address = bufferRead.readLine();
				
		    	//call add guest
		    	Guest.add(conn, name, address);
		    	break; 
		    case 'B':
		    	//call delete guest
		    	break; 
		    case 'C':
		    	//call update guest
		    	break;
		    
		    }
		    
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
}
