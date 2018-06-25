
import java.util.ArrayList;
import java.util.Calendar;

public class Invoice extends DatabaseReader {
	//could we make a dedicated function to codes?
	//See if there are any similarities we need to look for in these codes
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
	private String linkedTicket;

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
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(String customerCode) {
		for (Customer c: customers) {
			if (customerCode.equals(c.getCode())) {
				this.customer = c;
				setCustPerson(c.getContact());
				break;
			}
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
				break;
			}
		}
	}

	public Person getCustPerson() {
		return custPerson;
	}
	
	public void setCustPerson(String custPersonCode) {
		for (Person p: persons) {
			if (custPersonCode.equals(p.getCode())) {
				this.custPerson = p;
				break;
			}
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
		}
		else {
			this.invoiceDate = "(Invalid date)";
		}
	}
	
	//list of products bought on this account: 
	public ArrayList<Product> getProducts() {
		return invoiceProducts;
	}
	
	public void setProducts(ArrayList<Product> products) {
		this.invoiceProducts = products;
		
		// Setting any necessary fees
		setFees();
		// Setting total
		setTotal();
	}
	
	public boolean hasTicket() {
		return hasTicket;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal += subtotal;
	}

	public double getFees() {
		return fees;
	}
	
	/**
	 * Method that sets fees for students and season passes
	 */
	public void setFees() {
		if (this.customer instanceof Student) {
			this.fees = 6.75;
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
	}

	// Polymorphism used to set taxes for parking deficit handling
	public void setTaxes(double parkingTaxes) {
		this.taxes -= parkingTaxes;
		// Total is set again to reflect the changes to taxes
		setTotal();
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal() {
		if (customer instanceof Student) {
			double total = this.subtotal * 0.92;
			setDiscount((this.subtotal-total));
			this.total = this.subtotal + this.fees - this.discount;
		}
		else {
			this.total = subtotal + fees + taxes - discount;
		}
	}

	public double getParkingDiscount() {
		return parkingDiscount;
	}
	
	// Sets parking discount to the cost and quantity of the linked ticket
	
	public void setParkingDiscount(Product linkedTicket) {
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

	public String getLinkedTicket() {
		return linkedTicket;
	}

	public void setLinkedTicket(String linkedTicket) {
		this.linkedTicket = linkedTicket;
	}
}
