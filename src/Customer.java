import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
	private String custName;
	private Address address;
	private String code;
	private String contact;
	private char type;
	
	public Customer(String custName, Address address, String code, String contact, char type) {
		this.setName(custName);
		this.code = code;
		this.setAddress(address);
		this.contact = contact;
		this.setType(type);
	}
	
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
				if ((custName.toLowerCase()).charAt(i) < 97 &&
						(custName.toLowerCase()).charAt(i) != 45 &&
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
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
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
			System.out.println("Invalid customer type for " + custName);
		}
		else {
			this.type = type;
		}
	}
}