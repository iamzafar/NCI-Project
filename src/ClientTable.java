import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ClientTable {
	
	private static List<Client> client;
	
	/**
	 * Constructor gets list of clients from the search that should be displayed 
	 * @param client
	 */
	public ClientTable(List<Client> client) {
		// TODO Auto-generated constructor stub
		this.client = client;		
	}

	/**
	 * Stores list of clients into the ObservableList
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public ObservableList<Client> loadClients() throws Exception, SQLException{
		List <Client> clients = new ArrayList<Client>();		
		ObservableList<Client> data = FXCollections.observableArrayList();		
		clients = client;
		
		for(Client cl : clients){
			data.add(cl);
		}
		
		return data;
	}
}
