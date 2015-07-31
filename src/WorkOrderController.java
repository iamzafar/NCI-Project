import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.geom.Matrix3f;

import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;


public class WorkOrderController implements Initializable{

	private DBConnection connection;
	private List<Tools> toolsList;
	private List<String> listOfWorkers;
	private TreeItem<String> root, masonrySupplies, bulkGoods, disposal, fencing, masonry, miscellaneous,
								pipes, vehicles_eqipm, woods, stones, blocks, ADS, pipe1_5, pipe2, pipe3, pipe4;
	private List<EmployeeHours> employeelist;
	private List<String> list;
	
	
	@FXML
	private Button summary;
	
	@FXML
	TextField dailyTotal;
	
	@FXML
	TextField ascendingTotal;
	
	@FXML
	Label jobNumberLabel;
	
	@FXML
	Label clientNameLabel;
	
	@FXML
	Label workType;
	
	@FXML
	DatePicker datechooser;
	
	@FXML
	ComboBox<String> leaderList;
	
	@FXML
	ComboBox<String> workerList;
	
	@FXML
	ComboBox<String> fromHoursList;
	
	@FXML
	ComboBox<String> toHoursList;
	
	@FXML
	ComboBox<String> AmPm1;
	
	@FXML
	ComboBox<String>AmPm2;
	
	@FXML
	CheckBox lunchbreak;
	
	@FXML
	CheckBox jobCompleted;
	
	@FXML
	TextField itemAmount;
	
	@FXML
	Button add_Worker;
	
	@FXML
	TreeView<String> tools;//tree of tools	
	
	//workers table
	@FXML
	TableView workerstable;	
	
	@FXML
	TableColumn<EmployeeHours, String> Name;
	
	@FXML
	TableColumn<EmployeeHours, String> in;
	
	@FXML
	TableColumn<EmployeeHours, String> out;
	
	@FXML
	TableColumn<EmployeeHours, Double> hours;
	
	@FXML
	TableColumn<EmployeeHours, Double> cost;
	
	@FXML
	TableColumn<EmployeeHours, Double> total;
	
	//tools table
	@FXML
	TableView toolstable;
	
	@FXML
	TableColumn<Tools, Integer> Id;
	
	@FXML
	TableColumn<Tools, String> itemNameColumn;
	
	@FXML
	TableColumn<Tools, String> unitColumn;
	
	@FXML
	TableColumn<Tools, Double> toolAmount;
	
	@FXML
	TableColumn<Tools, Double> toolCost;
	
	@FXML
	TableColumn<Tools, Double> toolTotalColumn;
	
	
	
	private String jobNumber, name, jobtype;
	private  double TM_cost;
	private  double dailyCost;
	private String date;
	
	
	
	public WorkOrderController(String jobNumber, String clientName, String jobType, double cost) throws SQLException, Exception {		
		
		this.jobNumber = jobNumber; 
		this.name = clientName;
		this.jobtype = jobType;
		this.TM_cost = cost;
		
		connection = new DBConnection(); //connection to db
		listOfWorkers = new ArrayList<String>();		
				
		employeelist = new ArrayList<EmployeeHours>();
		toolsList = new ArrayList<Tools>();	
		
		
		
	}	
	
	
	
	@FXML
	public void check()throws SQLException, ParseException{
		System.out.println("Output after constructor was initialized " + jobNumber);		
		//formatting datepicker
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");		
		
		if(datechooser.getValue() != null)
			System.out.println(datechooser.getValue()); //printing preferred date format
		
		//if job is completed mark the date
		if(jobCompleted.isSelected()){
			if(datechooser.getValue() != null){
				
				//formatting the date
				formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				date = formatter.format(datechooser.getValue());
				connection.JobCompleted(date, jobNumber);
				
				System.out.println("Date was updated: " + date);
				
			}else{
				MessageBox box = new MessageBox();
				box.show("Date is not selected", "Error");
			}
		}		
		else if(datechooser.getValue() != null && !connection.isJobStarted(jobNumber)){	//it is the date when job is started
			
			formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			date = formatter.format(datechooser.getValue());
			connection.JobStartDate(date, jobNumber);
			
			System.out.println("Start date is: "  + date);				
		}
		
		
		
		
	}
	
	


