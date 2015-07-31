import java.sql.Date;

//class for the job list


public class Joblist {

	
	private int jobID;
	private int jobNum;
	private String clientName;
	private double invoice;
	private double cost;
	private double T_M;
	private String completion;
	private String workType;
	private double hours;
	private double materials;
	private Date startdate;
	private Date enddate;
	private String leader;
	private double profit;
	
	
	//costructor
	public Joblist(int jobID, int jobnum, String clientName, double invoice, double cost, double T_M,
			String completion, String worktype, double hours, double materials,
			Date startdate, Date enddate, String leader, double profit){
		this.jobID = jobID;
		this.jobNum = jobnum;
		this.clientName = clientName;
		this.invoice = invoice;
		this.cost = cost;
		this.T_M = T_M;
		this.completion = completion;
		this.workType = worktype;
		this.hours = hours;
		this.materials = materials;
		this.startdate  = startdate;
		this.enddate = enddate;
		this.leader = leader;
		this.profit = profit;
	}
	
	public Joblist(int jobnum, String clientName, double invoice, double cost, double T_M,
			String completion, String worktype, double hours, double materials, 
			Date startdate, Date enddate, String leader, double profit){
		
		this.jobNum = jobnum;
		this.clientName = clientName;
		this.invoice = invoice;
		this.cost = cost;
		this.T_M = T_M;
		this.completion = completion;
		this.workType = worktype;
		this.hours = hours;
		this.materials = materials;
		this.startdate  = startdate;
		this.enddate = enddate;
		this.leader = leader;
		this.profit = profit;
	}
	
	

	//generating getters and setters
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public int getJobNum() {
		return jobNum;
	}
	public void setJobNum(int jobNum) {
		this.jobNum = jobNum;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public double getInvoice() {
		return invoice;
	}
	public void setInvoice(double invoice) {
		this.invoice = invoice;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getT_M() {
		return T_M;
	}
	public void setT_M(double t_M) {
		T_M = t_M;
	}
	public String getCompletion() {
		return completion;
	}
	public void setCompletion(String completion) {
		this.completion = completion;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public double getHours() {
		return hours;
	}
	public void setHours(double hours) {
		this.hours = hours;
	}
	public double getMaterials() {
		return materials;
	}
	public void setMaterials(double materials) {
		this.materials = materials;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}
		
	@Override
	public String toString() {
		return "Joblist jobID=" + jobID + ", jobNum=" + jobNum
				+ ", clientName=" + clientName + ", invoice=" + invoice
				+ ", cost=" + cost + ", T_M=" + T_M + ", completion="
				+ completion + ", workType=" + workType + ", hours=" + hours
				+ ", materials=" + materials + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", leader=" + leader + ", profit=" + profit;
	}
}
