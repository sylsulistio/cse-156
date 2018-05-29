import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

public class DataConverter {
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Persons.dat file 
	 * @throws IOException 
	 */
	public static void readPersons() throws IOException {
		// Reading in file
		Scanner s = new Scanner(new File("data/Persons.dat"));
		int numPersons = Integer.parseInt(s.nextLine().trim());
		
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
				// if none
				emailArray = null;
			}
			else if (personInfo[3].contains(",")) {
				// if more than one
				emailArray = personInfo[3].split(",");
				ArrayList<String> dynamicEmailArr = new ArrayList<String>();
				// checks each email address for validity
				for (int j = 0; j < emailArray.length; j++) {
					if (emailArray[j].contains("@")) {
						dynamicEmailArr.add(emailArray[j]);
					}
				}
				// casts ArrayList back into a String[] to pass into the constructor
				emailArray = dynamicEmailArr.toArray(new String[0]);
			}
			else if (personInfo[3].contains("@")){
				// if only one
				emailArray[0] = personInfo[3];
			}
			
			Person person = new Person(personCode, personName, personAddress, emailArray);
			persons.add(person);
		}
		
		// Printing out objects
		// JSON printer
		FileWriter print = new FileWriter("data/Persons.json");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = "{ persons: " + gson.toJson(persons) + "}";
		print.write(json);
		
		// XML printer
		FileWriter xmlPrint = new FileWriter("data/Persons.xml");
		StringBuilder xmlStr = new StringBuilder();
		XStream xstream = new XStream();
		for (Person p: persons) {
			xmlStr.append("\n");
			String personAsXML = xstream.toXML(p);
			xmlStr.append(personAsXML);
		}
		String finalString = xmlStr.toString();
		xmlPrint.write(finalString);
		
		xmlPrint.close();
		print.close();
		s.close();
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Customers.dat file
	 * @throws IOException 
	 */
	public static void readCustomers() throws IOException {
		// Reading in file
		Scanner s = new Scanner(new File("data/Customers.dat"));
		int numCustomers = Integer.parseInt(s.nextLine().trim());
		
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
		
		// Printing out objects
		// JSON printer
		FileWriter print = new FileWriter("data/Customers.json");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = "{ customers: " + gson.toJson(customers) + "}";
		print.write(json);
		
		// XML printer
		FileWriter xmlPrint = new FileWriter("data/Customers.xml");
		StringBuilder xmlStr = new StringBuilder();
		XStream xstream = new XStream();
		for (Customer p: customers) {
			xmlStr.append("\n");
			String customerAsXML = xstream.toXML(p);
			xmlStr.append(customerAsXML);
		}
		String finalString = xmlStr.toString();
		xmlPrint.write(finalString);
		
		xmlPrint.close();
		print.close();
		s.close();
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a product contained in the Products.dat file
	 * @throws IOException 
	 */
	public static void readProducts() throws IOException {
		// Reading in file
		Scanner s = new Scanner(new File("data/Products.dat"));
		int numSales = Integer.parseInt(s.nextLine().trim());
		
		String salesCode;
		char salesType;
		String salesStartDate;
		String salesEndDate;
		String salesDateTime;
		String salesName;
		String[] addressArray;
		Address salesAddress;
		String salesSeat;
		double salesCost = 0;
		Product product = new Product();
		
		ArrayList<Product> products = new ArrayList<Product>();
		
		for (int i = 0; i < numSales; i++) {
			String line = (s.nextLine().trim());
			String[] salesInfo = line.split(";");
			boolean isValid = true; // Boolean to validate item
			
			switch((salesInfo[1]).charAt(0)) {
				// Movie tickets
				case 'M':
					salesCode = salesInfo[0];
					salesType = (salesInfo[1]).charAt(0);
					salesDateTime = salesInfo[2];
					salesName = salesInfo[3];
					addressArray = salesInfo[4].split(",");
					salesAddress = new Address(addressArray);
					salesSeat = salesInfo[5];
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[6]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new Product(salesCode, salesType, salesDateTime, salesName,
							salesAddress, salesSeat, salesCost);
					break;
					
				// Season pass
				case 'S':
					salesCode = salesInfo[0];
					salesType = (salesInfo[1]).charAt(0);
					salesName = salesInfo[2];
					salesStartDate = salesInfo[3];
					salesEndDate = salesInfo[4];
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[5]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new Product(salesCode, salesType, salesName,
							salesStartDate, salesEndDate, salesCost);
					break;
					
				// Parking passes
				case 'P':
					salesCode = salesInfo[0];
					salesType = (salesInfo[1]).charAt(0);
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[2]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new Product(salesCode, salesType,salesCost);
					break;
					
				// Refreshments
				case 'R':
					salesCode = salesInfo[0];
					salesType = (salesInfo[1]).charAt(0);
					salesName = salesInfo[2];
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[3]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new Product(salesCode, salesType, salesName,
							salesCost);
					break;	
				
				// Invalid product would be anything but the provided characters
				default:
					// System output to indicate which product is invalid
					System.out.println("Invalid product type! Code: " + salesInfo[0]);
					isValid = false;
					break;
			}
			
			// Item is not added if the product type is invalid
			if (isValid){
				products.add(product);
			}
		}
		// Printing out objects
		// JSON printer
		FileWriter print = new FileWriter("data/Products.json");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = "{ products: " + gson.toJson(products) + "}";
		print.write(json);
		
		// XML printer
		FileWriter xmlPrint = new FileWriter("data/Products.xml");
		StringBuilder xmlStr = new StringBuilder();
		XStream xstream = new XStream();
		for (Product p: products) {
			xmlStr.append("\n");
			String productAsXML = xstream.toXML(p);
			xmlStr.append(productAsXML);
		}
		String finalString = xmlStr.toString();
		xmlPrint.write(finalString);
		
		print.close();
		xmlPrint.close();
		s.close();
	}
	
	public static void main(String[] args) throws IOException {
		DataConverter.readPersons();
		DataConverter.readCustomers();
		DataConverter.readProducts();
	}
}