	/**
	 * Every container will be initialized at the beginning 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		jobNumberLabel.setText(jobNumber); 
		clientNameLabel.setText(name);
		workType.setText(jobtype);
		dailyTotal.setText("0.0");
		ascendingTotal.setText(String.valueOf(TM_cost));
		
		
		
		//getting leaders
		try {
			listOfWorkers = connection.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leaderList.setItems(loadName(listOfWorkers));	//list of leaders in combo box
		workerList.setItems(loadName(listOfWorkers));	//list of workers in combo box
		
		//setting AM/PM
		AmPm1.setItems(toAmpm());
		AmPm2.setItems(toAmpm());
		
		//setting hours
		fromHoursList.setItems(hours());
		toHoursList.setItems(hours());		
		
		
		//setup tree of tools	
		root = new TreeItem<String>(); //root of the tree
		root.setExpanded(true);		//root always will be expanded
				
		bulkGoods = makeBranch("Bulk Goods", root);	
		list = connection.getNameOfBulkGoods();
		for(String s : list)
			makeBranch(s, bulkGoods);		
		
		disposal = makeBranch("Disposal", root);
		list = connection.getNameOfDisposal();
		for(String s : list)
			makeBranch(s, disposal); //these are the leaves of the branch
		
		fencing = makeBranch("Fencing", root);
		masonrySupplies = makeBranch("Masonry Supply", root);
		
		blocks = makeBranch("Blocks & Stones", masonrySupplies);
		list = new ArrayList<String>(); 		
		list = connection.getNameOfBlockStones();	//getting name of stones		
		for(String s : list)	
			makeBranch(s, blocks); //adding names under block & stones		
		stones = makeBranch("Natural Stones", masonrySupplies);
		masonry = makeBranch("Masonry Supply", masonrySupplies);
		
		//Misc branch
		miscellaneous = makeBranch("Miscellaneous", root);
		
		pipes = makeBranch("Pipes", root);
		ADS = makeBranch("ADS", pipes);
		pipe1_5 = makeBranch("1.5\" Pipes", pipes);
		pipe2 = makeBranch("2\" Pipes", pipes);
		pipe3 = makeBranch("3\" Pipes", pipes);
		pipe4 = makeBranch("4\" Pipes", pipes);	
		
		//Vehicles and Equip branch
		vehicles_eqipm = makeBranch("Vehicled & Equipment", root);
		
		//Woods branch
		woods = makeBranch("Wood Products", root);
		list = connection.getNameOfWoodProducts();
		for(String s : list)
			makeBranch(s, woods);
				
		
		
		tools.setRoot(root); //setting the root
		tools.setShowRoot(false);	//root is not visible
		
		
		//set up worker table
		Name.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("name"));
		
		in.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("timein"));
		
		out.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("timeout"));
		
		hours.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("hours"));
		
		cost.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("cost"));
		
		total.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("total"));
		
		//setup tools table
		Id.setCellValueFactory(new PropertyValueFactory<Tools, Integer>("ID"));
		
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<Tools, String>("name"));
		
		unitColumn.setCellValueFactory(new PropertyValueFactory<Tools, String>("unit"));
		
		toolAmount.setCellValueFactory(new PropertyValueFactory<Tools, Double>("amount"));
		
		toolCost.setCellValueFactory(new PropertyValueFactory<Tools, Double>("billed"));
		
		toolTotalColumn.setCellValueFactory(new PropertyValueFactory<Tools, Double>("total"));
		
		
		//checks if the job is finshed
				try {
					if(connection.isJobFinished(jobNumber)){
						System.out.println(connection.isJobFinished(jobNumber));
						jobCompleted.setSelected(true);
						jobCompleted.setDisable(true);
					}
				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}
	
	/**
	 * This method adds employee to the table 
	 * @throws ParseException 
	 */
	@FXML
	public void addWorker() throws ParseException{
		String name;
		String timein;
		String timeout;
		double hours = 0.0;
		
		
			name = workerList.getValue();
			timein = fromHoursList.getValue() + " "+ AmPm1.getValue();
			timeout = toHoursList.getValue() + " " + AmPm2.getValue();
			
			
			try {
				if(!name.equals(null) && !timein.equals(null) && !timeout.equals(null)){
					System.out.println("add worker pressed" + name + timein + timeout);
					
					//if there is lunch break half hour will be subtracted
					if(lunchbreak.isSelected()){
						hours = getHours(timein, timeout) - 0.5;
					}else{
						 hours = getHours(timein, timeout);
						 }
					
					double cost = connection.getCost(name);
					double total = cost * hours;
					
					//updating daily and ascending total
					TM_cost = TM_cost + total;
					double new_cost = Math.round(TM_cost * 100.0) / 100.0;  //rounding to two decimal places
					ascendingTotal.setText(String.valueOf(new_cost));					
					dailyCost = dailyCost + total;
					dailyTotal.setText(String.valueOf(dailyCost));
					
					System.out.println(name + " cost is: "  + cost + ", hours " + (hours - 0.5 )+ ", total = " + total);
					EmployeeHours worker = new EmployeeHours(name, timein, timeout, hours, cost, total);		
					employeelist.add(worker);
					System.out.println("array size " + employeelist.size());
					
					
					workerstable.setItems(loadWorker(employeelist));
				}
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox.show("Input was incorrect while adding worker", "Error");
			}
			
		
		
	
	}
	
