public class General extends Customer {
	
	private String type;

	public General(String custName, Address address, String code, String contact, String type) {
		super(custName, address, code, contact);
		setType(type);
	}

	@Override
	public void setType(String type) {
		this.type = "General";
	}

	@Override
	public String getType() {
		return type;
	}

}
