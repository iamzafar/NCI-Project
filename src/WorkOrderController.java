import java.net.URL;
import java.sql.SQLException;
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


public class WorkOrderController implements Initializable{

	private DBConnection connection;
	private List<String> listOfWorkers;
	private TreeItem<String> root, masonrySupplies, bulkGoods;
	
	
	
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
	CheckBox lunchbreak;
	
	@FXML
	Button add_Worker;
	
	@FXML
	TreeView<String> tools;
	
	
	
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
		
		//setup tree of tools
		
		
		
		
		
		
		
		//set up table
		
		
	}	
	
	private ObservableList<String> loadName(List<String> list){
		
		ObservableList<String> names = FXCollections.observableArrayList(); 
		
		//adding all names to the obsrv list
		for(String str : list){
			names.add(str);
		}
		
		return names;
	}

}
