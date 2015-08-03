
public class EmployeeHours {
	private String name;
	private String timein;
	private String timeout;
	private double hours;
	private double cost;
	private double total;
	private double hourly;
	private double hourlytotal;


	public EmployeeHours(String name, String timein, String timeout, double hours,
			double cost, double hourly, double total, double hourlytotal){
		this.name = name;
		this.timein = timein;
		this.timeout = timeout;
		this.hours = hours;
		this.cost  = cost;
		this.total = total;
		this.hourly = hourly;
		this.hourlytotal = hourlytotal;
		}


	


	//getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public String getTimein() {
		return timein;
	}


	public void setTimein(String timein) {
		this.timein = timein;
	}


	public String getTimeout() {
		return timeout;
	}


	public void setTimeout(String timeout) {
		this.timeout = timeout;
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
	
	public double getHourly() {
		return hourly;
	}


	public void setHourly(double hourly) {
		this.hourly = hourly;
	}
	
	public double getHourlytotal() {
		return hourlytotal;
	}
	
	public void setHourlytotal(double hourlytotal) {
		this.hourlytotal = hourlytotal;
	}
	
	@Override
	public String toString() {
		return "EmployeeHours [name=" + name + ", timein=" + timein
				+ ", timeout=" + timeout + ", hours=" + hours + ", cost="
				+ cost +", hourly=" + hourly + ", total=" + total + ", hourly total="+ hourlytotal+"\n"; 
	}

	
	
}
