
public class Worker_Hours {

	private int ID;
	private String name;
	private double in;
	private double out;
	private double hours;
	private double cost;
	private double total;
	
	
	/**
	 * contructor
	 * @param name
	 * @param in
	 * @param out
	 * @param hours
	 * @param cost
	 * @param total
	 */
	public Worker_Hours(String name, double in, double out, double hours, double cost,
			double total){
		this.name = name;
		this.in = in;
		this.out = out;
		this.hours = hours;
		this.cost = cost;
		this.total = total;
		
	}
	
	public Worker_Hours(int ID, String name, double in, double out, double hours, double cost,
			double total){
		this.ID = ID;
		this.name = name;
		this.in = in;
		this.out = out;
		this.hours = hours;
		this.cost = cost;
		this.total = total;
		
	}	
	
	
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


	public double getIn() {
		return in;
	}


	public void setIn(double in) {
		this.in = in;
	}


	public double getOut() {
		return out;
	}


	public void setOut(double out) {
		this.out = out;
	}


	public double getHours() {
		return hours;
	}


	public void setHours(double hours) {
		this.hours = hours;
	}


	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}
	
	public String toString(){
		String result = "";
		result += "ID = " + ID + ", Name: " + name + ", Time in: " + in + ", Time out: " + out + ", Hours: " + hours
				+ ", Cost: " + cost + ", Total: " + total;		
		
		return result;
	}
}
