import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class StartServer {
	private HashMap<String, Integer> products;
	private String ipServer = "127.0.0.1";
	
	
	public void RegisterTransaction(){
 	    //System.setProperty("java.rmi.server.codebase",Task.class.getProtectionDomain().getCodeSource().getLocation().toString());   
		System.setProperty("java.security.policy", "C:\\Users\\Nicol�s\\Documents\\GitHub\\DistriTiende\\ServerAlmace\\src\\policy.policy");
		if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		Task t = new Transaction();
		try {
			Task engineStub = (Task)UnicastRemoteObject.exportObject(t, 0);
			Registry registry = LocateRegistry.getRegistry();
            registry.rebind("rmi://"+ipServer+":1099/Transaction", engineStub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
    public StartServer()  {
    	
    	try {
    		System.out.println("Iniciando Servidor");
    		RegisterTransaction();
    		//Leer articulos del archivo
    		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Nicol�s\\Documents\\GitHub\\DistriTiende\\ServerAlmacen\\src\\products.txt"));
    		products = new HashMap<String, Integer>();
			String line;
			while( (line = br.readLine()) != null ){
				String[] curr = line.split(" ");
				products.put(curr[0], Integer.parseInt(curr[1]));
			}
			//Recibiendo Clientes en un hilo
			tReceiveClient receiveClients = new tReceiveClient(products);
	    	Thread thread = new Thread(receiveClients);
	    	thread.start();
			//
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