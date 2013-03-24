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
		
		//Connection conn = null; 
		//get database connection 
		try {
			conn = (Connection) DBConn.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("No Database Connection...exiting...");
			return; 
		} 
		
		while(true){
			// console IO
			System.out.println("Welcome to the Hotel Database");
			System.out.println("Menu: \n(A) Add Guest \n(B) Delete Guest \n(C) Update Guest");
			 
			try{
			    bufferRead = new BufferedReader(new InputStreamReader(System.in));
			    String s = bufferRead.readLine();
			    
			    char a = s.toUpperCase().charAt(0); 
			    System.out.println("Selected Option:" + a);
	
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
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace(); 
			}
		}
	}
	
	
	public static void addGuest() throws IOException, Exception{
		//Get guestname, address
	
		System.out.println("Please enter the guest's name (Firstname Lastname)");
        String name = bufferRead.readLine();	
        
        while(Guest.isNameValid(name)){
        	System.out.println("You entered: \"" + name + "\", which " + 
        			"is invalid. Please try again.");
	        name = bufferRead.readLine();
        }
        
        System.out.println("Please enter the guest's address");
        String address = bufferRead.readLine();
		
    	//call add guest
    	Guest.add(conn, name, address);

		return; 
	}

	public static void deleteGuest() throws IOException, Exception{

    	System.out.println("Please enter the guest id:");
        String id = bufferRead.readLine();
        
        while(! Guest.isGuestIdValid(conn, id)){
        	System.out.println("You entered: \"" + id + "\", which " + 
        			"is invalid. Please try again.");
	        id = bufferRead.readLine();
        }
        
    	//call delete guest
    	Guest.delete(conn, id);
	}

	public static void updateGuest() throws IOException, Exception{
		//call update guest
    	System.out.println("Please enter the guest's name (Firstname Lastname)");
        String name = bufferRead.readLine();
        
        while(Guest.isNameValid(name)){
        	System.out.println("You entered: \"" + name + "\", which " + 
        			"is invalid. Please try again.");
	        name = bufferRead.readLine();
        }
        
        System.out.println("Please enter the guest's address");
        String address = bufferRead.readLine();
		
    	//call add guest
    	Guest.add(conn, name, address);
	}
}
