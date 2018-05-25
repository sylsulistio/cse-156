
public class Customer {
	private String firstName;
	private String lastName;
	private String[] address;
	private String code;
	private String contact;
	private char type;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		boolean validName = true;
		if (firstName == null) {
			this.firstName = "(First name unknown)";
		}
		else {
			for (int i = 0; i < firstName.length(); i++) {
				if ((firstName.toLowerCase()).charAt(i) < 97 ||
					(firstName.toLowerCase()).charAt(i) > 122 &&
					(firstName.toLowerCase()).charAt(i) != 45) {
					this.firstName = "(Invalid name)";	 
					break;
					}
				}
		}
		if (validName) {
			this.firstName = firstName;
		}
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		boolean validName = true;
		if (lastName == null) {
			this.lastName = "(First name unknown)";
		}
		else {
			for (int i = 0; i < lastName.length(); i++) {
				if ((lastName.toLowerCase()).charAt(i) < 97 ||
					(lastName.toLowerCase()).charAt(i) > 122 &&
					(lastName.toLowerCase()).charAt(i) != 45) {
					this.lastName = "(Invalid name)";	 
					break;
					}
				}
		}
		if (validName) {
			this.lastName = lastName;
		}
	}
	
	public String[] getAddress() {
		return address;
	}
	public void setAddress(String address) {
		String[] addressArr = address.split(",");
		this.address = addressArr;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public char getType() {
		return type;
	}
	public void setType(char type) {
		if (type != 'G' && type != 'S') {
			type = 'X';
			System.out.printf("Invalid customer type for %1s %1s", this.firstName,
					this.lastName);
		}
		this.type = type;
	}
}
