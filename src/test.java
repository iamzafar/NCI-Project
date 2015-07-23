/**
 * this class is for the test
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class test extends Application {
	
	/**
	 * gui for the main window
	 * for the test purposes I am using different fxmls
	 */
	public void start(Stage primaryStage) {
		try 
		{
			// it connects the fxml to the root
			//from the class instance (getClass()) it gets resource -> getResource
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
			
			
			//set the dimension from the scenebuilder -> layout of the AnchorPane
			Scene scene = new Scene(root,452,273);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setTitle("Main Menu");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
/**************************************************************************************************
 * MAIN METHOD
 * @param args
 * @throws SQLException
 * @throws Exception
 */
	public static void main(String[] args) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
		/**
		 * TESTING PROGRAM *******************************************************************************
		 */
		
		launch(args);
		//getting connection to the database
		DBConnection conn = new DBConnection();
		
		
		
		
		
		//all data will be stored in arraylist
		List<Client> list = new ArrayList<Client>();
		List<Client> searchlist = new ArrayList<Client>();
		//using query to get information about all clients
		//list = conn.getAllEmployees();
		
		
		/*System.out.println("****************Before adding new client into the db********************");
		//printing the query
		//getters and setter are essential in Client class because every information can be retrieved using getters 
		for(Client cl : list){
			//System.out.println(cl.getZipcode());
			System.out.println(cl);
			}*/
		
		
		//Client client = new Client("Ashley", "Thompson", "4434859648", "65 North Ave", "Baltimore", "21215", null);
		
		//conn.addClient(client);
		
		/*System.out.println("******************After adding new client*******************");
		for(Client cl : list){
			System.out.println(cl.getZipcode());
			System.out.println(cl);
			}*/
		
		//Client client2 = new Client("Marco", "Polo", "6548987789", "900 Holland St", "Baltimore", "21005", "marco@example.com");
		//conn.addClient(client2);
		//conn.addClient(new Client("Kate", "Hudson", "4565689497", "1506 Church St", "Owings Mills", "21008", "kate@example.com"));
		//conn.addClient(new Client("John", "Kennedy", "8476598649", "5 Baker St", "New York", "11218", "john@example"));
		/*list = conn.getAllClients();
		System.out.println("*****************Adding another client*********************");
		for(Client cl : list){
			//System.out.println(cl.getZipcode());
			System.out.println(cl);
			}
		
		System.out.println("\n******************searching by lastname************************");
		System.out.println("'a' is entered");
		searchlist = conn.searchClientByLastname("a");
		for(Client cl : searchlist){
			//System.out.println(cl.getZipcode());
			System.out.println(cl);
			}
		
		System.out.println("\n******************searching by firstname************************");
		System.out.println("'j' is entered");
		searchlist = conn.searchClientByFirstname("j");
		for(Client cl : searchlist){
			//System.out.println(cl.getZipcode());
			System.out.println(cl);
			}*/

	}

}
