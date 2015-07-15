import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainController implements Initializable{

	@FXML
	private Button employee;
	
	@FXML
	private Button client;
	
	@FXML
	private Button Job;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/***************************************************************************************
	 * if employee button was pressed
	 */
	@FXML
	private void pressEmployee() throws Exception{
		//EmployeeTabelController table = new EmployeeTabelController("name");
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/EmployeeTable.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Employee Information");
		stage.setResizable(false);
		stage.show();
		
	}
	
	/***************************************************************************************
	 * if client button was pressed
	 */
	@FXML
	private void pressClient() throws Exception{
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/ClientTable.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Client Information");
		stage.setResizable(false);
		stage.show();
	}
	
	
	/***************************************************************************************
	 * if job button was pressed
	 */
	@FXML
	private void pressJob(ActionEvent event) throws Exception{
		//this should be done
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Joblist.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();	
		stage.hide();
		stage.setScene(scene);
		stage.setTitle("Joblist");
		stage.setResizable(false);
		stage.show();
	}
	
	/*
	 * for Work Order button
	 */
	@FXML
	private void pressWorkOrder() throws Exception{	
			//instantiating constructor		
		
		Parent parent = FXMLLoader.load(getClass().getResource("/fxml/WONumber.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Work Order");
		stage.setResizable(false);
		stage.show();
		
		
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkOrder.fxml"));
		WorkOrderController wo = new WorkOrderController("ashdkjhsahd", "dkjfhkjsdh", "adkfjhsdf");
		
		loader.setController(wo);
		Parent parent = loader.load();		
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Word Order");
		stage.setResizable(false);
		stage.show();*/
	}

}