	/***********************************************************************
	 * Removes worker from the table
	 ***********************************************************************/
	@FXML
	public void removeWorker(){
		System.out.println("Remove Worker was pressed");
		
		ObservableList<EmployeeHours> items;
		
		items = workerstable.getItems();
		
		
		try {
			EmployeeHours e = (EmployeeHours)(workerstable.getSelectionModel().getSelectedItem()); //get selected item from the table
			items.remove(e);//deleting from the table
			
			//when employee is removed daily and ascending total will be subtracted
			TM_cost = TM_cost - e.getTotal();
			double new_cost = Math.round(TM_cost * 100.0) / 100.0;  //rounding to two decimal places
			ascendingTotal.setText(String.valueOf(new_cost));			
			dailyCost = dailyCost - e.getTotal();
			dailyTotal.setText(String.valueOf(dailyCost));
			
			//removing it from the array
			for(int i = 0; i < employeelist.size(); i++){
				if(e.equals(employeelist.get(i))){
					employeelist.remove(i);
					i = employeelist.size();
				}
			}
			
			
			System.out.println("Array size: " + employeelist.size() +"\nWorker was removed: "+e);
			System.out.println(employeelist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Employee is not selected");
		}
		
		
	}
	
	/**
	 * This method is executed when Add Items button was pressed
	 * @throws SQLException
	 */
	@FXML
	public void addItem()throws SQLException{
		double amount = 0.0;
		double total;
		
		System.out.println("Add Item Was pressed");		
		
		
		String amountfield = itemAmount.getText();	//getting the amount of items
		
		try {
			String str = tools.getSelectionModel().getSelectedItem().getValue(); //getting the name of selected item
			Tools t = connection.getTool(str);	//getting the information of the item by the given name
				
			
			if(str.length() != 0 && !str.equals(null) && amountfield.trim().length() !=0 && !amountfield.equals(null)){
				amount = Double.parseDouble(amountfield);
				total = t.getBilled() * amount;
				
				//updating daily and ascending totals
				TM_cost = TM_cost + total;
				double new_cost = Math.round(TM_cost * 100.0) / 100.0;	 //rounding to two decimal places
				ascendingTotal.setText(String.valueOf(new_cost));
				dailyCost = dailyCost + total;
				dailyTotal.setText(String.valueOf(dailyCost));
				
				t.setAmount(amount);
				t.setTotal(total);
				toolsList.add(t);
				
				toolstable.setItems(loadItem(toolsList)); //loading selected items into the table
				
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Incorrect Input", "Error");
		}
		//System.out.println("Selected item: "+str +"and amount = "+ amount +"\n" + t);
		
		
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	@FXML
	public void removeItem()throws SQLException{
		System.out.println("Remove Item Was pressed");
	
		ObservableList<EmployeeHours> items;		
		try {
			items = toolstable.getItems();
			Tools t = (Tools) toolstable.getSelectionModel().getSelectedItem();
			items.remove(t);
			
			//updating daily and ascending totals
			TM_cost = TM_cost - t.getTotal();
			double new_cost = Math.round(TM_cost * 100.0) / 100.0; //rounding to two decimal places
			ascendingTotal.setText(String.valueOf(new_cost));
			dailyCost = dailyCost - t.getTotal();
			dailyTotal.setText(String.valueOf(dailyCost));
			
			
			
			for(int i = 0; i < toolsList.size(); i++){
				if(t.equals(toolsList.get(i))){
					toolsList.remove(i);
					i = toolsList.size();
				}
			}
			
			
			System.out.println("Removed item: " + t);
			System.out.println("Item Array size: " + toolsList.size());
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Cannot remove an item\n" + e, "Error");
		}
		
	}
	
	
	//making branch
	private TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		item.setExpanded(false);
		parent.getChildren().add(item);		
		return item;
	}
	
	/**************************************************************************
	 * Returns list of strings
	 * @param list
	 * @return
	 **************************************************************************/
	private ObservableList<String> loadName(List<String> list){
		
		ObservableList<String> names = FXCollections.observableArrayList(); 		
		//adding all names to the obsrv list
		for(String str : list){
			names.add(str);
		}		
		return names;
	}
	
	//this method returns am/pm list
	private ObservableList<String> toAmpm(){
		ObservableList<String> list = FXCollections.observableArrayList();		
		list.addAll("AM", "PM");		
		return  list;
	}
	
	/**
	 * Returns worker which will be added into the table
	 * @param empl
	 * @return
	 */
	private ObservableList<EmployeeHours> loadWorker(List<EmployeeHours> empl){
		ObservableList<EmployeeHours> worker = FXCollections.observableArrayList();
		
		for(EmployeeHours e : empl){
			worker.add(e);
		}			
		return worker;		
	}
	
	/**
	 * returns Observblist of tool
	 * @param item
	 * @return
	 */
	private ObservableList<Tools> loadItem(List<Tools> items){
		ObservableList<Tools> tool = FXCollections.observableArrayList();
		
		for(Tools t : items){
			tool.add(t);
		}
		return tool;
		
	}
	
	/******************************************************************************************************************
	 * Returns list of hours
	 * @return
	 *******************************************************************************************************************/
	private ObservableList<String> hours(){
		ObservableList<String> time = FXCollections.observableArrayList();
		
		time.addAll("1:00","1:30","2:00","2:30","3:00","3:15" ,"3:30", "3:50", "4:00", "4:15", "4:20","4:30", "4:50", 
				"5:00", "5:30", "6:00", "6:30", "6:50", "7:00", "7:15", "7:30", "7:45", "8:00", "8:30", "9:00", "9:30",
				"10:00", "10:30", "11:00", "11:30", "12:00", "12:30");
		
		return time;
	}
	
	/*********************************************************************
	 * Return hours of work
	 * @param in
	 * @param out
	 * @return
	 * @throws ParseException 
	 **********************************************************************/
	private double getHours(String in, String out) throws ParseException{
		double hours = 0.0;			
		SimpleDateFormat frmt = new SimpleDateFormat("hh:mm a");
		java.util.Date date1 = frmt.parse(in);
		java.util.Date date2 = frmt.parse(out);
		
		hours = Math.round((Math.abs(date2.getTime() - date1.getTime())/3600000.0)*100.0)/100.0;		
		
		return hours;
		
	}
	

}
