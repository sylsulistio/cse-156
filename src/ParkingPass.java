
public class ParkingPass implements Product{
	
	private String code;
	private String type;
	private double cost;
	private int quantity;
	private String license;
	
	public ParkingPass(String salesCode, String salesType, double salesCost) {
		this.setCode(salesCode);
		this.setType(salesType);
		this.setCost(salesCost);
	}

	@Override
	public void setType(String type) {
		// code only allows for four specific types, otherwise it is set to I
		// for "Invalid"
		for (String s: TYPE_ARRAY) {
			if (s.equals(type)) {
				this.type = s;
				return;
			}
			else {
				this.type = "I";
			}
		}
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public int getQuantity() {
		return this.quantity;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

}