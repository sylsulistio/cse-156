
public class Student extends Customer {
	
	private String type;

	public Student(String custName, Address address, String code, String contact, String type) {
		super(custName, address, code, contact);
		setType(type);
	}

	@Override
	public void setType(String type) {
		this.type = "Student";
	}

	@Override
	public String getType() {
		return type;
	}

}
