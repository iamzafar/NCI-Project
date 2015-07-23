/*
 * This  class connects to the database and makes changes in the database
 * All queries are implemented through this connector class.
 */

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Zafar M
 */
public class DBConnection {
    private Connection myConn;	
    private PreparedStatement prepStmt;
    private MessageBox box;
    
    public DBConnection() throws SQLException, Exception{
    
        //getting db properties
        Properties props = new Properties();
        try {
        	props.load(new FileInputStream("P:/NatconProjectManager/Natcon/connection/NatConDB.properties"));//the txt file that holds information about address of db logging information
        	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR! Cannot access the file with username and password information\n"
					+ "Class DBConnection, line 32\n" + e);
			System.out.println("Cannot access the file");
		}
        
        
        
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");
        
        try {
        	//connecting to a db
            myConn = DriverManager.getConnection(dburl, user, password);
            System.out.println("DB connection is successful");
            
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "\tCONNECTION ERROR!\nEither the username or password, or the address of the connection is not correct"
					+ "\nClass DBConnection, line 45");
			System.out.println("Database connection is failed");
		}
        
        
       
        
    }
    
    /****************************************************************************************************
     * This method returns all clients from database
     * @return returns array of clients
     * @throws Exception
     ***************************************************************************************************/    
	public List<Client> getAllClients() throws Exception {
		List<Client> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();			
			myRs = myStmt.executeQuery("select * from client order by client_id;"); //sql query that returns all data from client table ordered by their last name
			 
			while (myRs.next()) {
				Client tempClient = convertRowToClient(myRs);
				list.add(tempClient);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/********************************************************************************************
	 * This method return list of clients names
	 * @return
	 * @throws SQLException
	 *******************************************************************************************/
	public List<String> getClientName() throws SQLException{
		List<String> namelist = new ArrayList<String>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT concat(lastname, \" \", firstname) as name FROM client;");
						
			
			while(myRs.next()){
				namelist.add(myRs.getString("name")); //column alias is  "name"
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			close(myStmt, myRs);
		}
		
		
		return namelist;
		
		
	}
	
	/***************************************************************************
	 * This method returns the id of client by the given full name 
	 * @param name
	 * @return
	 * @throws SQLException
	 ***************************************************************************/
	public int getClientID(String name) throws SQLException{
		int ID = 0;
		PreparedStatement myprepStmt = null;
		ResultSet myRs = null;
		
		try {
			myprepStmt = myConn.prepareStatement("SELECT client_id FROM client "
					+ "where concat(lastname, \" \", firstname) =?");
			
			myprepStmt.setString(1, name);
			myRs = myprepStmt.executeQuery();			
			
			if (myRs.next()) { //<-- IMPORTANT!
			    ID = myRs.getInt("client_id");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Could not get Client ID\n" + e, "Error");
			e.printStackTrace();
			
		}
		finally {
			close(myprepStmt, myRs);
		}
		
		return ID;
	}
	
	/***************************************************************************************************
	 * this method gets input from the user and inserts it into the database
	 * I used prepared statement class because it makes input easier 
	 * @param theClient
	 * @throws Exception
	 ***************************************************************************************************/
	public void addClient(Client theClient) throws Exception {
		PreparedStatement myprepStmt = null;

		try {
			// prepared statement
			myprepStmt = myConn.prepareStatement("insert into client"
					+ " (firstname, lastname, phone, address, city, zipcode, email)"
					+ " values (?, ?, ?, ?, ?, ?, ?);");
			
			// set parameters for every input
			myprepStmt.setString(1, theClient.getFirstname());
			myprepStmt.setString(2, theClient.getLastname());
			myprepStmt.setString(3, theClient.getPhone());
			myprepStmt.setString(4, theClient.getAddress());
			myprepStmt.setString(5, theClient.getCity());
			myprepStmt.setString(6, theClient.getZipcode());
			myprepStmt.setString(7, theClient.getEmail());			
			
			// execute SQL and updates the client table
			myprepStmt.executeUpdate();	
			theClient = null;
			JOptionPane.showMessageDialog(null, "Client was added successfully!");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR! Client was not added succesfully!\n class DBConnection, line 106\n" + e);
		}
		finally {
			close(myprepStmt);
		}
		
	}
	
	/**************************************************************************
	 * This method removes client from the database 
	 * id parameter is used to deleted record from the database
	 * @param id
	 * @throws SQLException
	 *************************************************************************/
	public void removeClient(int id) throws SQLException{
		prepStmt  =null;
		
		try {
			prepStmt = myConn.prepareStatement("delete from client where client_id=?");
			
			prepStmt.setInt(1, id);
			
			prepStmt.executeUpdate();
			
			box.show("Client was deleted from database!", "");
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Error occured during deletion of client", "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/**************************************************************************************
	 * Method updates client`s first name in db using ID number
	 * @param id
	 * @param name
	 * @throws SQLException
	 **************************************************************************************/
	public void updateClientFirstname(int id, String name) throws SQLException{
		
		prepStmt = null;
		
		try {
			//prepare the statement
			prepStmt = myConn.prepareStatement("update client set firstname=? where client_id=?");
			
			//set parameters
			prepStmt.setString(1, name);
			prepStmt.setInt(2, id);
			
			//execute SQL 
			prepStmt.executeUpdate();
			
			
		} catch (Exception e) {
			box.show("Client`s name was not updated", "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/**************************************************************************************
	 * Method Updates client`s last name in db using ID number 
	 * @param id
	 * @param lname
	 * @throws SQLException
	 **************************************************************************************/
	public void updateClientLastname(int id, String lname) throws SQLException{
			
			prepStmt = null;
			
			try {
				//prepare the statement
				prepStmt = myConn.prepareStatement("update client set lastname=? where client_id=?");
				
				//set parameters
				prepStmt.setString(1, lname);
				prepStmt.setInt(2, id);
				
				//execute SQL 
				prepStmt.executeUpdate();
				
				
			} catch (Exception e) {
				box.show("Client`s last name was not updated\n" + e, "Error");
			}
			finally{
				close(prepStmt);
			}
		}
	
	/****************************************************************************************
	 * Method updates client`s address
	 * @param id
	 * @param address
	 * @throws SQLException
	 *****************************************************************************************/
	public void updateClientAddress(int id, String address) throws SQLException{
		
		prepStmt = null;
		
		try {
			//prepare the statement
			prepStmt = myConn.prepareStatement("update client set address=? where client_id=?");
			
			//set parameters
			prepStmt.setString(1, address);
			prepStmt.setInt(2, id);
			
			//execute SQL 
			prepStmt.executeUpdate();
			
			
		} catch (Exception e) {
			box.show("Client`s address was not updated\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/******************************************************************************
	 * This method updates client`s email in database
	 * @param id
	 * @param email
	 * @throws SQLException
	 *******************************************************************************/
	public void updateClientEmail(int id, String email) throws SQLException{
			
			prepStmt = null;
			
			try {
				//prepare the statement
				prepStmt = myConn.prepareStatement("update client set email=? where client_id=?");
				
				//set parameters
				prepStmt.setString(1, email);
				prepStmt.setInt(2, id);
				
				//execute SQL 
				prepStmt.executeUpdate();
				
				
			} catch (Exception e) {
				box.show("Client`s address was not updated\n" + e, "Error");
			}
			finally{
				close(prepStmt);
			}
		}
		
	/*********************************************************************************************
	 * This method will change client`s phone number in the database using the id of the client
	 * @param id
	 * @param phone
	 * @throws SQLException
	 *********************************************************************************************/
	public void updateClientPhone(int id, String phone) throws SQLException{
		
		prepStmt = null;
		
		try {
			//prepare the statement
			prepStmt = myConn.prepareStatement("update client set phone=? where client_id=?");
			
			//set parameters
			prepStmt.setString(1, phone);
			prepStmt.setInt(2, id);
			
			//execute SQL 
			prepStmt.executeUpdate();
			
			
		} catch (Exception e) {
			box.show("Client`s address was not updated\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/***************************************************************************
	 * updates zipcode in database using Client`s ID
	 * @param id
	 * @param zpcode
	 * @throws SQLException
	 ***************************************************************************/
	public void updateClientZipcode(int id, String zpcode) throws SQLException{
			
			prepStmt = null;
			
			try {
				//prepare the statement
				prepStmt = myConn.prepareStatement("update client set zipcode=? where client_id=?");
				
				//set parameters
				prepStmt.setString(1, zpcode);
				prepStmt.setInt(2, id);
				
				//execute SQL 
				prepStmt.executeUpdate();
				
				
			} catch (Exception e) {
				box.show("Client`s address was not updated\n" + e, "Error");
			}
			finally{
				close(prepStmt);
			}
		}
		
		
		
	
	/*************************************************************************************
	 * For searching you don`t have to enter whole last name, just first couple letters
	 * @param lastName
	 * @return List of clients to be searched from database 
	 * @throws Exception
	 *************************************************************************************/
	public List<Client> searchClientByLastname(String lastName) throws Exception {
		List<Client> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			lastName += "%";
			myStmt = myConn.prepareStatement("select * from client where lastname like ?  order by lastname;");
			
			myStmt.setString(1, lastName);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Client tempEmployee = convertRowToClient(myRs);
				list.add(tempEmployee);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/******************************************************************************************
	 * Searches client name by given parameters and returns list of full name as a string
	 * It will be used in creating new job.
	 * @param name
	 * @return
	 * @throws Exception
	 ******************************************************************************************/
	public List<String> searchClientName(String name) throws Exception{
		List<String> list = new ArrayList<String>();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			name +="%"; 
			myStmt = myConn.prepareStatement("SELECT concat(lastname, \" \", firstname) as name FROM client where lastname like ?;");
			myStmt.setString(1, name);
			
			myRs = myStmt.executeQuery();
			
			while(myRs.next()){
				list.add(myRs.getString("name")); //column alias is  "name"
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Cannot do requested query of client name\n" + e, "Error");
		}
		finally {
			close(myStmt, myRs);
		}
	
		return list;
	}
	
	
	
	/***************************************************************************************
	 * For searching you don`t have to enter whole first name, just first couple letters
	 * @param fname
	 * @return List of clients to be searched from database 
	 * @throws Exception
	 ***************************************************************************************/
	public List<Client> searchClientByFirstname(String fname) throws Exception {
		List<Client> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			fname += "%";
			myStmt = myConn.prepareStatement("select * from client where firstname like ?  order by firstname;");
			
			myStmt.setString(1, fname);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Client tempEmployee = convertRowToClient(myRs);
				list.add(tempEmployee);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	
	/****************************************************************************************************
	 * This method converts output from the database in the client object
	 * @param myResult
	 * @return returns record for every client, that is a row in a table
	 * @throws SQLException
	 ****************************************************************************************************/
	//returns as client
	private Client convertRowToClient(ResultSet myResult) throws SQLException {
			//Client tempClient = null;
			
			int id = myResult.getInt("client_id");
			String firstname = myResult.getString("firstname");
			String lastname = myResult.getString("lastname");
			String phone = myResult.getString("phone");
			String address = myResult.getString("address");
			String city = myResult.getString("city");
			String zipcode = myResult.getString("zipcode");
			String email = myResult.getString("email");
			
			
			Client tempClient = new Client(id, firstname, lastname, phone, address, city, zipcode, email);
			
			return tempClient;
		}
	

	/****************************************************************************************************************
	 * ######################################### Employee starts from here ###########################################
	 ****************************************************************************************************************/

	/*****************************************************************************************************************
	 * Returns list of employees in the database
	 * @return
	 * @throws Exception
	 */
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from employee order by employeeId;"); //sql query that returns all data from client table ordered by their last name
			 
			while (myRs.next()) {
				Employee tempEmployee = convertRowToEmployee(myRs);
				list.add(tempEmployee);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	/*****************************************************************************************************************
	 * This method returns foremen which are leaders of the job
	 * @return list of foremen
	 * @throws Exception
	 *****************************************************************************************************************/
	public List<String> getName() throws Exception{
		List<String> list  = new ArrayList<String>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT first, last FROM employee;"); //sql query that returns all foreman
			 
			while (myRs.next()) {
				String tempName = convertRowToName(myRs);
				list.add(tempName);
			}
			System.out.println("list is loaded");
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	/********************************************************************************
	 * this method returns row representation of the employee
	 * @param myResult
	 * @return 
	 * @throws SQLException
	 ********************************************************************************/
	private Employee convertRowToEmployee(ResultSet myResult) throws SQLException {
		
		int id = myResult.getInt("employeeId");
		String firstname = myResult.getString("first");
		String lastname = myResult.getString("last");
		String position = myResult.getString("position");
		double amount = myResult.getDouble("amount");
		double hourly = myResult.getDouble("hourly");
		double cost = myResult.getDouble("cost");
		
		Employee tempEmployee = new Employee(id, lastname, firstname, position, amount, hourly, cost);
		
		return tempEmployee;
		
	}
	
	/*************************************************************************
	 * This method converts output from the database into string
	 * @param myResult
	 * @return
	 * @throws SQLException
	 *************************************************************************/
	private String convertRowToName(ResultSet myResult) throws SQLException{
		String result = "";
		String first = myResult.getString("first");
		String last = myResult.getString("last");
		
		result += first+" "+ last; 
		
		return result;
	}
	
	/***********************************************************************
	 * This method adds employee object into the database
	 * @param theEmployee
	 * @throws Exception
	 ***********************************************************************/
	public void addEmployee(Employee theEmployee) throws Exception {
		PreparedStatement myprepStmt = null;

		try {
			// prepared statement
			myprepStmt = myConn.prepareStatement("insert into employee"
					+ " (last, first, amount, hourly, cost, position)"
					+ " values (?, ?, ?, ?, ?, ?);");
			
			// set parameters for every input
			myprepStmt.setString(1, theEmployee.getLastname());
			myprepStmt.setString(2, theEmployee.getFirstname());
			myprepStmt.setDouble(3, theEmployee.getAmount());
			myprepStmt.setDouble(4, theEmployee.getHourly());
			myprepStmt.setDouble(5, theEmployee.getCost());
			myprepStmt.setString(6, theEmployee.getPosition());
			
			
			// execute SQL and updates the client table
			myprepStmt.executeUpdate();	
			theEmployee = null;
			MessageBox box = new MessageBox();
			box.show("Employee was added succesfully", "Confirmation");
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR! Client was not added succesfully!\n class DBConnection, line 106\n" + e);
		}
		finally {
			close(myprepStmt);
		}
		
	}
	
	/*************************************************************************************
	 * this method changes the last name of the employee in the database
	 * @param last
	 * @param id
	 * @throws SQLException
	 *************************************************************************************/
	public void changeEmplLastname(String last, int id) throws SQLException{
		prepStmt = null;
		
		try {
			//prepare statement
			prepStmt = myConn.prepareStatement("update employee"
					+ " set last=? where employeeId=?");
			
			//setting parameters
			prepStmt.setString(1, last);
			prepStmt.setInt(2, id);
			
			//execute SQL
			prepStmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Table was not updated\nClass DBConnection line 318\n" + e, "Error");
			
		}
		finally{
			close(prepStmt);
		}
		
	}
	
	/************************************************************************************************
	 * changes employees first name
	 * @param first
	 * @param id
	 * @throws SQLException
	 ************************************************************************************************/
	public void changeEmplFirstname(String first, int id) throws SQLException{
		prepStmt = null;
		
		try {
			//prepare statement
			prepStmt = myConn.prepareStatement("update employee"
					+ " set first=? where employeeId=?");
			
			//setting parameters
			prepStmt.setString(1, first);
			prepStmt.setInt(2, id);
			
			//execute SQL
			prepStmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Table was not updated\nClass DBConnection line 602\n" + e, "Error");
			
		}
		finally{
			close(prepStmt);
		}
		
	}
	
	/*************************************************************************************************
	 * this method changes the amount and hourly wage paid for  the employee by connecting to the db
	 * and updating it using prepared statement
	 * @param id
	 * @param amount
	 * @param hourly
	 * @throws SQLException
	 *************************************************************************************************/
	public void changeAmount(int id, double amount, double hourly) throws SQLException{
		PreparedStatement prepStmt = null;
		
		try {
			//prepared statement
			prepStmt = myConn.prepareStatement("update employee"
					+ " set amount=?, hourly=? where employeeId=?");
			
			//setting parameters
			prepStmt.setDouble(1, amount);
			prepStmt.setDouble(2, hourly);
			prepStmt.setInt(3, id);
			
			//executing the statement
			prepStmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Table was not updated\nClass DBConnection line 637\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/***********************************************************************************************
	 * This method changes billing column in the database
	 * @param id
	 * @param bill
	 * @throws SQLException
	 ***********************************************************************************************/
	public void changeBilling(int id, double bill) throws SQLException{
		PreparedStatement prepStmt = null;
		
		try {
			//prepared statement
			prepStmt = myConn.prepareStatement("update employee"
					+ " set cost=? where employeeId=?");
			
			//setting parameters
			prepStmt.setDouble(1, bill);			
			prepStmt.setInt(2, id);
			
			//executing the statement
			prepStmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Table was not updated\nClass DBConnection line 667\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/********************************************************************************************************
	 * 	----------------------------JOB STARTS HERE------------------------------------------
	 ********************************************************************************************************/
	
	/******************************************************************************************
	 * Returns list of Jobs
	 * @return
	 * @throws Exception
	 ******************************************************************************************/
	public List<Joblist> getAllJob() throws Exception{
		
		List<Joblist> list = new ArrayList<Joblist>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try{
			myStmt = myConn.createStatement();			
			myRs = myStmt.executeQuery("select jobnum, concat(lastname, \" \", firstname ) as name, invoice, jobcost, t_m, "
					+ "completion, worktype, hours, materials, startdate, finishdate, concat(first, \" \", last) as leader "
					+ "from client join job using(client_id) join employee using(employeeId);"); //sql query that returns all data from client table ordered by their last name
			 
			while (myRs.next()) {
				Joblist job = convertRowToJob(myRs);
				list.add(job);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
		
	}
	
	/**********************************************************************************************
	 * 
	 * @param jobnum
	 * @param worktype
	 * @param id
	 * @param completion
	 * @throws Exception
	 **********************************************************************************************/
	public void insertJobnumber(int jobnum, String worktype, int id, int completion) throws Exception{
		PreparedStatement prepStmt = null;
		
		try {
			prepStmt = myConn.prepareStatement("insert into job (jobnum, completion, client_id, worktype) values (?, ?, ?, ?);");
			prepStmt.setInt(1, jobnum);
			prepStmt.setInt(2, completion);
			prepStmt.setInt(3, id);
			prepStmt.setString(4, worktype);			
			
			prepStmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Cannot insert new jobnumer to the database", "ERROR");
		}
		finally{
			close(prepStmt);
		}
	}
	
	/******************************************************************************************************
	 * Converts the output to Joblist object
	 * @param myResult
	 * @return
	 * @throws SQLException
	 ******************************************************************************************************/
	private Joblist convertRowToJob(ResultSet myResult) throws SQLException{
		Joblist tempJob = null;
		
		 
		int jobnumber = myResult.getInt("jobnum");
		String name = myResult.getString("name");
		double invoice = myResult.getDouble("invoice");
		double jobcost = myResult.getDouble("jobcost");
		double t_m = myResult.getDouble("t_m");
		String complete = myResult.getString("completion");
		String workType = myResult.getString("worktype");
		double hours = myResult.getDouble("hours");
		double material = myResult.getDouble("materials");
		Date start = myResult.getDate("startdate");
		Date finish =  myResult.getDate("finishdate");
		String leader = myResult.getString("leader");
		
		tempJob = new Joblist(jobnumber, name, invoice, jobcost, t_m, complete, workType, hours, material, start, finish, leader);
		
		
		return tempJob;
		
	}
	
	
	
	/********************************************************************************************************
	 * Changes the invoice in the database
	 * @param jobnum
	 * @param amount
	 * @throws SQLException
	 */
	public void changeInvoice(int jobnum, double amount) throws SQLException{
		prepStmt = null;
		
		try {
			prepStmt = myConn.prepareStatement("update job "
					+ "set invoice=? where jobnum=?");
			
			prepStmt.setDouble(1, amount);
			prepStmt.setInt(2, jobnum);
			
			prepStmt.executeUpdate();
			
			System.out.println("database was updated");
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Could not update the database\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	
	
	/**
	 * this methods deletes an employee from the database
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException{
		
		prepStmt = null;
		
		try {
			prepStmt = myConn.prepareStatement("delete from employee "
					+ " where employeeId=?");
			prepStmt.setInt(1, id);
			
			prepStmt.executeUpdate();
			
			box.show("Employee was deleted from the database", "Confirmation");
		} catch (Exception e) {
			// TODO: handle exception
			box.show("Deleting is not successful!!!\nClass DBConnection line 387\n" + e, "Error");
		}
		finally{
			close(prepStmt);
		}
	}
	
	//closes the connection with the database
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}
	
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
	
	private void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}
    
}
