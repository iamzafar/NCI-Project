import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class NewJobController implements Initializable{

	private DBConnection conn;
	
	@FXML
	ComboBox<String> clientName;
	
	@FXML
	ComboBox<String> workType;
	
	@FXML
	ComboBox<String> year;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		clientName.setEditable(true);
		workType.setEditable(true);
		
		year.setItems(loadYear());//setting the year
		workType.setItems(loadWorktype());
		
		try {
			clientName.setItems(loadClientName()); //setting client name in the combo list
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * loads client name into the Observable list
	 * @return
	 * @throws Exception
	 */
	private ObservableList<String> loadClientName() throws Exception{
		
		conn = new DBConnection();
		ObservableList<String> name = FXCollections.observableArrayList();
		List<String> list = new ArrayList<String>();
		list = conn.getClientName();
		
		for(String str : list)
			name.add(str);		
		
		return name;
	}
	

	/**
	 * Overloaded method of loading names into Observable list
	 * @param names
	 * @return
	 * @throws Exception
	 */
	private ObservableList<String> loadClientName(List<String> names) throws Exception{
			
			
			ObservableList<String> name = FXCollections.observableArrayList();
			List<String> list = new ArrayList<String>();
			list = names;
			
			for(String str : list)
				name.add(str);		
			
			return name;
		}
	

	
	/**********************************************************************************************
	 * Returns worktype list
	 * @return
	 * @throws Exception
	 */
	private ObservableList<String> loadWorktype() {		
		
		ObservableList<String> type = FXCollections.observableArrayList();
		type.addAll("Maintanence", "Landscaping", "Hardscape", "Masonry", "Drainage", "Combination", "Other");			
		
		return type;		
	}
	
	
	
	/*********************************************************************************
	 * List of the year
	 * @return
	 */
	private ObservableList<String> loadYear(){
		
		ObservableList<String> year  =FXCollections.observableArrayList();
		year.addAll("2015" , "2016" , "2017", "2018");
		
		return year;
	}
	
	/*********************************************************************************
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	@FXML
	void searchPressed() throws SQLException, Exception{
		
		conn = new DBConnection();
		String name = clientName.getValue();		
		
		try {
			if(name.trim().length() != 0){
				List<String> list = conn.searchClientName(name);
				System.out.println("searching " + name);			
				clientName.setItems(loadClientName(list));
				
			}
		} catch (Exception e) {	//if there`s no input message will be displayed
			// TODO: handle exception
			new MessageBox().show("Enter the name", "Incorrect Input");  
		}		
	}
	
	
	
	/**************************************************************************************
	 * When create button pressed all condition will be checked and 
	 * it will return to the joblist window
	 * @param event
	 * @throws IOException
	 ***************************************************************************************/
	@FXML
	public void createPressed(ActionEvent event)throws IOException, SQLException{
		
		String ID;
		int clientID;		
		
		//conditions
		
		//getting values from the comboboxes
		String name = clientName.getValue();
		String wrkTp = workType.getValue();
		String compyear = year.getValue();
		
		
		try {
			if(name.trim().length() != 0 && !name.equals(null) 
					&& wrkTp.trim().length() != 0 && compyear.trim().length() != 0){
												
				clientID = conn.getClientID(name);//getting id
				ID = Integer.toString(clientID) ; 
				System.out.println("ID is: " + ID + ", worktype: " + wrkTp + ", year: " + compyear);
				
				int jobnum = createJobnum(compyear, ID, name);
				conn.insertJobnumber(jobnum, wrkTp, clientID, Integer.parseInt(compyear));
				
				
				Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Joblist.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();	
				stage.hide();	
				stage.setScene(scene);
				stage.setTitle("Joblist");
				stage.setResizable(false);
				stage.show();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			MessageBox box = new MessageBox();
			box.show("Wrong input", "Error");
		}		
	}
	
	/*********************************************************************************************************
	 * This method will create the folder with specific path
	 * @param year
	 * @param id
	 * @param name
	 * @return
	 * @throws IOException
	 *********************************************************************************************************/
	private int createJobnum(String year, String id, String name) throws IOException{
		String ID;
		String index = "01";
		MessageBox box = new MessageBox();
		
		String Year = year.substring(2, 4);
		
		//creating the id for the client;
		//id for the client consists of 3 digits, e.g. 001;
		if(id.length() == 1)
			ID = "00" + id;
		else if(id.length() == 2)
			ID = "0" + id;
		else
			ID = id;
		
		String jobnum = Year + ID + index;
		String path = "E:/Natural Concerns/Client Folder - NEW " + year + "/" + name + "/" + jobnum;
		
		File file = new File(path);				
		
		int number = Integer.parseInt(jobnum);
		
		while(file.exists()){
			number = number + 1;
			path = "E:/Natural Concerns/Client Folder - NEW " + year + "/" + name + "/" + number;
			System.out.println("New JobNumber: "+number);
			file = new File(path);
		}
		
		if(!file.exists()){
			file.mkdirs(); //I chose mkdirs() because it will create the path if it does not exist
			box.show("Job number " +  number + " was created",  "Success");
		}
		else if(!file.mkdirs()){
			System.out.println("jobnum was not created");
			box.show("Job numer was not created", "ERROR");
		}
		
		
		return number;
		
	}
	
	
	
	

}
