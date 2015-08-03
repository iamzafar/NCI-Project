
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JOptionPane;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;


public class ClientTableController implements Initializable{

	private DBConnection dbconnection;
	private List<Client> clientlist;
	ConfirmationBox confirmbox;
	
	@FXML
	private Button addClient;
	
	@FXML
	private Button deleteClient;
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private TextField nameTextfield;
	
	@FXML
	private Button searchButton;
	
	@FXML
	private RadioButton FirstRd;
	
	@FXML
	private RadioButton LastRd;
	
	@FXML
	private TableView<Client> table;
	
	@FXML
	private TableColumn<Client, Integer> ID;
	
	@FXML
	private TableColumn<Client, String> Fname;
	
	@FXML
	private TableColumn<Client, String> Lname;
	
	@FXML
	private TableColumn<Client, String> Phone;
	
	@FXML
	private TableColumn<Client, String> Address;
	
	@FXML
	private TableColumn<Client, String> City;
	
	@FXML
	private TableColumn<Client, String> Zipcode;
	
	@FXML
	private TableColumn<Client, String> Email;
	
	/**
	 * Setting up the Columns for the table. Every column should match with the type of data.
	 * When the method setCellValueFactory(new PropertyValueFactory<Client, Integer>("id") is used,
	 * parameter of the method, in this case ("id"), should EXACTLY the same variable name from the Client class
	 * The reason of this is that method uses getters and setters of the Client class. 
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		table.setEditable(true); // whole table becomes editable
		
		//should be exact variable name defined in Client class
		ID.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id")); 
		
		
		Fname.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
		makeFirstNameEditable(Fname);	//First name column is editable
		
		Lname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
		makeLastNameEditable(Lname);	//Last name column becomes editable
		
		Phone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
		makePhoneEditable(Phone);	//phone column becomes editable
		
		Address.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
		makeAddressEditable(Address);	//address column becomes editable
		
		City.setCellValueFactory(new PropertyValueFactory<Client, String>("city"));
		
		Zipcode.setCellValueFactory(new PropertyValueFactory<Client, String>("zipcode"));
		makeZipcodeEditable(Zipcode);	//zipcode column becomes editable
		
		Email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		makeEmailEditable(Email);	// email column becomes editable
		
		ClientTable cltable;
		
		try {
			dbconnection = new DBConnection(); //establishes connection to the DB
			clientlist = new ArrayList<Client>();	
			clientlist = dbconnection.getAllClients();
			cltable = new ClientTable(clientlist);
			ObservableList<Client> data = FXCollections.observableArrayList(); //creating new ObservableList
			data = cltable.loadClients(); //storing list of clients from ClientTable class to the ObservableList		
			
			table.setItems(data);	
			
		} catch (Exception e) {
			MessageBox box = new MessageBox();
			box.show("Could not connect to Database", "Error");
		}
		
		
	}
	
	/*******************************************************************************************************************
	 * this is search method for the GUI
	 * @param event
	 * @throws SQLException
	 * @throws Exception
	 */	
	public void search() throws SQLException, Exception{
		ClientTable cltable;		
		
		//actual searching algorithm	
		try {
			dbconnection = new DBConnection(); //establishes connection to the DB
			clientlist = new ArrayList<Client>();			
			//**********************************************************
			//I have to make it search by firstname too!!!
			//Now it searches by first name as well
			String name = nameTextfield.getText();		
			
			if(FirstRd.isSelected() && name != null && name.trim().length() > 0){
				clientlist = dbconnection.searchClientByFirstname(name);			
			}else if(LastRd.isSelected() && name != null && name.trim().length() > 0){
				clientlist = dbconnection.searchClientByLastname(name);
				}else if(FirstRd.isSelected() && LastRd.isSelected() ){
					JOptionPane.showMessageDialog(null, "Choose only one from lastname and firstname!!!");
				}
			else {
				clientlist = dbconnection.getAllClients();
			}			
			
		} catch (Exception e) {
			MessageBox box = new MessageBox();
			box.show("Could not connect to Database", "Error");
		}
		
		/**sending clients, which should be displayed, to the ClientTable class 
		ClientTable class gets list of clients and stores them into ObservableList*/
		cltable = new ClientTable(clientlist);
		ObservableList<Client> data = FXCollections.observableArrayList(); //creating new ObservableList
		data = cltable.loadClients(); //storing list of clients from ClientTable class to the ObservableList		

		try {
			table.setItems(data);
		} catch (Exception e) {
			//if something is wrong with connection message dialog will pop up
			JOptionPane.showMessageDialog(null, "Could not load clients!!\n\n" + e);
			e.printStackTrace();
		}
		
		for(Client cl : clientlist){
			System.out.println(cl);
		}
		
		
	}
	
	
	/**
	 * this method will create a window that will ask information about a user
	 * @throws Exception
	 */
	@FXML
	public void addClient() throws Exception{
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/AddClient.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Add Client");
		stage.setResizable(false);
		stage.show();	
	}
	
