import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class sample {

	public static void main(String[] args) throws SQLException, Exception{
		DBConnection conn = new DBConnection();
		/*List<Joblist> list =  new ArrayList<Joblist>();
		
		list = conn.getAllJob();
		
		for(Joblist j : list)
			System.out.println(j);//it is working
		
		List<String> str = new ArrayList<String>();
		
		str = conn.getClientName();
		
		for(String s : str)
			System.out.println(s);*/
		
		File file = new File("E:/Directory1/subDirectory/subDir2_1");
		
		/*if(!file.exists()){
			if(file.mkdir())
				System.out.println("directory created");
			else if(!file.mkdir())
				System.out.println("directory is not created");
		}
		else {
			System.out.println("Directory already exists");
		}*/
		
		
		/*if(!file.exists()){
			if(file.mkdirs())
				System.out.println("directory created");
			else if(!file.mkdirs())
				System.out.println("directory was not created");
		}
		else
			System.out.println("directory already exists");*/
		
		
		System.out.println("2015".substring(2, 4));
		
		System.out.println(Integer.parseInt("2015") + 1);
		
		int num = 1545601;
		System.out.println(num);
		
		
		/*int num = conn.getClientID("Hudson Kate");
		System.out.println(num);*/
		
			
	}
}
