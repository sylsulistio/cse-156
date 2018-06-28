
import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jvc.ext.InvoiceData;

public class Invoice extends DatabaseReader {
	static final Logger log = LoggerFactory.getLogger(InvoiceData.class);
	private String invoiceCode;
	private String invoiceDate;
	private double subtotal;
	private double fees;
	private double taxes;
	private double discount;
	private double total;
	private ArrayList<Product> invoiceProducts = new ArrayList<Product>();
	private boolean hasTicket;
	private double parkingDiscount;
	private Customer customer;
	private Person person;
	private Person custPerson;
	private double parkingTaxes;
	private boolean parkingDeficit;
	private Product linkedTicket;

	//set all the information
	public Invoice(String invoiceCode, String customerCode, String persCode, 
		String invoiceDate, String linkedTicket, ArrayList<Product> products) {
		parkingDiscount = 0;
		this.invoiceCode = invoiceCode;
		this.setCustomer(customerCode);
		this.setPerson(persCode);
		this.setInvoiceDate(invoiceDate);
		this.setLinkedTicket(linkedTicket);
		this.setProducts(products);
	}
	
	//getters and setters
	//invoice code
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
		log.info("Invoice code set");
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(String customerCode) {
		for (Customer c: customers) {
			if (customerCode.equals(c.getCode())) {
				this.customer = c;
				setCustPerson(c.getContact());
				log.info("Customer set");
				break;
			}
		}
		if (this.customer == null) {
			log.error("Customer not found!");
		}
	}

	//sales code
	public Person getPerson() {
		return person;
	}
	public void setPerson(String persCode) {
		for (Person p: persons) {
			if (persCode.equals(p.getCode())) {
				this.person = p;
				log.info("Salesperson set");
				break;
			}
		}
		if (this.person == null) {
			log.error("Salesperson not found!");
		}
	}

	public Person getCustPerson() {
		return custPerson;
	}
	
	public void setCustPerson(String custPersonCode) {
		for (Person p: persons) {
			if (custPersonCode.equals(p.getCode())) {
				this.custPerson = p;
				log.info("Primary contact set");
				break;
			}
		}
		if (this.custPerson == null) {
			log.error("Primary contact not found!");
		}
	}
	
