import java.util.ArrayList;
import java.util.Calendar;

public class Invoice extends DataConverter {
	//could we make a dedicated function to codes?
	//See if there are any similarities we need to look for in these codes
	private String invoiceCode;
	private String custCode;
	private String persCode;
	private String invoiceDate;
	private String custName;
	private String custType;
	private String persName;
	private String[] persEmails;
	private Address custAddress;
	private Address movieAddress;
	private Address persAddress;
	private double subtotal;
	private double fees;
	private double taxes;
	private double discount;
	private double total;
	private ArrayList<Product> invoiceProducts = new ArrayList<Product>();
	private boolean hasTicket = false;
	private boolean hasParking = false;
	private boolean hasPass = false;
	private double parkingDiscount;
	private int passQuantity;
	
	//set all the information
	public Invoice(String invoiceCode, String customerCode, String persCode, 
		String invoiceDate, String[] productList) {
		this.invoiceCode = invoiceCode;
		this.setCustCode(customerCode);
		this.setPersCode(persCode);
		this.setInvoiceDate(invoiceDate);
		this.setProducts(productList);
		hasTicket = false;
		hasParking = false;
		hasPass = false;
		parkingDiscount = 0;
	}
	
	//getters and setters
	//invoice code
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	//customer code	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String customerCode) {
		for (Customer c: customers) {
			if (customerCode.equals(c.getCode())) {
				this.custName = c.getName();
				this.custCode = c.getCode();
				this.custType = c.getType();
				this.custAddress = c.getAddress();
				break;
			}
		}
	}

	public String getCustName() {
		return custName;
	}

	public String getCustType() {
		return custType;
	}

	public String getPersName() {
		return persName;
	}

	public String[] getPersEmails() {
		return persEmails;
	}

	public Address getCustAddress() {
		return custAddress;
	}

	public Address getMovieAddress() {
		return movieAddress;
	}

	public Address getPersAddress() {
		return persAddress;
	}

	//sales code
	public String getPersCode() {
		return persCode;
	}
	public void setPersCode(String persCode) {
		for (Person p: persons) {
			if (persCode.equals(p.getCode())) {
				this.persName = p.getName();
				this.persCode = p.getCode();
				this.persAddress = p.getMail();
				this.persEmails = p.getEmails();
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
			//This is an example of a productList:: fp12:2,3289:1:fp12,ff23:4
			String[] productArray = p.split(":");
			//check through all existing products to see what type of product it is.
			if (productArray.length == 2) {
				for(int i = 0; i < products.size(); i++) {
					if (productArray[0].equals(products.get(i).getCode())) {
						
						// Creating a new object to store a duplicate of the product to be placed in the array
						Product product = products.get(i);
						
						// Setting quantity in each product object
						product.setQuantity(Integer.parseInt(productArray[1]));
						
						// Adding product object to invoice ArrayList
						invoiceProducts.add(product);
						setSubtotal(product.getCost()*product.getQuantity());
						
						// If product is a MovieTicket, ticket boolean is set to true
						// If product is a SeasonPass, both the ticket and pass booleans
						// are set to true
						if (product instanceof MovieTicket) {
							this.hasTicket = true;
						}
						else if (product instanceof SeasonPass) {
							this.passQuantity += product.getQuantity();
							this.hasTicket = true;
							this.hasPass = true;
						}
					}
				}
			}
			else if (productArray.length == 3) {
				for(int i = 0; i < products.size(); i++) {
					if (productArray[0].equals(products.get(i).getCode())) {
						
						// Creating a new object to store a duplicate of the product to be placed in the array
						Product product = products.get(i);
						
						// Setting quantity in each product object
						product.setQuantity(Integer.parseInt(productArray[1]));
						
						// Adding product object to invoice ArrayList
						invoiceProducts.add(products.get(i));
						setSubtotal(product.getCost()*product.getQuantity());
						((ParkingPass)product).setLicense(productArray[2]);
						
						// Parking is present, so discount amount is activated
						this.setParkingDiscount(product.getCost());
						
						// Parking boolean is set to true
						this.hasParking = true;
					}
				}
			}
		}
		
		// Checks status of customer
		if (this.custType.equals("Student")) {
			setDiscount(0.08);
		}
		else {
			setDiscount(0);
		}
		
		setFees();
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
		if (custType.equals("Student")) {
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
		if (hasParking && hasTicket) {
			this.discount += parkingDiscount;
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
