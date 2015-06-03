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
import java.util.ArrayList;
import java.util.HashMap;

public class StartServer implements InterfazServidor{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> products;
	private ArrayList<Transaction> transactions;
	
	public void RegisterTransaction(){
 	    //System.setProperty("java.rmi.server.codebase",Task.class.getProtectionDomain().getCodeSource().getLocation().toString());   
		//System.setProperty("java.security.policy", "C:/Users/sala_a/workspace-kepler2/DistriTienda/ServerAlmacen/src/policy.policy");
		System.setProperty("java.security.policy", "C:/Users/alfredo/Documents/distribuidos/Proyecto/DistriTiende/ServerAlmacen/src/policy.policy");
		
		if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		
		try {			
			InterfazServidor engineStub2 = (InterfazServidor)UnicastRemoteObject.exportObject(this, 0);
			Registry registry2 = LocateRegistry.getRegistry();
            registry2.rebind("rmi://127.0.0.1:1099/rmiServer", engineStub2);
            
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
    public StartServer() throws RemoteException {
    	
    	try {
    		RegisterTransaction();
    		System.out.println("----Registré Transaction-----------");
    		//read products from file
    		//BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\sala_a\\workspace-kepler2\\DistriTienda\\ServerAlmacen\\src\\products.txt"));
    		BufferedReader br = new BufferedReader(new FileReader("products.txt"));
    		products = new HashMap<String, Integer>();
			String line;
			while( (line = br.readLine()) != null ){
				String[] curr = line.split(" ");
				products.put(curr[0], Integer.parseInt(curr[1]));
			}
	    	
			
			
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
        try {
			new StartServer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public HashMap startTransaction(Object ip) throws RemoteException {
		System.out.println("nuevo cliente ip: "+ ip );
		transactions.add(new Transaction((String)ip, transactions.size()));
		return products;
	}

	

	public void addToCart(int idTransaction, Object producto, int cantidad)
			throws RemoteException {
			transactions.get(idTransaction).addToCart((String)producto, cantidad);
			transactions.get(idTransaction).yaEscribi();
	}
	
	
	public void buy(int idTransaction){
		
	}
}





