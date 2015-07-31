import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class WONumberController implements Initializable{

	@FXML
	TextField jobnumber;
	
	@FXML
	Label error;
	
	DBConnection conn;
	private double amount;
	
	
	@FXML
	public void submit(ActionEvent event) throws SQLException, Exception{
		String number = jobnumber.getText();
		conn = new DBConnection();
		
		amount = conn.getJobTMCost(number);
		String [] data = new String[2];
		data = conn.getClientName_WorkType(number);
		
		//verifying whether the input exists and it is number
		if(number.trim().length() !=0 && isNumber(number) && (number.length() == 7) && !data[0].equals(null) && !data[1].equals(null)){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkOrder.fxml"));
			WorkOrderController wo = new WorkOrderController(number, data[0], data[1], amount);
			
			loader.setController(wo);
			Parent parent = loader.load();		
			Scene scene = new Scene(parent);
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.setTitle("Word Order");
			stage.setResizable(false);
			stage.show();
		}		
		else
			error.setVisible(true);	
	}	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		error.setVisible(false);		
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
	
	

}