	//invoice date
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] dateTime = invoiceDate.split("-");
		boolean isValid = true;
		
		if (Integer.parseInt(dateTime[0]) < 1900 || Integer.parseInt(dateTime[0]) > (currentYear + 10)) {
			isValid = false;
		}
		else if (Integer.parseInt(dateTime[1]) < 1 || Integer.parseInt(dateTime[1]) > 12) {
			isValid = false;
		}
		else if (Integer.parseInt(dateTime[1]) < 1 || Integer.parseInt(dateTime[1]) > 31) {
			isValid = false;
		}
		
		if (isValid) {
			this.invoiceDate = invoiceDate;
			log.info("Invoice date set");
		}
		else {
			log.error("Invalid date!");
		}
	}
	
	//list of products bought on this account: 
	public ArrayList<Product> getProducts() {
		return invoiceProducts;
	}
	
	public void setProducts(ArrayList<Product> products) {
		this.invoiceProducts = products;
		log.info("Products passed into InvoiceProducts");
	}
	
	public boolean hasTicket() {
		return hasTicket;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal += subtotal;
		log.info(subtotal + " added to subtotal. Current subtotal: " + this.subtotal);
	}

	public double getFees() {
		return fees;
	}
	
	public void setFees() {
		if (this.customer instanceof Student) {
			this.fees = 6.75;
			log.info("Student fee set");
		}
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxRate, Product product) {
		// If the product is a season pass, $8 is added as part of the cost
		if (product instanceof SeasonPass) {
			double taxes = (product.getCost()+8)*product.getQuantity()*taxRate;
			this.taxes += taxes;
			log.info("SeasonPass tax calculated and added: " + taxes + "\nCurrent taxes: " + this.taxes);
			return;
		}
		// If the product is a parking pass, the tax is added to a
		// separate tax rate in case there is no deficit to be
		// paid by the customer
		else if (product instanceof ParkingPass) {
			double parkingAfterDiscount = product.getCost()*product.getQuantity() - this.parkingDiscount;
			if (parkingAfterDiscount < 0) {
				parkingAfterDiscount = product.getCost()*product.getQuantity();
			}
			this.setParkingTaxes(this.getParkingTaxes() + parkingAfterDiscount*taxRate);
		}
		// The taxes are then added as normal
		double taxes = product.getCost()*product.getQuantity()*taxRate;
		this.taxes += taxes;
		log.info("Tax calculated and added: " + taxes + "\nCurrent taxes: " + this.taxes);
	}

	// Polymorphism used to set taxes for parking deficit handling
	public void setTaxes(double parkingTaxes) {
		this.taxes -= parkingTaxes;
		log.info("Set parking taxes for later");
		// Total is set again to reflect the changes to taxes
		setTotal();
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount() {
		if (this.getCustomer().getType().equals("S")) {
			this.discount += this.taxes;
			log.info("Student taxes deducted from total");
		}
		// If there is a linked ticket, refreshments are 5% off
		if (this.linkedTicket != null) {
			double refreshmentTotal = 0;
			for (Product p: invoiceProducts) {
				if (p instanceof Refreshment) {
					refreshmentTotal += p.getCost();
				}
			}
			// amount discounted for refreshments
			double refreshmentDiscount = refreshmentTotal*0.05;
			this.discount += refreshmentDiscount;
			log.info("Refreshment discount added to discount tally");
		}
		// If tickets are on Tue/Thur, they are 7% off
		for (Product p: invoiceProducts) {
			if (p instanceof MovieTicket) {
				if (((MovieTicket)p).isTueThur()) {
					double movieDiscount = p.getCost()*0.07;
					this.discount += movieDiscount;
					log.info("Movie ticket Tue/Thur discount calculated and added: " + movieDiscount);
				}
			}
		}
	}

	private void setDiscount(double d) {
		this.discount += d;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal() {
		if (customer instanceof Student) {
			double total = this.subtotal * 0.92;
			double discount = this.subtotal-total;
			setDiscount(discount);
			log.info("Student 8% discount calculated: " + discount);
			this.total = this.subtotal + this.fees + this.taxes - this.discount;
			log.info("Student total: " + this.total);
		}
		else {
			this.total = this.subtotal + this.fees + this.taxes - this.discount;
			log.info("General total: " + this.total);
		}
	}

	public double getParkingDiscount() {
		return parkingDiscount;
	}
	
	// Sets parking discount to the cost and quantity of the linked ticket
	public void setParkingDiscount() {
		this.parkingDiscount = linkedTicket.getQuantity()*linkedTicket.getCost();
	}

	public void setParkingDeficit(boolean b) {
		this.parkingDeficit = b;
	}
	
	public boolean getParkingDeficit() {
		return parkingDeficit;
	}

	public double getParkingTaxes() {
		return parkingTaxes;
	}

	public void setParkingTaxes(double parkingTaxes) {
		this.parkingTaxes += parkingTaxes;
	}

	public Product getLinkedTicket() {
		return linkedTicket;
	}

	public void setLinkedTicket(String linkedTicket) {
		Product product = null;
		if (linkedTicket != null) {
			for (Product p: products) {
				if (p.getCode().equals(linkedTicket))
					if (p instanceof MovieTicket) {
						product = new MovieTicket((MovieTicket)p);
					}
					else if (p instanceof SeasonPass) {
						product = new SeasonPass((SeasonPass)p, this.invoiceDate);
					}
			}
			this.linkedTicket = product;
			log.info("Linked ticket: " + linkedTicket);
		}
		else {
			log.info("No valid linked ticket");
			return;
		}
	}
}
