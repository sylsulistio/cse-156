import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class DataConverter {
	
	protected static ArrayList<Customer> customers;
	protected static ArrayList<Person> persons;
	protected static ArrayList<Product> products;
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Persons.dat file 
	 * @throws IOException 
	 */
	public static void readPersons() throws IOException {
		// Reading in file
		Scanner s = new Scanner(new File("data/Persons.dat"));
		int numPersons = Integer.parseInt(s.nextLine().trim());
		
		persons = new ArrayList<Person>();
		
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
		
		s.close();
		
		// Printing out objects
		// JSON printer
//		FileWriter print = new FileWriter("data/Persons.json");
//		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = "{ persons: " + gson.toJson(persons) + "}";
//		print.write(json);
//		
//		// XML printer
//		FileWriter xmlPrint = new FileWriter("data/Persons.xml");
//		StringBuilder xmlStr = new StringBuilder();
//		xmlStr.append("<?xml version=\"1.0\"?>\n<Persons>");
//		XStream xstream = new XStream();
//		for (Person p: persons) {
//			xmlStr.append("\n");
//			String personAsXML = xstream.toXML(p);
//			xmlStr.append(personAsXML);
//		}
//		xmlStr.append("</Persons>");
//		String finalString = xmlStr.toString();
//		xmlPrint.write(finalString);
//		
//		xmlPrint.close();
//		print.close();
//		s.close();
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
		
		customers = new ArrayList<Customer>();
		
		for (int i = 0; i < numCustomers; i++) {
			String line = (s.nextLine().trim());
			String[] customerInfo = line.split(";");
			
			// Identification
			String customerCode = customerInfo[0];
			String customerType = customerInfo[1];
			String customerContact = customerInfo[2];
			String customerName = customerInfo[3];
			String[] addressArray = customerInfo[4].split(",");
			Address customerAddress = new Address(addressArray);
			
			Customer customer;
			if (customerType.equals("S")) {
				customer = new Student(customerName, customerAddress, customerCode, 
						customerContact, customerType);
				customers.add(customer);
			}
			else if (customerType.equals("G")) {
				customer = new General(customerName, customerAddress, customerCode, 
						customerContact, customerType);
				customers.add(customer);
			}
		}
		
		s.close();
		
		// Printing out objects
		// JSON printer
//		FileWriter print = new FileWriter("data/Customers.json");
//		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = "{ customers: " + gson.toJson(customers) + "}";
//		print.write(json);
//		
//		// XML printer
//		FileWriter xmlPrint = new FileWriter("data/Customers.xml");
//		StringBuilder xmlStr = new StringBuilder();
//		xmlStr.append("<?xml version=\"1.0\"?>\n<Customers>");
//		XStream xstream = new XStream();
//		for (Customer p: customers) {
//			xmlStr.append("\n");
//			String customerAsXML = xstream.toXML(p);
//			xmlStr.append(customerAsXML);
//		}
//		xmlStr.append("</Customers>");
//		String finalString = xmlStr.toString();
//		xmlPrint.write(finalString);
//		
//		xmlPrint.close();
//		print.close();
//		s.close();
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
		String salesType;
		String salesStartDate;
		String salesEndDate;
		String salesDateTime;
		String salesName;
		String[] addressArray;
		Address salesAddress;
		String salesScrnNum;
		double salesCost = 0;
		Product product = null;
		
		products = new ArrayList<Product>();
		
		for (int i = 0; i < numSales; i++) {
			String line = (s.nextLine().trim());
			String[] salesInfo = line.split(";");
			boolean isValid = true; // Boolean to validate item
			
			switch((salesInfo[1]).charAt(0)) {
				// Movie tickets
				case 'M':
					salesCode = salesInfo[0];
					salesType = "" + (salesInfo[1]).charAt(0);
					salesDateTime = salesInfo[2];
					salesName = salesInfo[3];
					addressArray = salesInfo[4].split(",");
					salesAddress = new Address(addressArray);
					salesScrnNum = salesInfo[5];
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[6]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new MovieTicket(salesCode, salesType, salesDateTime, salesName,
							salesAddress, salesScrnNum, salesCost);
					break;
					
				// Season pass
				case 'S':
					salesCode = salesInfo[0];
					salesType = "" + (salesInfo[1]).charAt(0);
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
					
					product = new SeasonPass(salesCode, salesType, salesName,
							salesStartDate, salesEndDate, salesCost);
					break;
					
				// Parking passes
				case 'P':
					salesCode = salesInfo[0];
					salesType = "" + (salesInfo[1]).charAt(0);
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[2]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new ParkingPass(salesCode, salesType,salesCost);
					break;
					
				// Refreshments
				case 'R':
					salesCode = salesInfo[0];
					salesType = "" + (salesInfo[1]).charAt(0);
					salesName = salesInfo[2];
					// try-catch in case the number is invalid
					try {
						salesCost = Double.parseDouble(salesInfo[3]);
					}
					catch (Exception e) {
						System.out.println("Invalid product cost! Code: " + salesCode);
					}
					
					product = new Refreshment(salesCode, salesType, salesName,
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
//		FileWriter print = new FileWriter("data/Products.json");
//		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = "{ products: " + gson.toJson(products) + "}";
//		print.write(json);
//		
//		// XML printer
//		FileWriter xmlPrint = new FileWriter("data/Products.xml");
//		StringBuilder xmlStr = new StringBuilder();
//		xmlStr.append("<?xml version=\"1.0\"?>\n<Products>");
//		XStream xstream = new XStream();
//		for (Product p: products) {
//			xmlStr.append("\n");
//			String productAsXML = xstream.toXML(p);
//			xmlStr.append(productAsXML);
//		}
//		xmlStr.append("</Products>");
//		String finalString = xmlStr.toString();
//		xmlPrint.write(finalString);
//		
//		print.close();
//		xmlPrint.close();
//		s.close();
	}
	
	/**
	 * This reads in the constructed ArrayLists as well as 
	 * the Invoice.dat file and prints the quantified information
	 * for an invoice to be stored in the Invoice.txt file
	 * 
	 * @throws FileNotFoundException
	 * @return ArrayList<Invoice>
	 */
	 public static void readInvoices() throws FileNotFoundException {
		Scanner s = new Scanner(new File("data/Invoices.dat"));
		int numInvoices = Integer.parseInt((s.nextLine()).trim());
		
		String invoiceCode;
		String customerCode;
		String salesCode;
		String invoiceDate;
		String[] productList;
		Invoice invoice = null;
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		
		for (int i = 0; i < numInvoices; i++) {
			String line = (s.nextLine().trim());
			String[] invoiceInfo = line.split(";");
			
			invoiceCode = invoiceInfo[0];
			customerCode = invoiceInfo[1];
			salesCode = invoiceInfo[2];
			invoiceDate = invoiceInfo[3];
			productList = invoiceInfo[4].split(",");
			invoice = new Invoice(invoiceCode, customerCode, salesCode,
				invoiceDate, productList);
			
			invoices.add(invoice);
		}
		writeInvoiceSummary(invoices);
		writeInvoiceIndividual(invoices);

		s.close();
	}
	
	public static void writeInvoiceSummary(ArrayList<Invoice> invoices) {
		StringBuilder invoiceString = new StringBuilder();
		
		invoiceString.append("========================\n"
				   + "Executive Summary Report\n"
				   + "========================\n"
				   + String.format("%-10s%-35s%-20s%-10s%-10s%-10s%-10s%-10s\n",
				   "Invoice", "Customer", "Salesperson", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		
		// Values for TOTALS row 
		double totalSubtotal = 0;
		double totalFees = 0;
		double totalTaxes = 0;
		double totalDiscount = 0;
		double totalTotal = 0;
		 
		// DecimalFormat object for string construction
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		for (int i = 0; i < invoices.size(); i++) {
			String invCode = invoices.get(i).getInvoiceCode();
			String custName = invoices.get(i).getCustomer().getName() + " [" + invoices.get(i).getCustomer().getType() + "]";
			String persName = invoices.get(i).getPerson().getName();
			double subtotal = invoices.get(i).getSubtotal();
			double fees = invoices.get(i).getFees();
			double taxes = invoices.get(i).getTaxes();
			double discount = invoices.get(i).getDiscount();
			double total = invoices.get(i).getTotal();
			
			totalSubtotal += subtotal;
			totalFees += fees;
			totalTaxes += taxes;
		 	totalDiscount += discount;
		 	totalTotal += total;
			
			invoiceString.append(String.format("%-10s%-35s%-20s%-10s%-10s%-10s%-10s%-10s\n",
			invCode, custName, persName, "$" + df.format(subtotal), "$" + df.format(fees), "$" + df.format(taxes), "$-" + df.format(discount), "$" + df.format(total)));
		}
		
		invoiceString.append(String.format("%-65s%-10.2f%-10.2f%-10.2f%-10.2f%-10.2f",
			"TOTALS", totalSubtotal, totalFees, totalTaxes, totalDiscount, totalTotal));
		
		System.out.println(invoiceString + "\n");
	}
				     
	public static void writeInvoiceIndividual(ArrayList<Invoice> invoices) {
		StringBuilder invoiceString = new StringBuilder();
		invoiceString.append("Individual Invoice Detail Reports\n" + "==================================\n");
		for (int i = 0; i < invoices.size(); i++) {
			String invCode = invoices.get(i).getInvoiceCode();
			String custName = invoices.get(i).getCustomer().getName() + " [" + invoices.get(i).getCustomer().getType() + "]";
			String custCode = "(" + invoices.get(i).getCustomer().getCode() + ")";		
			String custPersonName = invoices.get(i).getCustPerson().getName();
			String custAddress = invoices.get(i).getCustomer().getAddress().getStreet();
			String custAddress2 = invoices.get(i).getCustomer().getAddress().getLine2();
			
			double subtotal = invoices.get(i).getSubtotal();
			double fees = invoices.get(i).getFees();
			double taxes = invoices.get(i).getTaxes();
			double discount = invoices.get(i).getDiscount();
			double total = invoices.get(i).getTotal();
			
			// String values for string construction
			String subtotalString = "";
			String taxesString = "";
			String totalString = "";
			String discountString = "";
			
			// DecimalFormat object for string construction
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			
			invoiceString.append("Invoice " + invCode);
			invoiceString.append("\n====================\n");
			
			invoiceString.append(String.format("\nCustomer Info: \n"));
			invoiceString.append(String.format("    %-5s\n", custName));
			invoiceString.append(String.format("    %-5s\n", custCode));
			invoiceString.append(String.format("    %-5s\n", custPersonName));
			invoiceString.append(String.format("    %-5s\n", custAddress));
			invoiceString.append(String.format("    %-5s\n", custAddress2));
			
			invoiceString.append(String.format("-------------------------------------------\n"));
			invoiceString.append(String.format("%-10s%-65s %-15s%-10s%-15s\n",
					   "Code", "Item", "Subtotal", "Tax", "Total"));
			
			ArrayList<Product> productArray = invoices.get(i).getProducts();
			
			for (Product p: productArray) {
				if (p instanceof MovieTicket) {
					subtotal = p.getCost()*p.getQuantity();
					subtotalString = "$" + df.format(subtotal);
					taxes = subtotal*0.06;
					taxesString = "$" + df.format(taxes);
					totalString = "$" + df.format(subtotal + taxes);
					String movieString = "MovieTicket '" + ((MovieTicket) p).getName() + "' @ " + ((MovieTicket)p).getAddress().getStreet();
					invoiceString.append(String.format("%-10s%-65s %-15s%-10s%-15s\n", p.getCode(), movieString, subtotalString, 
																					   taxesString, totalString));
					movieString = ((MovieTicket) p).getMovieDateTime() + " (" + p.getQuantity() + " units @ $" + df.format(p.getCost()) + "/unit)";
					invoiceString.append(String.format("%-10s%-65s\n", "", movieString));
				}
				else if (p instanceof ParkingPass) {
					int freeNum = 0;
					subtotal = p.getCost()*p.getQuantity();
					double initialCost = p.getCost();
					String freeParking = "";
					double parkingAmount = invoices.get(i).getParkingDiscount();
					// If there is a parking discount at all
					if (parkingAmount > 0) {
						freeNum = (int)(parkingAmount/p.getCost());
						freeParking = "(" + freeNum + " free)";
					}
					// Sets the parking amount to how much the customer would actually pay after discount and taxes
					parkingAmount = subtotal + taxes - parkingAmount;
					// If the parking discount is higher than what they actually used, everything is set to zero
					if (parkingAmount <= 0) {
						// Sets boolean in object to false for later use
						invoices.get(i).setParkingDeficit(false);
						parkingAmount = 0;
						p.setCost(0);
						subtotal = 0;
					}
					// Else make the subtotal of parking whatever the customer would pay before taxes
					else {
						subtotal = parkingAmount - taxes;
					}
					subtotalString = "$" + df.format(subtotal);
					taxes = subtotal*0.04;
					taxesString = "$" + df.format(taxes);
					totalString = "$" + df.format(subtotal + taxes);
					String parkingString = "ParkingPass (" + ((ParkingPass)p).getLicense() + ") (" + p.getQuantity() + " unit(s) @ $"
											+ df.format(initialCost) + "/unit)" + freeParking; //freeParking string only shows if there is indeed free parking
					invoiceString.append(String.format("%-10s%-65s %-15s%-10s%-15s\n", 
							p.getCode(), parkingString, subtotalString, taxesString, totalString));
				}
				else if (p instanceof SeasonPass) {
					subtotal = (p.getCost()+8)*p.getQuantity();
					subtotalString = "$" + df.format(subtotal);
					taxes = subtotal*0.06;
					taxesString = "$" + df.format(taxes);
					totalString = "$" + df.format(subtotal + taxes);
					invoiceString.append(String.format("%-10s%-65s %-15s%-10s%-15s\n", p.getCode(), "SeasonPass - " + ((SeasonPass)p).getName(), subtotalString, taxesString, totalString));
					invoiceString.append(String.format("%-10s%-1s unit(s) @ $%-1.2f/unit + $8.00 fee/unit)\n", "", "(" + p.getQuantity(), p.getCost()));							
				}
				else if (p instanceof Refreshment) {
					String refreshmentString = ((Refreshment) p).getName() + " (" + p.getQuantity() + " units @ $" + df.format(p.getCost()) + "/unit)";
					subtotal = p.getCost()*p.getQuantity();
					subtotalString = "$" + df.format(subtotal);
					taxes = subtotal*0.04;
					taxesString = "$" + df.format(taxes);
					totalString = "$" + df.format(subtotal + taxes);
					if (invoices.get(i).hasTicket()) {
						refreshmentString += "(5% off)";
					}
					invoiceString.append(String.format("%-10s%-65s %-15s%-10s%-15s\n", 
							p.getCode(), refreshmentString, subtotalString, taxesString, totalString));
				}
			}
			// Divider between list of products and final calculations
			invoiceString.append(String.format("====================================\n"));
			
			subtotal = invoices.get(i).getSubtotal();
			subtotalString = "$" + df.format(subtotal);
			String feesString = "$" + df.format(invoices.get(i).getFees());
			// if there is no parking deficit, subtract parking tax
			// from set taxes
			if (!invoices.get(i).getParkingDeficit()) {
				invoices.get(i).setTaxes(invoices.get(i).getParkingTaxes());
			}
			taxes = invoices.get(i).getTaxes();
			taxesString = "$" + df.format(taxes);
			discount = invoices.get(i).getDiscount();
			discountString = "$-" + df.format(discount);
			totalString = "$" + df.format(subtotal + taxes);
			
			invoiceString.append(String.format("%-75s %-15s%-10s%-15s\n", "SUB-TOTALS", subtotalString, taxesString, totalString));
			
			// Reformatting totalString to include fees and discounts where applicable
			total = invoices.get(i).getTotal();
			totalString = "$" + df.format(total);
			
			// If the customer is a student
			if (invoices.get(i).getCustomer() instanceof Student) {
				discount = invoices.get(i).getDiscount() + invoices.get(i).getTaxes();
				discountString = "$-" + df.format(discount);
				invoiceString.append(String.format("%-100s %-15s\n", "DISCOUNT: (STUDENT & NO TAX)", discountString));
				invoiceString.append(String.format("%-100s %-15s\n", "ADDITIONAL FEE (STUDENT)", feesString));
			}
			else {
				invoiceString.append(String.format("%-100s %-15s\n", "DISCOUNT", discountString));
				invoiceString.append(String.format("%-100s %-15s\n", "ADDITIONAL FEE (SEASON PASS)", feesString));
			}
			
			invoiceString.append(String.format("%-100s %-15s\n\n\n", "TOTALS", totalString));
			invoiceString.append("Thank you for your purchase!\n-------------------------\n\n\n");
		}
		System.out.println(invoiceString);
	}
	public static void main(String[] args) throws IOException {
		DataConverter.readPersons();
		DataConverter.readCustomers();
		DataConverter.readProducts();
		
		readInvoices();
	}
}