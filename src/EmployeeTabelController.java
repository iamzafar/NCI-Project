import java.awt.ItemSelectable;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

/**
 * Controller for employee table
 * @author ZM
 *
 */
public class EmployeeTabelController implements Initializable{

	private DBConnection connection;
	private List<Employee> emplist;
	String name;
	private static double Percent = 7.65;
	
	//not in use, however will be used in the method 
	private static List<String> listofPercenatage = new ArrayList<String>(); 
	
	
	
	@FXML
	private TextField firstn;
	
	@FXML
	private TextField lastn;
	
	@FXML
	private TextField amount;
	
	/*@FXML
	private TextField percent;*/
	
	@FXML
	private  ComboBox<String> PercentRate;	
	
	@FXML
	private ComboBox<String> workerType;
	
	//there are gonna be 7 columns
	@FXML
	private TableColumn<Employee, Integer> idColumn;
	
	@FXML
	private TableColumn<Employee, String> lastnColumn;
	
	@FXML
	private TableColumn<Employee, String> firstnColumn;
	
	@FXML
	private TableColumn<Employee, String> position;
	
	@FXML
	private TableColumn<Employee, Double> amountColumn;
	
	@FXML
	private TableColumn<Employee, Double> hourlyColumn;
	
	@FXML
	private TableColumn<Employee, Double> costColumn;	
	
	@FXML
	private TableView<Employee> table;
	
	//I tried to use constructor to instantiate this class that accepts some parameters
	//but it did not work, so made columns editable
	public EmployeeTabelController(){
		
	}
	
	public EmployeeTabelController(String name){
		this.name = name;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		table.setEditable(true);
		
		//Setting up the property for each column from Employee class		
		idColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("empID"));
		
		//making columns editable
		lastnColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Lastname"));
		setLastNameEdittable(lastnColumn);
		
		
		firstnColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Firstname"));
		setFirstNEdittable(firstnColumn);
		
