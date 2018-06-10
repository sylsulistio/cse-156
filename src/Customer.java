//Get information of customer and sets it, pretty standard stuff
public class Customer {
	private String custName;
	private Address address;
	private String code;
	private String contact;
	private String type;
	
	public Customer(String custName, Address address, String code, String contact, String type) {
		this.setName(custName);
		this.code = code;
		this.setAddress(address);
		this.contact = contact;
		this.setType(type);
	}
	
	//Name of customer
	public String getName() {
		return custName;
	}
	public void setName(String custName) {
		boolean validName = true;
		if (custName == null) {
			this.custName = "(First name unknown)";
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
	
	//Validate whether customer is general or student.
	public String getType() {
		return type;
	}
	public void setType(String type) {
		// Only two types of customers, so I used the specific characters to validate
		if (!type.equals("G") && !type.equals("S")) {
			type = "X";
			System.out.println("Invalid customer type for " + custName);
		}
		else {
			this.type = type;
		}
	}
}
