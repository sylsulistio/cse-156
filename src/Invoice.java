import java.util.ArrayList;
import java.util.Calendar;

public class Invoice extends DataConverter {
	//could we make a dedicated function to codes?
	//See if there are any similarities we need to look for in these codes
	private String invoiceCode;
	private String customerCode;
	private String persCode;
	private String invoiceDate;
	private String[] productList;
	private String custName;
	private String custType;
	private String persName;
	private String[] persEmails;
	private Address custAddress;
	private Address movieAddress;
	private Address persAddress;
	
	//set all the information
	public Invoice(String invoiceCode, String customerCode, String persCode, 
		String invoiceDate, String[] productList) {
		this.invoiceCode = invoiceCode;
		this.setCustomerCode(customerCode);
		this.setPersCode(persCode);
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
		return custName;
	}
	public void setCustomerCode(String customerCode) {
		for (Customer c: customers) {
			if (customerCode.equals(c.getCode())) {
				this.custName = c.getName();
				this.customerCode = c.getCode();
				this.custType = c.getType();
				this.custAddress = c.getAddress();
				break;
			}
		}
	}
	
	//sales code
	public String getPersCode() {
		return persName;
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
	public String[] getProducts() {
		return productList;
	}
	public void setProducts(String[] productList) {
		this.productList = productList;
	}
}