	/**
	 * Method deletes client from the table first, then it deletes the record from
	 * database. removeclient() method removes the record from the database
	 * @throws SQLException
	 */
	@FXML
	public void deleteClient() throws SQLException{
		System.out.println("Delete is pressed");
		ObservableList<Client> delItems, items;
		
		items = table.getItems();
		System.out.println("Size of the employee list:" + items.size());
		
		delItems = table.getSelectionModel().getSelectedItems();
		
		if(confirmbox.display("Verification", "Do you really want to delete this client?")){
			for(Client cl : delItems){
				items.remove(cl);
				
				dbconnection.removeClient(cl.getId());
				
				System.out.println("deleted client: " + cl);
			}
		}
	}
	
	/**
	 * This method makes First name column editable
	 * If the user makes new input, the method updates the database
	 * @param column
	 */
	private void makeFirstNameEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				//((Employee) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmpID();
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String newname = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", new Name: "  + newname);
				
				try {
					dbconnection.updateClientFirstname(id, newname);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Method makes last name column editable
	 * @param column
	 */
	private void makeLastNameEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String lastname = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", new last_name: "  + lastname);
				
				try {
					dbconnection.updateClientLastname(id, lastname);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Method makes address column editable
	 * @param column
	 */
	private void makeAddressEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String address = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", address: "  + address);
				
				try {
					dbconnection.updateClientAddress(id, address); //	updates in database
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Method makes email column editable
	 * @param column
	 */
	private void makeEmailEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String email = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", email: "  + email);
				
				try {
					dbconnection.updateClientEmail(id, email); //	updates in database
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Method makes phone column editable
	 * @param column
	 */
	private void makePhoneEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String phone = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", phone: "  + phone);
				
				try {
					dbconnection.updateClientPhone(id, phone); //	updates in database
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Method makes zipcode column editable
	 * @param column
	 */
	private void makeZipcodeEditable(TableColumn<Client, String> column) {
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {

			@Override
			public void handle(CellEditEvent<Client, String> event) {
				// TODO Auto-generated method stub
				int id = ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).getId();
				String zpcode = event.getNewValue();
				
				System.out.println("Update client`s ID: " + id + ", zipcode: "  + zpcode);
				
				try {
					dbconnection.updateClientZipcode(id, zpcode); //	updates in database
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	
	/**
	 * Creating ObservableList of Clients. This list will be added to the TableView
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	/*private ObservableList<Client> loadClients() throws Exception, SQLException{
		List <Client> clients = new ArrayList<Client>();		
		ObservableList<Client> data = FXCollections.observableArrayList();
		
		clients = search();
		try {s
			dbconnection = new DBConnection(); //establishes connection to the DB
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Database connection Failed!!\n\n" + e);	//if something is wrong with connection message dialog will pop up
		}
		
		clients = dbconnection.getAllClients();//getting list of clients
		
		//adding every client to the Observable list
		for(Client cl : clients){
			data.add(cl);
		}
		
		
		return data;
	}*/

}
