/**
 * Class for Employees
 * @author Zafar
 *
 */
public class Employee {

	private Integer empID;
	private String Lastname;
	private String Firstname;
	private String position;
	private double amount;
	private double hourly;
	private double cost;
	
	public Employee(int empID, String Lastname, String Firstname, String position,
			double amount, double hourly, double cost){
		this.empID = empID;
		this.Lastname = Lastname;
		this.Firstname = Firstname;
		this.position = position;
		this.amount = amount;
		this.hourly = hourly;
		this.cost = cost;
	}
	
	public Employee(String Lastname, String Firstname, String position, 
			double amount, double hourly, double cost){
		
		this.Lastname = Lastname;
		this.Firstname = Firstname;
		this.position  = position;
		this.amount = amount;
		this.hourly = hourly;
		this.cost = cost;
	}

	/*
	 * Defining getter and setters
	 */
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public String getLastname() {
		return Lastname;
	}

	public void setLastname(String lastname) {
		Lastname = lastname;
	}

	public String getFirstname() {
		return Firstname;
	}

	public void setFirstname(String firstname) {
		Firstname = firstname;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getHourly() {
		return hourly;
	}

	public void setHourly(double hourly) {
		this.hourly = hourly;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	 /**
     * String representation of Employee information
     */
   public String toString(){
       String result = "";
       result += "[ID = " + empID + ", Firstname = " + Firstname + ", Lastname = " + Lastname + 
    		  ", Position = " + position + ", Amount = " + amount + ", Hourly = " + hourly +", Cost = " + cost;
       
       return result;
    }
	
}
