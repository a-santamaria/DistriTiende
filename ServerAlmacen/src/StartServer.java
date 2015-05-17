import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class StartServer {
	

	private tReceiveClient receiveClients;
	private HashMap<String, Integer> products;
	
	
    public StartServer()  {
    	
    	try {
			
    		//read products from file
    		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\sala_a\\workspace-kepler2\\DistriTienda\\ServerAlmacen\\src\\products.txt"));
    		products = new HashMap<String, Integer>();
			String line;
			while( (line = br.readLine()) != null ){
				String[] curr = line.split(" ");
				products.put(curr[0], Integer.parseInt(curr[1]));
			}
			
			//thread receive new Clients
			receiveClients = new tReceiveClient(products);
	    	new Thread(receiveClients).start();
	    	
	    	
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
    }
	
	public static void main(String[] args) {
        new StartServer();
    }
}