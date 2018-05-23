import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataConverter {
	
	/*
	What we need:
	* A list of sales including
	** Tickets
	*** Movie tickets
	*** Season Passes
	** Sub Offerings
	*** Parking Passes
	*** Refreshment Preorders
	*
	* Invoices-information regarding the sale
	** A unique alphanumeric code identifying the invoice
	** Customer's given name
	** Salesperson responsible
	** A number of products sold for that individual order, including fees
	*
	*
	* Type of Customer
	** Students, who get an 8% discount and are exempt from taxes. $6.75 to validate student's status
	** General, who don't that at all, suckers. Tickets contain 6% sales tax, services are 4%	
	*/
	/**
	 * 	This reads in a file and prints the quantified information
	 * for a person contained in the Person.dat file 
	 * 
	 * @throws FileNotFoundException
	 */
	
	
	public static void readPersons() throws FileNotFoundException {
		//read three files
		System.out.println("person:\n--------------------------------------------------");
		Scanner s = new Scanner(new File("data/Persons.dat"));
		int numPersons = Integer.parseInt(s.nextLine().trim());	
		
		for (int i = 0; i < numPersons; i++) {
			String line = (s.nextLine().trim());
			String[] personInfo = line.split(";");
			
			StringBuilder personString = new StringBuilder();
			personString.append("Person: " + (i+1) + "\n");
			personString.append("Person code: " + personInfo[0] + "\n");
			
			String[] names = personInfo[1].split(",");
			personString.append("First name: " + names[1] + "\n");
			personString.append("Last name: " + names[0] + "\n");
			
			String[] addressParts = personInfo[2].split(",");
			personString.append("Street: " + addressParts[0] + "\n");
			personString.append("City: " + addressParts[1] + "\n");
			personString.append("State: " + addressParts[2] + "\n");
			personString.append("Zip Code: " + addressParts[3] + "\n");
			personString.append("Country: " + addressParts[4] + "\n");
			// are there emails then?
			if(personInfo.length == 4) {
				personString.append("person emails: " + personInfo[3] + "\n");
			
			} 
			else {
				personString.append("person emails: none\n");
			}
			
			System.out.println(personString);
		}
		s.close();
		System.out.println("--------------------------------------------------");
	}
	public static void readCustomers() throws FileNotFoundException {
		System.out.println("customers:\n-------------------------------------------------");
		Scanner s = new Scanner(new File("data/Customers.dat"));
		int numCustomers = Integer.parseInt(s.nextLine().trim());
		
		for (int i = 0; i < numCustomers; i++) {
			//C001;G;231;Clark Consultants;259 Concorde Suites,Lincoln,NE,68588-0115,USA
			
			String line = (s.nextLine().trim());
			String[] customerInfo = line.split(";");
		
			StringBuilder customerString = new StringBuilder();
			customerString.append("Customer: " + (i+1) + "\n");
			customerString.append("Customer Code: " + customerInfo[0] + "\n");
			customerString.append("Customer Type: " + customerInfo[1] + "\n");

			customerString.append("Customer Primary Contact: " + customerInfo[2] + "\n");
			customerString.append("Customer Name: " + customerInfo[3] + "\n");
			
			String[] addressParts = customerInfo[4].split(",");
			customerString.append("Street: " + addressParts[0] + "\n");
			customerString.append("City: " + addressParts[1] + "\n");
			customerString.append("State: " + addressParts[2] + "\n");
			customerString.append("Zip Code: " + addressParts[3] + "\n");
			customerString.append("Country: " + addressParts[4] + "\n");
			
			System.out.println(customerString);
		}
		
		s.close();
		System.out.println("--------------------------------------------------");
	}
	
	public static void readProducts() throws FileNotFoundException {
		System.out.println("products:\n--------------------------------------------------");
		
		Scanner s = new Scanner(new File("data/Products.dat"));
		int numSales = Integer.parseInt(s.nextLine().trim());
		
		for (int i = 0; i < numSales; i++) {
			/*
			 * b29e;S;Winter Special;2016-12-13;2017-01-07;120.00
			 * ff23;R;Labatt Beer-20oz;4.99
			 * fp12;M;2016-10-21 13:10;A Monster Calls;3555 South 140th Plaza,Omaha, NE, 68144, USA;2A;21.50
			 * 90fa;P;25.00
			 */
			String line = (s.nextLine().trim());
			String[] salesInfo = line.split(";");
			
			StringBuilder salesString = new StringBuilder();
			salesString.append("Product: " + (i+1) + "\n");
			salesString.append("Product Code: " + salesInfo[0] + "\n");
			salesString.append("Product Code: " + salesInfo[1] + "\n");
			char type = salesInfo[1].charAt(0);
			switch(type) {
				case 'M':
					String[] dateTime = salesInfo[2].split(" ");
					salesString.append("Date: " + dateTime[0] + "\n");
					salesString.append("Time: " + dateTime[1] + "\n");
					
					salesString.append("Movie Name: " + salesInfo[3] + "\n");
		
					String[] addressParts = salesInfo[4].split(",");
					salesString.append("Theatre Street: " + addressParts[0] + "\n");
					salesString.append("Theatre City: " + addressParts[1] + "\n");
					salesString.append("Theatre State Abrev: " + addressParts[2] + "\n");
					salesString.append("Theatre Zipcode: " + addressParts[3] + "\n");
					salesString.append("Theatre Country: " + addressParts[4] + "\n");
					
					salesString.append("Screen Number: " + salesInfo[5] + "\n");
					
					salesString.append("Price per Unit: " + salesInfo[6] + "\n");
					break;
				case 'S':
					salesString.append("Pass Name: " + salesInfo[2] + "\n");
					salesString.append("Start Date: " + salesInfo[3] + "\n");					
					salesString.append("End Date: " + salesInfo[4] + "\n");					
					salesString.append("Price: $" + salesInfo[5] + "\n");
					break;
				case 'P':
					salesString.append("Price of Parking: $" + salesInfo[2] + "\n");
					break;
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
		//DataConverter.readPersons();
		//DataConverter.readCustomers();
		DataConverter.readProducts();
	}
}
