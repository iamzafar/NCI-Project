import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddClientController implements Initializable{

	private DBConnection connection;
	private String last, first;
	private MessageBox box;
	
	@FXML
	private TextField fNameField;
	
	@FXML
	private TextField lNameField;
	
	@FXML
	private TextField phoneField;
	
	@FXML
	private TextField addressField;
	
	@FXML
	private TextField cityField;
	
	@FXML
	private TextField zipcodeField;
	
	@FXML
	private TextField emailField;
	
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	/**
	 * method for adding the client
	 * @throws Exception
	 */
	@FXML
	private void addtheClient() throws Exception{
		String fname, lname, phone, addrs, city, zip, email;
		
			fname = fNameField.getText();
			lname = lNameField.getText();
			phone = phoneField.getText();
			addrs = addressField.getText();
			city = cityField.getText();
			zip = zipcodeField.getText();
			email = emailField.getText();
		
		//this should be modified to check the correct input from the user
			
			if((fname != null && fname.trim().length() > 0)
					&& (lname != null && lname.trim().length() > 0) 
					&& (phone != null && phone.trim().length() > 0)
					&& (addrs != null && addrs.trim().length() > 0)){
		
		Client client = new Client(fname, lname, phone, addrs, city, zip, email);
		
		try {
			connection = new DBConnection();			
			connection.addClient(client);
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the database\n" + e);
		}
		
		fNameField.clear();
		lNameField.clear();
		phoneField.clear();
		addressField.clear();
		cityField.clear();
		zipcodeField.clear();
		emailField.clear();
			}
			else{
				box.show("The input is incorrect", "Error");
			}
	}
	
	@FXML
	private void cancelInput(ActionEvent event) throws Exception{
		((Node)(event.getSource())).getScene().getWindow().hide();
		
	}
	
}
