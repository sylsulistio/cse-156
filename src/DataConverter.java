import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataConverter {
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Persons.dat file 
	 * 
	 * @throws FileNotFoundException
	 */
	public static void readPersons() throws FileNotFoundException {
		System.out.println("person:\n--------------------------------------------------");
		// Reading in file
		Scanner s = new Scanner(new File("data/Persons.dat"));
		int numPersons = Integer.parseInt(s.nextLine().trim());	
		
//		for (int i = 0; i < numPersons; i++) {
//			String line = (s.nextLine().trim());
//			String[] personInfo = line.split(";");
//
//			StringBuilder personString = new StringBuilder();
//			personString.append("Person: " + (i+1) + "\n");
//			personString.append("Person code: " + personInfo[0] + "\n");
//			// Name
//			String[] names = personInfo[1].split(",");
//			personString.append("First name: " + names[1] + "\n");
//			personString.append("Last name: " + names[0] + "\n");
//			// Address
//			String[] addressParts = personInfo[2].split(",");
//			personString.append("Street: " + addressParts[0] + "\n");
//			personString.append("City: " + addressParts[1] + "\n");
//			personString.append("State: " + addressParts[2] + "\n");
//			personString.append("Zip Code: " + addressParts[3] + "\n");
//			personString.append("Country: " + addressParts[4] + "\n");
//			// are there emails then?
//			// If yes
//			if(personInfo.length == 4) {
//				personString.append("Person email(s): ");
//				String[] personEmailArray = personInfo[3].split(",");
//				// For each email, start a new line
//				for (String email: personEmailArray) {
//					personString.append(email + "\n");
//				}
//			
//			} 
//			// If no
//			else {
//				personString.append("Person emails: none\n");
//			}
//			
//			System.out.println(personString);
//		}
		
		ArrayList<Person> persons = new ArrayList<Person>();
		
		for (int i = 0; i < numPersons; i++) {
			String line = (s.nextLine().trim());
			String[] personInfo = line.split(";");
			// Identification
			String personCode = personInfo[0];
			String personName = personInfo[1];
			String[] addressArray = personInfo[2].split(",");
			Address personAddress = new Address(addressArray);
			String[] emailArray = new String[1];
			// Email check
			if (personInfo.length < 4) {
				emailArray = null;
			}
			else if (personInfo[3].contains(",")) {
				// if more than one
				emailArray = personInfo[3].split(",");
			}
			else if (personInfo[3].contains("@")){
				// if only one
				emailArray[0] = personInfo[3];
			}
			
			Person person = new Person(personCode, personName, personAddress, emailArray);
			persons.add(person);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = "{ customers: " + gson.toJson(persons) + "}";
		System.out.println(json);
		
		s.close();
		System.out.println("--------------------------------------------------");
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Customers.dat file
	 * 
	 * @throws FileNotFoundException
	 */
	public static void readCustomers() throws FileNotFoundException {
		System.out.println("customers:\n-------------------------------------------------");
		// Reading in file
		Scanner s = new Scanner(new File("data/Customers.dat"));
		int numCustomers = Integer.parseInt(s.nextLine().trim());
		StringBuilder customerString = new StringBuilder();
		
		// Currently, this chunk is commented out so I can test the object and JSON output
		
//		for (int i = 0; i < numCustomers; i++) {
//			String line = (s.nextLine().trim());
//			String[] customerInfo = line.split(";");
//			// Identification
//			customerString.append("Customer: " + (i+1) + "\n");
//			customerString.append("Customer Code: " + customerInfo[0] + "\n");
//			customerString.append("Customer Type: " + customerInfo[1] + "\n");
//			customerString.append("Customer Primary Contact: " + customerInfo[2] + "\n");
//			customerString.append("Customer Name: " + customerInfo[3] + "\n");
//			// Address
//			String[] addressParts = customerInfo[4].split(",");
//			customerString.append("Street: " + addressParts[0] + "\n");
//			customerString.append("City: " + addressParts[1] + "\n");
//			customerString.append("State: " + addressParts[2] + "\n");
//			customerString.append("Zip Code: " + addressParts[3] + "\n");
//			customerString.append("Country: " + addressParts[4] + "\n");
//			
//			System.out.println(customerString);
//		}
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		for (int i = 0; i < numCustomers; i++) {
			String line = (s.nextLine().trim());
			String[] customerInfo = line.split(";");
			// Identification
			String customerCode = customerInfo[0];
			char customerType = (customerInfo[1]).charAt(0);
			String customerContact = customerInfo[2];
			String customerName = customerInfo[3];
			String[] addressArray = customerInfo[4].split(",");
			Address customerAddress = new Address(addressArray);
			
			Customer customer = new Customer(customerName, customerAddress, customerCode, 
					customerContact, customerType);
			customers.add(customer);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = "{ customers: " + gson.toJson(customers) + "}";
		System.out.println(json);
		s.close();
		System.out.println("--------------------------------------------------");
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a product contained in the Products.dat file
	 * 
	 * @throws FileNotFoundException
	 */
	public static void readProducts() throws FileNotFoundException {
		System.out.println("products:\n--------------------------------------------------");
		// Reading in file
		Scanner s = new Scanner(new File("data/Products.dat"));
		int numSales = Integer.parseInt(s.nextLine().trim());
		
		for (int i = 0; i < numSales; i++) {
			String line = (s.nextLine().trim());
			String[] salesInfo = line.split(";");
			
			StringBuilder salesString = new StringBuilder();
			salesString.append("Product: " + (i+1) + "\n");
			salesString.append("Product Code: " + salesInfo[0] + "\n");
			salesString.append("Product Code: " + salesInfo[1] + "\n");
			char type = salesInfo[1].charAt(0);
			// Switch case to change number of fields required per type of product
			switch(type) {
				// Movie tickets
				case 'M':
					// Movie information
					String[] dateTime = salesInfo[2].split(" ");
					salesString.append("Date: " + dateTime[0] + "\n");
					salesString.append("Time: " + dateTime[1] + "\n");
					salesString.append("Movie Name: " + salesInfo[3] + "\n");
					// Theatre address
					String[] addressParts = salesInfo[4].split(",");
					salesString.append("Theatre Street: " + addressParts[0] + "\n");
					salesString.append("Theatre City: " + addressParts[1] + "\n");
					salesString.append("Theatre State Abrev: " + addressParts[2] + "\n");
					salesString.append("Theatre Zipcode: " + addressParts[3] + "\n");
					salesString.append("Theatre Country: " + addressParts[4] + "\n");
					// More movie information
					salesString.append("Screen Number: " + salesInfo[5] + "\n");
					salesString.append("Price per Unit: " + salesInfo[6] + "\n");
					break;
				// Season pass
				case 'S':
					salesString.append("Pass Name: " + salesInfo[2] + "\n");
					salesString.append("Start Date: " + salesInfo[3] + "\n");					
					salesString.append("End Date: " + salesInfo[4] + "\n");					
					salesString.append("Price: $" + salesInfo[5] + "\n");
					break;
				// Parking passes
				case 'P':
					salesString.append("Price of Parking: $" + salesInfo[2] + "\n");
					break;
				// Refreshments
				case 'R':
					salesString.append("Refreshment: " + salesInfo[2] + "\n");
					salesString.append("Price: $" + salesInfo[3] + "\n");
					break;					
			}			
			System.out.println(salesString);
		}
		s.close();
		System.out.println("--------------------------------------------------");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		DataConverter.readPersons();
		//DataConverter.readCustomers();
		//DataConverter.readProducts();
	}
}
