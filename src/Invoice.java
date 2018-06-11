
import java.util.ArrayList;
import java.util.Calendar;

public class Invoice extends DataConverter {
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
	private boolean hasParking;
	private boolean hasPass;
	private double parkingDiscount;
	private int passQuantity;
	private Customer customer;
	private Person person;
	private Person custPerson;

	//set all the information
	public Invoice(String invoiceCode, String customerCode, String persCode, 
		String invoiceDate, String[] productList) {
		parkingDiscount = 0;
		this.invoiceCode = invoiceCode;
		this.setCustomer(customerCode);
		this.setPerson(persCode);
		this.setInvoiceDate(invoiceDate);
		hasTicket = false;
		hasParking = false;
		hasPass = false;
		this.setProducts(productList);
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
	public void setProducts(String[] productList) {
		for (String p: productList) {
			String[] productArray = p.split(":");
			// if only two in productArray, it is not a ParkingPass
			if (productArray.length == 2) {
				for(Product pr: products) {
					// Looked through CarExample 3 to model this bit after, tried my best to adapt it
					if (productArray[0].equals(pr.getCode())) {
						
						if (pr instanceof MovieTicket) {
							MovieTicket ticket = new MovieTicket((MovieTicket)pr);
							ticket.setQuantity(Integer.parseInt(productArray[1]));
							this.hasTicket = true;
							invoiceProducts.add(ticket);
							setSubtotal(ticket.getCost()*ticket.getQuantity());
						}
						else if (pr instanceof SeasonPass) {
							SeasonPass pass = new SeasonPass((SeasonPass)pr);
							pass.setQuantity(Integer.parseInt(productArray[1]));
							this.passQuantity += pass.getQuantity();
							this.hasTicket = true;
							this.hasPass = true;
							invoiceProducts.add(pass);
							setSubtotal(pass.getCost()*pass.getQuantity());
						}
						else if (pr instanceof Refreshment) {
							Refreshment refreshment = new Refreshment((Refreshment)pr);
							refreshment.setQuantity(Integer.parseInt(productArray[1]));
							invoiceProducts.add(refreshment);
							setSubtotal(refreshment.getCost()*refreshment.getQuantity());
						}
					}
				}
			}
			else if (productArray.length == 3) {
				for(int i = 0; i < products.size(); i++) {
					if (productArray[0].equals(products.get(i).getCode())) {
						
						// Creating a new object to store a duplicate of the product to be placed in the array
						ParkingPass product = new ParkingPass((ParkingPass)products.get(i));
						product.setQuantity(Integer.parseInt(productArray[1]));
						product.setLicense(productArray[2]);
						
						invoiceProducts.add(product);
						
						// Parking is present, so discount amount is activated
						this.setParkingDiscount(product.getCost());
						
						// Parking boolean is set to true
						this.hasParking = true;
						
						
					}
				}
			}
		}
		
		// Checks status of customer
		if (this.customer instanceof Student) {
			setDiscount(0.08);
		}
		else {
			setDiscount(0);
		}
		
		setFees();
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

	public void setFees() {
		if (this.customer instanceof Student) {
			this.fees = 6.75;
		}
		if (hasPass == true) {
			double totalFees = passQuantity * 8;
			this.fees += totalFees;
		}
		
		this.subtotal += this.fees;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		if ((hasParking && hasTicket) || (hasParking && hasPass)) {
			this.parkingDiscount += discount;
		}
		else {
			this.discount = discount;
		}
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getParkingDiscount() {
		return parkingDiscount;
	}

	public void setParkingDiscount(double parkingDiscount) {
		this.parkingDiscount = parkingDiscount;
	}
}
