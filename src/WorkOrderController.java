import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
	private List<String> listOfWorkers;
	private TreeItem<String> root, masonrySupplies, bulkGoods;
	private List<EmployeeHours> employeelist;
	
	
	@FXML
	private Button summary;
	
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
	Button add_Worker;
	
	@FXML
	TreeView<String> tools;//tree of tools
	
	TreeItem<String> base;
	TreeItem<String> masonrysupply;
	TreeItem<String> bulkgoods;
	
	
	
	
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
	
	
	
	private String jobNumber,n;
	
	
	
	public WorkOrderController(String jobNumber, String clientName, String jobType) throws SQLException, Exception {
		// TODO Auto-generated constructor stub
		this.jobNumber = jobNumber;
		connection = new DBConnection();
		listOfWorkers = new ArrayList<String>();
		root = new TreeItem<String>();
		masonrySupplies = new TreeItem<String>();
		bulkGoods = new TreeItem<String>();
		root.setExpanded(false);
		employeelist = new ArrayList<EmployeeHours>();
		
		
		
	}	
	
	
	//for testing
	public void set(String str){
		jobNumber = str;
	}	
	
	@FXML
	public void check(){
		System.out.println("Output after constructor was initialized " + jobNumber);
		
		//formatting datepicker
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		
		if(datechooser.getValue() != null)
			System.out.println(formatter.format(datechooser.getValue())); //printing preferred date format
		
	}


	/**
	 * Every container will be initialized at the beginning 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		jobNumberLabel.setText(jobNumber);
		clientNameLabel.setText("Client name");
		workType.setText("Work type");
		
		
		
		
		
		//getting leaders
		try {
			listOfWorkers = connection.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leaderList.setItems(loadName(listOfWorkers));	//list of leaders in combo box
		workerList.setItems(loadName(listOfWorkers));	//list of workers in combo box
		AmPm1.setItems(toAmpm());
		AmPm2.setItems(toAmpm());
		fromHoursList.setItems(hours());
		toHoursList.setItems(hours());
		
		
		
		
		
		//setup tree of tools
		
		base = new TreeItem<String>();
		base.setExpanded(true);
		masonrySupplies = makeBranch("Masonry Supply", root);
		bulkgoods = makeBranch("Bulk Goods", root);
		tools = new TreeView<String>(base);
		//tools.setRoot(base);
		tools.setShowRoot(false);
		
		
		
		
		
		
		
		//set up table
		Name.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("name"));
		
		in.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("timein"));
		
		out.setCellValueFactory(new PropertyValueFactory<EmployeeHours, String>("timeout"));
		
		hours.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("hours"));
		
		cost.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("cost"));
		
		total.setCellValueFactory(new PropertyValueFactory<EmployeeHours, Double>("total"));
		
		
		
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
		
		
			name = workerList.getValue();
			timein = fromHoursList.getValue() + " "+ AmPm1.getValue();
			timeout = toHoursList.getValue() + " " + AmPm2.getValue();
			
			
			try {
				if(!name.equals(null) && !timein.equals(null) && !timeout.equals(null)){
					System.out.println("add worker pressed" + name + timein + timeout);
				}
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox.show("Input was incorrect while adding worker", "Error");
			}
			
			
		double hours = getHours(timein, timeout);
		double cost = connection.getCost(name);
		double total = cost * hours;
		
		System.out.println(name + " cost is: "  + cost + ", hours " + hours + ", total = " + total);
		EmployeeHours worker = new EmployeeHours(name, timein, timeout, hours, cost, total);		
		employeelist.add(worker);
		System.out.println("array size " + employeelist.size());
		
		
		workerstable.setItems(loadWorker(employeelist));
	
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
			EmployeeHours e = (EmployeeHours)(workerstable.getSelectionModel().getSelectedItem());
			items.remove(e);//deleting from the table
			
			for(int i = 0; i < employeelist.size(); i++){
				if(e.equals(employeelist.get(i))){
					employeelist.remove(i);
				}
			}
			
			
			System.out.println("Array size: " + employeelist.size() +"\nWorker was removed: "+e);
			System.out.println(employeelist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Employee is not selected");
		}
		
		
	}
	
	//making branch
	private TreeItem<String> makeBranch(String title, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(title);
		item.setExpanded(true);
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
