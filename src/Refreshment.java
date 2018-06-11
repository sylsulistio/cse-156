
public class Refreshment implements Product{
	
	private String code;
	private String type;
	private double cost;
	private String productName;
	private int quantity;
	
	public Refreshment(String salesCode, String salesType, String salesName, double salesCost) {
		this.setCode(salesCode);
		this.setType(salesType);
		this.setName(salesName);
		this.setCost(salesCost);
	}

	public Refreshment(Refreshment product) {
		this.setCode(product.getCode());
		this.setType(product.getType());
		this.setName(product.getName());
		this.setCost(product.getCost());
	}

	public void setName(String productName) {
		this.productName = productName;
	}
	
	public String getName() {
		return this.productName;
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
}
