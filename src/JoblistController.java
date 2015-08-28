import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;


public class JoblistController implements Initializable{

	private Stage stage;
	
	private List<Joblist> jobs;
	
	private DBConnection connection;
	
	@FXML
	SplitPane spltpane;
	
	@FXML
	MenuItem close;
	
	@FXML
	MenuItem newJob;
	
	@FXML
	MenuItem deleteJob;	
	
	@FXML
	CheckBox notInvoiced;
	
	@FXML
	DatePicker startdate;
	
	@FXML
	DatePicker enddate;
	
	@FXML
	TableView joblist;
	
	@FXML
	TableColumn<Joblist, Integer> jobnumber;
	
	@FXML
	TableColumn<Joblist, String> clientName;
	
	@FXML
	TableColumn<Joblist, Double> invoiced;
	
	@FXML
	TableColumn<Joblist, Double> cost;
	
	@FXML
	TableColumn<Joblist, Double> TM;
	
	@FXML
	TableColumn<Joblist, String> completion;
	
	@FXML
	TableColumn<Joblist, String> work_type;
	
	@FXML
	TableColumn<Joblist, Double> hours;
	
	@FXML
	TableColumn<Joblist, Date> start_date;
	
	@FXML
	TableColumn<Joblist, Date> end_date;
	
	@FXML
	TableColumn<Joblist, Double> materials;
	
	@FXML
	TableColumn<Joblist, String> leader;
	
