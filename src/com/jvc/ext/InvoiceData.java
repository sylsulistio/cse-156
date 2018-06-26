package com.jvc.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Donot change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 * @throws SQLException 
	 */
	public static void removeAllPersons() throws SQLException{
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "TRUNCATE table Emails";
		ps = conn.prepareStatement(query);
		//Function to delete all emails from the Emails table, as every email is connected to a person
		try {
			ps.execute();
			
			//Function to delete each address associated with a person
			query = "DELETE FROM Address JOIN Persons WHERE Persons.AddressKey = Address.AddressKey";
			ps = conn.prepareStatement(query);
			ps.execute();
			
			//Function to delete all Persons from the Persons table
			query = "TRUNCATE table Persons";
			ps = conn.prepareStatement(query);
			ps.execute();

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			conn.close();
		}

	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @throws SQLException 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String street, String city, String state, String zip, String country) throws SQLException {
		//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//Inserting Address First
			String query = "INSERT INTO Address(Street, City, State, Zip, Country) VALUES "
					+ "(?, ?, ?, ?, ?)";
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.execute();
			
			// Inserting Person
			// Selecting the last inserted address key
			query = "SELECT AddressKey FROM Address WHERE Address.AddressKey = LAST_INSERT_ID";
			ps = conn.prepareStatement(query);
			int addressID = 999;
			rs = ps.executeQuery();
			
			if(rs.next()){
				addressID = rs.getInt("AddressKey");
			}
			
			// inserting blank email field
			query = "INSERT INTO Emails VALUES (LAST_INSERT_ID, null)";
			ps = conn.prepareStatement(query);
			ps.execute();
			
			query = "SELECT EmailKey FROM Emails WHERE Emails.EmailKey = LAST_INSERT_ID";
			ps = conn.prepareStatement(query);
			int emailID = 999;
			rs = ps.executeQuery();

			if(rs.next()){
				emailID = rs.getInt("EmailKey");
			}
			
			// Adding this key into Persons
			query = "INSERT INTO Persons (PersonCode, Name, AddressKey, EmailKey)"
						+ " VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.execute();
			
			String personName = lastName + ", " + firstName;
			
			ps.setString(1, personCode);
			ps.setString(2, personName);
			ps.setInt(3, addressID);
			ps.setInt(4, emailID);
			ps.execute();
			
			rs.close();
			ps.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 * @throws SQLException 
	 */
	
	public static void addEmail(String personCode, String email) throws SQLException {
		//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM Persons WHERE PersonCode = ?";
		ps = conn.prepareStatement(query);
		ps.setString(1, personCode);
		
		try {
			// Finding email key for relevant personCode
			rs = ps.executeQuery();
			
			int emailKey = 999;
			String prevEmail = "";
			if (rs.next()) {
				emailKey = rs.getInt("EmailKey");
				prevEmail = rs.getString("Email");
			}
			// Appending email to original string, if not null
			query = "UPDATE Emails SET Email = ? WHERE EmailKey = ?";
			ps.setString(1, (prevEmail + "," + email));
			ps.setInt(2, emailKey);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	/**
	 * 4. Method that removes every customer record from the database
	 * @throws SQLException 
	 */

	public static void removeAllCustomers() throws SQLException {
		
		//Call the remove all invoices function, as you cannot have invoices without customers
		removeAllInvoices(); 
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "DELETE FROM Address JOIN Customers WHERE Customer.AddressKey = Address.AddressKey";
		ps = conn.prepareStatement(query);
		
		try {
			//Function to delete each address associated with a customer
			ps.execute();
			
			//Function to delete all Customers from the Customers table
			query = "TRUNCATE table Customers";
			ps = conn.prepareStatement(query);
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			conn.close();
		}
	}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
		String name, String street, String city, String state, String zip, String country) throws SQLException {
		
		//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "INSERT INTO Address(Street, City, State, Zip, Country) VALUES "
			+ "(?, ?, ?, ?, ?)";
		ps = conn.prepareStatement(query);
		
		try {
			//Inserting Address First
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.execute();

			//Inserting Person Contact
			
			//Insert Customer
			//next, to find the address ID. We can do this by finding the last input address ID from the address we created
			
			query = "Select AddressKey FROM Address WHERE Address.AddressKey = LAST_INSERT_ID";
			int addressID = 999;
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				addressID = rs.getInt("AddressKey");
			}
			//Now for the main insertion:
			query = "INSERT INTO Customers(CustomerID, Type, PrimaryContact, Name, AddressKey) VALUES "
					+ "?, ?, ?, ?, ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setInt(5, addressID);
			ps.execute();
			
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			//done
			ps.close();
			conn.close();
		}
	}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		//before we can truncate data, we need to delete all addresses associated with a product
		//The only product which has address data is the movie ticket field:
		//Connect:
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		
	//Not sure whether we need to delete the Invoice table. 
		
	//Function to delete each address associated with a movie ticket
		ps = null;
		String query = "DELETE FROM Address JOIN MovieTicket WHERE MovieTicket.AddressKey = Address.AddressKey";
		ps = conn.prepareStatement(query);
		
	//Function to delete all product tables relating to products
		ps = null;
		query = "TRUNCATE table MovieTicket";
		ps = conn.prepareStatement(query);
		
		ps = null;
		query = "TRUNCATE table ParkingPass";
		ps = conn.prepareStatement(query);
		
		ps = null;
		query = "TRUNCATE table SeasonPass";
		ps = conn.prepareStatement(query);
		
		ps = null;
		query = "TRUNCATE table Refreshments";
		ps = conn.prepareStatement(query);
		
		ps = null;
		query = "TRUNCATE table Products";
		ps = conn.prepareStatement(query);
		
	//done?
		ps.close();
		conn.close();
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, 
			String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
	//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet  rs = ps.executeQuery();
		
	//Inserting Address First
		String query = "INSERT INTO Address(Street, City, State, Zip, Country) VALUES "
			+ "(?, ?, ?, ?, ?)";
		ps.setString(1, street);
		ps.setString(2, city);
		ps.setString(3, state);
		ps.setString(4, zip);
		ps.setString(5, country);
		ps.execute();
		
	//next, to find the address ID. We can do this by finding the last input address ID from the address we created
		
		query = "Select AddressKey FROM Address WHERE Address.AddressKey = LAST_INSERT_ID";
		int addressID = 999;
		rs =ps.executeQuery();
			
		if(rs.next()){
			addressID = rs.getInt("AddressKey");
		}
		
	//Now for the main insertion:
		query = "INSERT INTO MovieTicket(ProductID, Date, Name, TheatreNum, pricePerUnit, AddressKey) VALUES "
				+ "?, ?, ?, ?, ?, ?";
				
		ps.setString(1, productCode);
		ps.setString(2, dateTime);
		ps.setString(3, movieName);
		ps.setString(4, screenNo);
		ps.setDouble(5, pricePerUnit);
		ps.setString(6, addressID);
		ps.execute();
		
	//Finally, insert the product code into Products:
		query = "INSERT INTO Products(ProductID) VALUES (?)";
		ps.setString(1, addressID);
		
	//done
		rs.close();
		ps.close();
		conn.close();				
	}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, 
			String seasonStartDate, String seasonEndDate,	double cost) {
		
	//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet  rs = ps.executeQuery();
		
	//Main Insertion:	
		String query = "INSERT INTO SeasonPass(ProductID, Name, StartDate, EndDate, Cost) VALUES "
				+ "?, ?, ?, ?, ?";
		
		ps.setString(1, productCode);
		ps.setString(2, name);
		ps.setString(3, seasonStartDate);
		ps.setString(4, seasonEndDate);
		ps.setDouble(5, cost);
		ps.execute();
		
	//Finally, insert the product code into Products:
		query = "INSERT INTO Products(ProductID) VALUES (?)";
		ps.setString(1, addressID);
	
	//done
		rs.close();
		ps.close();
		conn.close();				
	}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		
	//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet  rs = ps.executeQuery();
	
	//Main Insertion:	
		String query = "INSERT INTO ParkingPass(ProductID, Cost) VALUES "
			+ "?, ?";
				
		ps.setString(1, productCode);
		ps.setString(2, name);
		ps.setString(3, seasonStartDate);
		ps.setString(4, seasonEndDate);
		ps.setDouble(5, cost);
		ps.execute();
		
	//Finally, insert the product code into Products:
			query = "INSERT INTO Products(ProductID) VALUES (?)";
			ps.setString(1, addressID);
			
		//done
			rs.close();
			ps.close();
			conn.close();
	}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
	
	//Connect to Database
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet  rs = ps.executeQuery();
			
	//Main Insertion:	
		String query = "INSERT INTO Refreshments(ProductID, Name, Cost) VALUES "
			+ "?, ?, ?";
	}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		//Function to delete all emails from the Emails table, as every email is connected to a person
		String query = "TRUNCATE table Invoices";
		ps = conn.prepareStatement(query);
		
		ps.close();
		conn.close();
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		
	}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {}
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {}

}
