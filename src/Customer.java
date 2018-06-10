//Get information of customer and sets it, pretty standard stuff
public abstract class Customer {
	private String custName;
	private Address address;
	private String code;
	private String contact;
	
	public Customer(String custName, Address address, String code, String contact) {
		this.setName(custName);
		this.code = code;
		this.setAddress(address);
		this.contact = contact;
	}
	
	//Name of customer
	public String getName() {
		return custName;
	}
	public void setName(String custName) {
		boolean validName = true;
		if (custName == null) {
			this.custName = "(Name unknown)";
		}
		else {
			for (int i = 0; i < custName.length(); i++) {
				// If statements allow for hyphens, apostrophes, and spaces in names
				if ((custName.toLowerCase()).charAt(i) < 97 &&
					(custName.toLowerCase()).charAt(i) != 45 &&
					(custName.toLowerCase()).charAt(i) != 39 &&
					(custName.toLowerCase()).charAt(i) != 32||
					(custName.toLowerCase()).charAt(i) > 122) {
					this.custName = "(Invalid name)";
					validName = false;
					break;
				}
			}
		}
		if (validName) {
			this.custName = custName;
		}
	}
	
	//address of customer
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	//customer code
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	//Contact customer made with company
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public abstract String getType();
	
	public abstract void setType(String type);
}