	@FXML
	TableColumn<Joblist, Double> profit;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		spltpane.setDividerPositions(0.8772);		
		setUpTables();		
		
	}
	
		
	
	//close method is not working properly
	@FXML
	private void closeMenu(ActionEvent event){		
		
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	
	//I am not gonna use this method for creating the new job
	@FXML
	private void newJobMenuPressed() throws IOException{
		System.out.println("new job menuitem pressed");
		
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/NewJob.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = new Stage();		
		stage.setScene(scene);
		stage.setTitle("New Job");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void back(ActionEvent event)throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();	
		stage.hide();	
		stage.setScene(scene);
		stage.setTitle("Main");
		stage.setResizable(false);
		stage.show();
	}
	
	/**
	 * I am gonna use this method of creating of new job because with it joblist will be updated
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void newJob(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/NewJob.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();	
		stage.hide();	
		stage.setScene(scene);
		stage.setTitle("New Job");
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML
	public void deleteJob(){
		System.out.println("Delete Menu_Item was pressed");
		ObservableList<Joblist> jobs;
		jobs = joblist.getItems();		
		Joblist j = (Joblist) joblist.getSelectionModel().getSelectedItem();		
		System.out.println("Selected job: " + j);
		
		
		boolean answer  = ConfirmationBox.display("Deleting Job", "Are you sure to delete this job: " + j.getJobNum());
		if(answer){
			jobs.remove(j);
			try {
				connection.deleteJob(j.getJobID());
				MessageBox.show("Job "+ j.getJobNum()+" was deleted", "Confirmation");
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox.show("Cannot delete job", "Error");
			}
		}		
	}
	
	
	/**
	 * Displays jobs that are finished by the given date  
	 * @throws ParseException
	 * @throws SQLException
	 */
	@FXML
	public void getFinishedJobs()throws ParseException, SQLException{
		List<Joblist> list = new ArrayList<Joblist>();
		System.out.println("*********Get Finished Jobs was pressed***********");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		String start, finish;
		try {
			start = formatter.format(startdate.getValue());
			finish = formatter.format(enddate.getValue());
			System.out.println("start date: " + start + "\tend date: " + finish);
			
			list = connection.getCompletedJobs(start, finish);
			
			try {
				joblist.setItems(LoadJoblist(list));
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox box = new MessageBox();
				box.show("Could not Load joblist to the table\n" + e, "Error");
				e.printStackTrace();
			}
			
			for(Joblist j : list)
				System.out.println(j);
			
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox.show("Incorrect input\n" + e , "Error");
			e.printStackTrace();
		}
		
	}
		
	
	/**
	 * This method displays jobs that are not invoiced yet
	 * @throws Exception
	 */
	@FXML
	public void checkNotInvoiced()throws Exception{		
		System.out.println("Not invoiced was checked\nGetting query...");
		
		List<Joblist> list = new ArrayList<Joblist>();
		connection = new DBConnection();
		
		if(notInvoiced.isSelected()){
			try {
				list = connection.getNotInvoiced();	
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox box = new MessageBox();
				box.show("Could not connect to database\n" + e, "Error");
			}

			try {
				joblist.setItems(LoadJoblist(list));
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox box = new MessageBox();
				box.show("Could not Load joblist to the table\n" + e, "Error");
				e.printStackTrace();
			}
		}else if(!notInvoiced.isSelected()){
			try {
				list = connection.getAllJob();
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox box = new MessageBox();
				box.show("Could not connect to database\n" + e, "Error");
			}

			try {
				joblist.setItems(LoadJoblist(list));
			} catch (Exception e) {
				// TODO: handle exception
				MessageBox box = new MessageBox();
				box.show("Could not Load joblist to the table\n" + e, "Error");
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * Setting up tables when joblist is loaded
	 */
	private void setUpTables(){
		joblist.setEditable(true);

		//setting up table
		jobnumber.setCellValueFactory(new PropertyValueFactory<Joblist, Integer>("jobNum"));

		clientName.setCellValueFactory(new PropertyValueFactory<Joblist, String>("clientName"));

		invoiced.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("invoice"));
		setInvoiceEdittable(invoiced);

		cost.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("cost"));

		TM.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("T_M"));

		completion.setCellValueFactory(new PropertyValueFactory<Joblist, String>("completion"));

		work_type.setCellValueFactory(new PropertyValueFactory<Joblist, String>("workType"));

		hours.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("hours"));

		materials.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("materials"));

		start_date.setCellValueFactory(new PropertyValueFactory<Joblist, Date>("startdate"));

		end_date.setCellValueFactory(new PropertyValueFactory<Joblist, Date>("enddate"));

		leader.setCellValueFactory(new PropertyValueFactory<Joblist, String>("leader"));

		profit.setCellValueFactory(new PropertyValueFactory<Joblist, Double>("profit"));


		jobs = new ArrayList<Joblist>();

		try {
			connection = new DBConnection();
			jobs = connection.getAllJob();
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Could not connect to database\n" + e, "Error");
		}

		try {
			joblist.setItems(LoadJoblist(jobs));
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Could not Load joblist to the table\n" + e, "Error");
			e.printStackTrace();
		}
		
	}
	
	
	//this method will return observable list of objects that will be inserted into the table view
	private ObservableList<Joblist> LoadJoblist(List<Joblist> jobs){
		List<Joblist> joblist = new ArrayList<Joblist>();
		joblist = jobs;
		
		ObservableList<Joblist> list = FXCollections.observableArrayList();
		
		for(Joblist job : joblist)
			list.add(job);
		
		return list;
	}
	
	
	/************************************************************************************
	 * This method makes invoice column edittable 
	 * @param invoice
	 */
	private void setInvoiceEdittable(TableColumn<Joblist, Double> invoice){
		invoice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		invoice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Joblist,Double>>() {
			
			@Override
			public void handle(CellEditEvent<Joblist, Double> event) {
				// TODO Auto-generated method stub
				int jobnum = ((Joblist) event.getTableView().getItems().get(event.getTablePosition().getRow())).getJobNum();
				double billed = event.getNewValue();
				double jobcost = 0.0;
				System.out.println("new invoice amount= " + billed);
				
				((Joblist) event.getTableView().getItems().get(event.getTablePosition().getRow())).setInvoice(billed);
				
				try {
					jobcost = connection.getJobCost(jobnum);
					System.out.println("Raw Cost of job " + jobnum + " is " + jobcost);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					MessageBox box = new MessageBox();
					box.show("Could not get Raw Cost of job: " + jobnum, "Error");
				}
				
				//actual calculation
				double profit =  Math.round((((billed -jobcost) / billed) * 100) * 100.0) / 100.0;
				
				System.out.println("Invoiced: " + billed + ", raw cost: " + jobcost + ", profit: "+ profit);				
				
				 ((Joblist) event.getTableView().getItems().get(event.getTablePosition().getRow())).setProfit(profit);
				try {
					connection.changeInvoice(jobnum, billed);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				updateProfit(jobnum, profit);
				System.out.println("Profit of job "+jobnum+" was updated successfully");
			}
		});			
	}
	
	/**
	 * Connects to database and updates
	 * @param jobnum
	 * @param profit
	 */
	private void updateProfit(int jobnum, double profit){
		try {
			connection.setJobProfit(jobnum, profit);
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Could not upate job profit", "Error");
		}
	}
	
	
	
	
	

}
