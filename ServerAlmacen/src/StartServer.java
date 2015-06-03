import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class StartServer implements InterfazServidor{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> products;
	private ArrayList<Transaction> transactions;
	private static int idTransaction;
	private String ipServer;
	private HashMap<String,String> userPass;
	
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
			
            registry2.rebind("rmi://"+ipServer+":1099/rmiServer", engineStub2);
            
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
    public StartServer() throws RemoteException {
    	
    	transactions = new ArrayList<Transaction>();
    	this.idTransaction = 0;
    	userPass = new HashMap<String, String>();
    	userPass.put("user", "user");
    	try {
    		ipServer = InetAddress.getLocalHost().getHostAddress();
    		System.out.println("ipServer: "+ipServer);
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

	public InfoTransaction startTransaction(Object ip) throws RemoteException {
		System.out.println("nuevo cliente ip: "+ ip );
		transactions.add(new Transaction((String)ip, idTransaction++));
		
		return new InfoTransaction(products, idTransaction-1);
	}

	

	public void addToCart(int idTransaction, Object producto, int cantidad)
			throws RemoteException {
			transactions.get(idTransaction).addToCart((String)producto, cantidad);
			transactions.get(idTransaction).yaEscribi();
	}
	
	
	public void deleteItem(int idTransaction, Object producto){
		transactions.get(idTransaction).removeFromCart((String)producto);
		transactions.get(idTransaction).yaEscribi();
	}
	
	public void modifyItem(int idTransaction, Object producto, int num){
		transactions.get(idTransaction).updateItem((String) producto, num);
		transactions.get(idTransaction).yaEscribi();
	}
	
	
	public boolean buy(int idTransaction){
		
		int idFin = idTransaction++;
		transactions.get(idTransaction).setIdFin(idFin);
		for(Transaction t : transactions){
			
			if(t.getId() >= idTransaction ||
			  (t.getIdFin() != -1 && t.getIdFin() < idFin))
				continue;
			
			
			if(t.isEscribio()){
				return false;
			}
			
		}
		
		//actualizar productos
		for(Entry<String, Integer> e : transactions.get(idTransaction).getCart().entrySet()){
			products.put(e.getKey(), products.get(e.getValue())-e.getValue());
		}
		return true;		
	}

	public boolean login(Object user, Object passwd) throws RemoteException {
		if (userPass.containsKey(user)){
			if (userPass.get(user).equals(passwd)){
				return true;
			}
		}
		return false;
	}

	public boolean signin(Object user, Object passwd) throws RemoteException {
		if (userPass.containsKey(user)){
			return false;
		}else{
			String key = (String) user;
			String value = (String) passwd;
			userPass.put(key, value);
			return true;
		}
		
	}

}