		position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		
		//amount column could be changed the wage can be changed by the management
		amountColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("amount"));
		setAmountColumnEditable(amountColumn);		
		
		hourlyColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("hourly"));
		
		costColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("cost"));
		setBillColumnEdittable(costColumn);
		
		
		emplist = new ArrayList<Employee>();

		
		
		try {
			connection = new DBConnection();
			emplist = connection.getAllEmployees();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR! Cannot connect to the DB\n Class EmployeeTabelController, line 178\n" + e);
		}
		
		try {
			table.setItems(loadEmployeeInObservlist(emplist));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot load list of employees to the table"
					+ "\nClass EmployeeTabelController, line 185");
			e.printStackTrace();
		}
		
		//percent.setEditable(false);//percent text field will be disabled
		PercentRate.setItems(getPercentlist());
		PercentRate.setEditable(true);
		
		workerType.setItems(loadWorkerType());
		
		/*if(!(PercentRate.getValue().equals("0")) || !(PercentRate.getValue().equals(null))){
			PercentRate.setValue(percRate);
		}*/
		
		
	}
	
	
	/*******************************************************************************************************************************
	 * method for adding the employee
	 */
	@FXML
	private void addEmployee()throws Exception, SQLException{
		connection = new DBConnection();
		emplist = new ArrayList<Employee>();
		double bill = 0;
		
		String first = firstn.getText();
		String last = lastn.getText();
		
		String amnt = amount.getText();
		//String prcnt = percent.getText();
		String percRate = PercentRate.getValue();
		
		System.out.println(percRate);
		
		//checking the input to validate the condition
		if((first != null && first.trim().length() > 0)
				&& (last != null && last.trim().length() > 0) 
				&& (amnt != null && amnt.trim().length() > 0)				
				&& isNumber(amnt)  && !percRate.equals(null) 
				&& isNumber(percRate) && percRate.trim().length() > 0){
			
			//System.out.println(first + " " + last + " " + amnt + " " + prcnt);			
			//converting a string to number
			double Amount = Double.parseDouble(amnt); //this variable will be changed
			Percent = Double.parseDouble(percRate);
			//double PercRate = Double.parseDouble(percRate);
			
			double hourly = Amount + Amount *(Percent/100.0);
			//DecimalFormat df = new DecimalFormat("#.##");
			//double new_hourly = Double.parseDouble(df.format(hourly));			
			double new_hourly = Math.round(hourly * 100.0) / 100.0;
			
			System.out.println("Hourly before: " + hourly + "\tafter: " + new_hourly);			
			
			String position = workerType.getValue();
			
			//there will be 2 types of foreman
			//Foreman 1 will get $55, Foreman 2 will get $65
			switch(position){
			case "Laborer":
				bill = 35.0;
				break;
			case "Foreman 55":
				bill = 55.0;
				break;
			case "Foreman 65":
				bill = 65.0;
				break;
			case "Super":
				bill = 75.0;
				break;				
			}
			
			Employee tempEmployee = new Employee(last, first, position, Amount, new_hourly, bill);
			connection.addEmployee(tempEmployee);	//adding new employee to the database
			emplist = connection.getAllEmployees();	//getting new list of employees from the database
			
			/*
			 * getting list of employees and displaying this list in a table
			 */
			try {
				table.setItems(loadEmployeeInObservlist(emplist));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Cannot load list of employees to the table"
						+ "\nClass EmployeeTabelController, line 88");
				e.printStackTrace();
			}
			
			System.out.println(tempEmployee);
			System.out.println(PercentRate.getValue());
			//PercentRate.setItems(loadPercentRate(percRate)); // not in use at all
			
			firstn.clear();
			lastn.clear();
			amount.clear();
			
			
			
		}
		else{
			MessageBox box = new MessageBox();
			box.show("THE INPUT IS INCORRECT!\nPLEASE CHECK YOUR INPUT!\nFILL IN ALL FIELDS", "Ooops..ERROR");
		}
		

		
	}
	
	@FXML
	private void deleteEmployee() throws Exception{
		System.out.println("Delete Pressed");
		ObservableList<Employee> delItems, items;
		emplist = new ArrayList<Employee>();
		connection = new DBConnection();
		emplist = connection.getAllEmployees();
		ConfirmationBox confirm = new ConfirmationBox();
		//items = loadEmployeeInObservlist(emplist);
		
		
		items = table.getItems();
		System.out.println("size of the employee list: "+items.size());

		delItems = table.getSelectionModel().getSelectedItems();
		System.out.println(delItems.size());
		
		//it will ask if user wants to delete this record or not
		if(confirm.display("Verification", "Do you really want to delete this employee?")){
			
			for(Employee e: delItems){
				items.remove(e);
				//deleted employee from the database
				connection.delete(e.getEmpID());
				
				System.out.println("deleted employee :" + e);
			}
			
		}		
		
		/*try {
			table.setItems(loadEmployeeInObservlist(items));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot delete list of employees to the table"
					+ "\nClass EmployeeTabelController, line 311");
			e.printStackTrace();
		}*/

		//get the id of the selected row
		
	}
	
	/****************************************************************************************
	 * Makes last Name column editable
	 * @param last
	 */
	private void setLastNameEdittable(TableColumn<Employee, String> last){
		last.setCellFactory(TextFieldTableCell.forTableColumn());
		last.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {			
			@Override
			public void handle(CellEditEvent<Employee, String> event) {
				// TODO Auto-generated method stub
				int id = ((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmpID();	// getting the id of the employee
				((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).setLastname(event.getNewValue());	//changing the value in the table only
				String last = event.getNewValue();	//new input Value
				
				System.out.println("ID = " + id + " " + last);
				
				//updating the actual database
				try {
					connection.changeEmplLastname(last, id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
	}
	
	/******************************************************************************************
	 * Makes first name column editable
	 * @param first
	 */
	private void setFirstNEdittable(TableColumn<Employee, String> first){
		first.setCellFactory(TextFieldTableCell.forTableColumn());
		
		first.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {

			@Override
			public void handle(CellEditEvent<Employee, String> event) {
				// TODO Auto-generated method stub
				int id = ((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmpID();
				((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).setFirstname(event.getNewValue());	//changing the value in the table only
				String fname = event.getNewValue();	//new input Value
				System.out.println("ID = " + id + " " + "new firstname: "+ fname);
				
				try {
					connection.changeEmplFirstname(fname, id);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/****************************************************************************************************
	 * Sets Amount column edittable
	 * @param amount
	 */
	private void setAmountColumnEditable(TableColumn<Employee, Double> amount){
		amount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		amount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,Double>>() {

			@Override
			public void handle(CellEditEvent<Employee, Double> event) {
				// TODO Auto-generated method stub
			
				int id = ((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmpID();
				double amount = event.getNewValue();	//new input Value
				double hourly = amount + amount *(Percent/100.0);
				double new_hourly = Math.round(hourly * 100.0) / 100.0;
			
				
				((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAmount(event.getNewValue());	//changing the value in the table only				
				
				((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).setHourly(new_hourly);
							
				
				System.out.println("The id is " + id + "\tNew Amount is " + amount + "\tNew Hourly is: " + hourly);
				
				try {
					connection.changeAmount(id, amount, hourly);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/***********************************************************************************************
	 * changes the billing for the employee
	 * @param cost
	 */
	private void setBillColumnEdittable(TableColumn<Employee, Double> cost){
		cost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		cost.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,Double>>() {

			@Override
			public void handle(CellEditEvent<Employee, Double> event) {
				// TODO Auto-generated method stub
				int id = ((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmpID();
				double bill = event.getNewValue();
				
				((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCost(event.getNewValue());
				
				System.out.println("The id is " + id  + "\tNew billing amount: " + bill);
				
				try {
					connection.changeBilling(id, bill);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}
	
	/********************************************************************************************************************************
	 * this method gets list of employees
	 * @param empl
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */	
	private ObservableList<Employee> loadEmployeeInObservlist(List<Employee> empl)throws Exception, SQLException{
		List<Employee> employees = new ArrayList<Employee>();
		ObservableList<Employee> list = FXCollections.observableArrayList();
		
		
		employees = empl;
		
		for(Employee e: employees){
			list.add(e);
		}
		
		return list;
	}
	
	/*******************************************************************************************************************************
	 * This overloaded method and it gets single employee as parameter  
	 * @param emp
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	private ObservableList<Employee> loadEmployeeInObservlist(Employee emp)throws Exception, SQLException{
	
		ObservableList<Employee> list = FXCollections.observableArrayList();
		list.add(emp);	
		
		return list;
	}
	
	/******************************************************************************************************
	 * the method looks for the character in the string
	 * if it finds the character or white space then the string is not a number. 
	 * @param str
	 * @return boolean value
	 */
	private boolean isNumber(String str){
		boolean result = true;		
		if(!(str.equals(null))){
		for(int i = 0; i < str.length(); i++){
			if(Character.isLetter(str.charAt(i)) || Character.isWhitespace(str.charAt(i)) || str.charAt(i) == ',' || str.charAt(i) == '%'){
				result = false;
				i = str.length();
			}					
		}		
		}
		return result;
	}
	
	/**
	 * Returns the list positions of the employee
	 * @return
	 */
	private ObservableList<String> loadWorkerType(){
		
		ObservableList<String> workers = FXCollections.observableArrayList();
		workers.addAll("Laborer", "Foreman 55", "Foreman 65", "Super");
		
		return workers;
	}

	/*********************************************************************************************
	 * loads the string into the combobox. I am not using this method this time because  
	 * there`s a problem whenever the program is restarted, percents are not saved in the combox list
	 * I am defining the initial percent rate which was given by George
	 * @return
	 */
	private ObservableList<String> loadPercentRate(String str){
		
		boolean isDuplicate = false;
		ObservableList<String> Perc_list = FXCollections.observableArrayList();
		
		//checks whether there is duplicate input in the list
		for(int i = 0; i < listofPercenatage.size(); i++){
			if(listofPercenatage.get(i).equals(str) || !isNumber(str)){
				isDuplicate = true;
				i = listofPercenatage.size(); //if the there is duplicate exit the loop
			}				
		}
		
		
		//if there is not a duplicate add the list into the observable list  
		if(!isDuplicate && isNumber(str)){
			listofPercenatage.add(str);
			for (String s: listofPercenatage){
				Perc_list.add(s);
			}
			
		}		
		
		return Perc_list;
	}
	
	/***********************************************************************************
	 * returns the list percents
	 * Percent rate for employees can be changed from here 
	 * @return
	 */
	private ObservableList<String> getPercentlist(){
		ObservableList<String> Perc_list = FXCollections.observableArrayList();		
		Perc_list.addAll("7.65");
		
		for(String s: listofPercenatage){
			Perc_list.add(s);
		}
		
		return Perc_list;
	}
	
	
	
	

}
