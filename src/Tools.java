/**
 * This class is designed for tools
 * @author Zafar
 *
 */
public class Tools {

	private int ID;
	private String name;
	private String unit;
	private double cost;
	private double billed;
	private double amount;
	private double total;
	
	
	//constructor
	public Tools(int ID, String name, String unit,double cost, double billed, double amount, double total){
		this.ID = ID;
		this.name = name;
		this.unit = unit;
		this.cost = cost;
		this.billed = billed;
		this.amount = amount;
		this.total = total;
	}	


	public Tools(int iD, String name, String unit,double cost, double billed) {		
		ID = iD;
		this.name = name;
		this.unit = unit;
		this.cost = cost;
		this.billed = billed;
		
	}


	


	//getters and setters
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getBilled() {
		return billed;
	}
	public void setBilled(double billed) {
		this.billed = billed;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	//toString()
	@Override
	public String toString() {
		return "Tools [ID=" + ID + ", name=" + name + ", unit=" + unit + ", cost=" + cost
				+ ", billed=" + billed + ", amount=" + amount + ", total="
				+ total + "]\n";
	}



}
