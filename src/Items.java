/*
 * Item class is for tools and items that will be used
 */
public class Items {	

	private int item_id;
	private String item_name;
	private String item_unit;
	private double item_amount;
	private double item_cost;
	private double item_total;
	
	/*
	 * There will be overloaded constructers
	 */
	public Items(int item_id, String item_name, String item_unit,
			double item_amount, double item_cost, double item_total) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_unit = item_unit;
		this.item_amount = item_amount;
		this.item_cost = item_cost;
		this.item_total = item_total;
	}
	
	public Items(String item_name, String item_unit,
			double item_amount, double item_cost, double item_total) {
		super();		
		this.item_name = item_name;
		this.item_unit = item_unit;
		this.item_amount = item_amount;
		this.item_cost = item_cost;
		this.item_total = item_total;
	}

	/*
	 * Getters and Setters
	 */
	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_unit() {
		return item_unit;
	}

	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}

	public double getItem_amount() {
		return item_amount;
	}

	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}

	public double getItem_cost() {
		return item_cost;
	}

	public void setItem_cost(double item_cost) {
		this.item_cost = item_cost;
	}

	public double getItem_total() {
		return item_total;
	}

	public void setItem_total(double item_total) {
		this.item_total = item_total;
	}
	
	@Override
	public String toString() {
		return "Items [item_id=" + item_id + ", item_name=" + item_name
				+ ", item_unit=" + item_unit + ", item_amount=" + item_amount
				+ ", item_cost=" + item_cost + ", item_total=" + item_total
				+ "]";
	}
}
