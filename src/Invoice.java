import java.util.Calendar;

public class Invoice {
	//could we make a dedicated function to codes?
	//See if there are any similarities we need to look for in these codes
	private String invoiceCode;
	private String customerCode;
	private String salesCode;
	private String invoiceDate;
	private String[] productList;
	
	//set all the information
	public Invoice(String invoiceCode, String customerCode, String salesCode, 
		String invoiceDate, String[] productList) {
		this.invoiceCode = invoiceCode;
		this.invoiceCode = invoiceCode;
		this.customerCode = customerCode;
		this.setInvoiceDate(invoiceDate);
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
	
	//customer code	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	//sales code
	public String getSalesCode() {
		return salesCode;
	}
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
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
	public String[] getProducts() {
		return productList;
	}
	public void setProducts(String[] productList) {
		this.productList = productList;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	