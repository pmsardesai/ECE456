import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.sql.*;
import com.mysql.jdbc.*;

public class Assignment3 {
	//global variables
	static Connection conn; 
	static BufferedReader bufferRead;
	
	public static void main(String args[]){
		//get database connection 
		try {
			conn = (Connection) DBConn.getConnection();
		} catch (Exception e1) {
			System.out.println("Error: No Database Connection. Exiting...");
			return; 
		}   
	
		System.out.println("Welcome to the Hotel Database");
		while(true){
			// console IO
			System.out.println("\nMenu: \n(A) Add Guest \n(B) Delete Guest \n(C) Update Guest");
			 
			try{
			    bufferRead = new BufferedReader(new InputStreamReader(System.in));
			    String s = bufferRead.readLine();
			    
			    char a = s.toUpperCase().charAt(0); 
			    System.out.println("Selected Option: " + a);
	
			    switch(a){
			    case 'A': //add guest
			    	addGuest(); 
			    	break; 
			    case 'B': //delete guest
			    	deleteGuest(); 
			    	break; 
			    case 'C': //update guest
			    	updateGuest(); 
			    	break;
			    default:
			    	System.out.println("That option does not exist.");
			    	break; 
			    }
			    
			}
			catch(Exception e){
				e.printStackTrace(); 
			}
		}
	}
	
	
	public static void addGuest() throws IOException, Exception{
		//Get guestname, address
		System.out.println("Enter the guest's full name:");
        String name = bufferRead.readLine();	
        
        while(!Guest.isNameValid(name)){
        	System.out.println("Invalid: Please enter a non-empty value that is " +
        					   "less than 30 characters: ");
	        name = bufferRead.readLine();
        }
        
        System.out.println("Enter the guest's address");
        String address = bufferRead.readLine();
		
    	//call add guest
    	Guest.add(conn, name, address);

		return; 
	}

	public static void deleteGuest() throws IOException, Exception{
    	System.out.println("Enter the guest ID: ");
        String id = bufferRead.readLine();
        
        while(!Guest.isGuestIdValid(conn, id)){
        	System.out.println("Invalid: Please enter a 4-digit guest ID:");
	        id = bufferRead.readLine();
        }
        
    	//call delete guest
    	Guest.delete(conn, id);
	}

	public static void updateGuest() throws IOException, Exception{
		System.out.println("Enter the guest ID: ");
		String id = bufferRead.readLine();
		
		while(!Guest.isGuestIdValid(conn, id)){
        	System.out.println("Invalid: Please enter a 4-digit guest ID:");
	        id = bufferRead.readLine();
        }

    	System.out.println("Enter the guest's full name, or press enter to skip: ");
        String name = bufferRead.readLine().trim();
        
        while(!Guest.isNameLengthValid(name)){
        	System.out.println("Invalid: Please enter a name that is " +
        					   "less than 30 characters: ");
	        name = bufferRead.readLine();
        }
        
        if (name.length() == 0) {
        	name = null;
        }
        
        System.out.println("Please enter the guest's address, or press enter to skip: ");
        String address = bufferRead.readLine();
        
        if (address.length() == 0) {
        	address = null;
        }
        
    	//call add guest
    	Guest.update(conn, id, name, address);
	}
}
