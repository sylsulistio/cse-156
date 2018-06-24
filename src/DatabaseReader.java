import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseReader {
	static final Logger log = LoggerFactory.getLogger(DatabaseReader.class);
	protected static ArrayList<Customer> customers = new ArrayList<Customer>();
	protected static ArrayList<Person> persons = new ArrayList<Person>();
	protected static ArrayList<Product> products = new ArrayList<Product>();
	
	/**
	 * This reads in the database for person values: 
	 * Person ID
	 * Name
	 * Address
	 * Email
	 */
	public static void readPersons() throws SQLException{
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "SELECT * FROM Persons";
		ps = conn.prepareStatement(query);
		ResultSet  rs = ps.executeQuery();
		
		try {
			if (log.isDebugEnabled()) {
				while (rs.next()) {
					String personCode = rs.getString("PersonID");
					String personName = rs.getString("Name");
					Address address = null;
					String emails = null;
					
					//Getting Address values
					query = "SELECT * FROM Address WHERE AddressKey = ?";
					ps = conn.prepareStatement(query);
					ps.setInt(1, rs.getInt("AddressKey"));
					
					ResultSet rs1 = ps.executeQuery();
					if (rs1.next()) {
						String[] array = {rs1.getString("Street"), rs1.getString("City"), rs1.getString("State"), rs1.getString("Zip"), rs1.getString("Country")};
						address = new Address(array);
					}
					
					//Getting email string
					query = "SELECT * FROM Emails WHERE EmailKey = ?";
					ps = conn.prepareStatement(query);
					ps.setInt(1, rs.getInt("EmailKey"));
					
					rs1 = ps.executeQuery();
					if (rs1.next()) {
						emails = rs1.getString("Email");
					}
					
					Person p = new Person(personCode, personName, address, emails);
	
					persons.add(p);
					rs1.close();
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			rs.close();
			conn.close();
		}
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a person contained in the Customers.dat file
	 * @throws SQLException 
	 */
	public static void readCustomers() throws SQLException {
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "SELECT * FROM Customers";
		ps = conn.prepareStatement(query);
		ResultSet  rs = ps.executeQuery();
		
		try {
			while (rs.next()) {
				String custCode = rs.getString("CustomerID");
				String custName = rs.getString("Name");
				Address address = null;
				String persCode = rs.getString("PrimaryContact");
				int type = rs.getInt("Type");
				
				//Getting Address values
				query = "SELECT * FROM Address WHERE AddressKey = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, rs.getInt("AddressKey"));
				
				ResultSet rs1 = ps.executeQuery();
				if (rs1.next()) {
					String[] array = {rs1.getString("Street"), rs1.getString("City"), rs1.getString("State"), rs1.getString("Zip"), rs1.getString("Country")};
					address = new Address(array);
				}
				
				Customer c = null;
				
				if (type == 1) {
					c = new Student(custName, address, custCode, persCode, "S");
				}
				else if (type == 0){
					c = new General(custName, address, custCode, persCode, "G");
				}
				
				customers.add(c);
				rs1.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			rs.close();
			conn.close();
		}
	}
	
	/**
	 * This reads in a file and prints the quantified information
	 * for a product contained in the Products.dat file
	 * @throws SQLException 
	 */
	public static void readProducts() throws SQLException {
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "SELECT * FROM MovieTicket";
		ps = conn.prepareStatement(query);
		ResultSet  rs = ps.executeQuery();
		
		try {
			// Adding all movie tickets
			while (rs.next()) {
				String productCode = rs.getString("ProductID");
				String productName = rs.getString("Name");
				Address address = null;
				String scrnNum = rs.getString("TheatreNum");
				double cost = rs.getDouble("Cost");
				String dateTime = rs.getString("Date");
				
				//Getting Address values
				query = "SELECT * FROM Address WHERE AddressKey = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, rs.getInt("AddressKey"));
				
				ResultSet rs1 = ps.executeQuery();
				if (rs1.next()) {
					String[] array = {rs1.getString("Street"), rs1.getString("City"), rs1.getString("State"), rs1.getString("Zip"), rs1.getString("Country")};
					address = new Address(array);
				}
				
				MovieTicket m = new MovieTicket(productCode, "M", dateTime, productName, address, scrnNum, cost);
				products.add(m);
				rs1.close();
			}
			// End adding movie tickets
			
			// Adding all parking passes
			query = "SELECT * FROM ParkingPass";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				String productCode = rs.getString("ProductID");
				double cost = rs.getDouble("Cost");
				
				ParkingPass p = new ParkingPass(productCode, "P", cost);
				products.add(p);
			}
			// End adding parking passes
			
			// Adding all refreshments
			query = "SELECT * FROM Refreshments";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				String productCode = rs.getString("ProductID");
				String productName = rs.getString("Name");
				double cost = rs.getDouble("Cost");
				
				Refreshment r = new Refreshment(productCode, "R", productName, cost);
				products.add(r);
			}
			// End adding refreshments
			
			// Adding all season passes
			query = "SELECT * FROM SeasonPass";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				String productCode = rs.getString("ProductID");
				String productName = rs.getString("Name");
				String startDate = rs.getString("StartDate");
				String endDate = rs.getString("EndDate");
				double cost = rs.getDouble("Cost");
				
				SeasonPass s = new SeasonPass(productCode, "S", productName, startDate, endDate, cost);
				products.add(s);
			}
			// End adding refreshments
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			rs.close();
			conn.close();
		}
	}
	
	/**
	 * This reads in the constructed ArrayLists as well as 
	 * the Invoice.dat file and prints the quantified information
	 * for an invoice to be stored in the Invoice.txt file
	 * 
	 * @return ArrayList<Invoice>
	 * @throws SQLException 
	 */
	public static void readInvoices() throws SQLException {
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		String query = "SELECT * FROM Invoices";
		ps = conn.prepareStatement(query);
		ResultSet  rs = ps.executeQuery();
		
		Invoice invoice = null;
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		
		try {
			while (rs.next()) {
				String invoiceCode = rs.getString("InvoiceID");
				if (invoiceCode == null) {
					log.debug("Invoice code not found");
				}
				String customerCode = rs.getString("CustomerID");
				if (invoiceCode == null) {
					log.debug("Customer code not found");
				}
				String salesCode = rs.getString("SalespersonID");
				if (invoiceCode == null) {
					log.debug("Person code not found");
				}
				String invoiceDate = rs.getString("Date");
				if (invoiceCode == null) {
					log.debug("Invoice date not found");
				}
				
				// Getting linked ticket, if any
				String linkedTicket = rs.getString("LinkedTicket");
				
				// Getting products for the invoice, starting with movie tickets
				String[] productList = null;
				
				query = "SELECT ProductID, Quantity FROM Purchases p WHERE EXISTS (SELECT ProductID FROM MovieTicket m WHERE p.ProductID = m.ProductID) AND InvoiceID = ?";
				ps.setString(1, invoiceCode);
				
				ResultSet productRs = ps.executeQuery();
				
				
				invoice = new Invoice(invoiceCode, customerCode, salesCode,
						invoiceDate, linkedTicket, productList);
					
				invoices.add(invoice);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			ps.close();
			rs.close();
			conn.close();
		}
		writeInvoiceSummary(invoices);
		writeInvoiceIndividual(invoices);
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
					movieString = ((MovieTicket) p).getMovieDateTime() + " (" + p.getQuantity() + " units @ $" + df.format(p.getCost()) + "/unit)";String isTueThur = "(Tue/Thur 7% off)";
					// If Tue/Thur discount is active, the string appears
					if (((MovieTicket)p).isTueThur()) {
						movieString += isTueThur;
					}
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
					String parkingString = "ParkingPass (" + ((ParkingPass)p).getLink() + ") (" + p.getQuantity() + " unit(s) @ $"
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
					String prorateString = "";
					if (((SeasonPass) p).isProrated()) {
						prorateString = "(Prorated " + ((SeasonPass) p).getTimeLeft() + "/" + ((SeasonPass) p).getActiveTime() + " days)5";
					}
					invoiceString.append(String.format("%-10s%-1s unit(s) @ $%-1.2f/unit + $8.00 fee/unit)%-1s\n", "", "(" + p.getQuantity(), p.getCost(), prorateString ));							
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
	public static void main(String[] args) throws SQLException, FileNotFoundException {
		log.info("Program started");
		
		DatabaseReader.readPersons();
		log.info("Persons read into objects");
		DatabaseReader.readCustomers();
		log.info("Customers read into objects");
		DatabaseReader.readProducts();
		log.info("Products read into objects");
		
		log.info("Reading invoices");
		readInvoices();
		log.info("Program stopping");
	}
}