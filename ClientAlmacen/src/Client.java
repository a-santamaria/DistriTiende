import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


public class Client {

	private static String dirServer;
	private static String username = "yo";
	private static String password;
	private static HashMap<String, Integer> productMap;
	
	
	public static void buy(){
		System.setProperty("java.security.policy",
				"C:/Users/alfredo/Documents/distribuidos/Proyecto/DistriTiende/ClientAlmacen/src/policy.policy");
	   
	
		try {
			Registry registry = LocateRegistry.getRegistry(dirServer, 1099);
			Task task = (Task)registry.lookup("rmi://"+"127.0.0.1"+":1099/Transaction");
			
			task.buy();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	}
	
	public static void main(String[] args) {
		//change to real ip
		//dirServer = "127.0.0.1";
		dirServer = "10.150.20.100";
		productMap = new HashMap<String, Integer>();
		
		Registry registro;
		try {
			/*registro = LocateRegistry.getRegistry("10.150.20.100",1099);
			InterfazServidor rmiServidor = (InterfazServidor) (registro.lookup("rmiServer")); 
			
			
			productMap = rmiServidor.startTransaction("100.0.0.0");*/
			
			
			Registry registry = LocateRegistry.getRegistry(dirServer, 1099);
			InterfazServidor rmiServer = (InterfazServidor)registry.lookup("rmi://"+"127.0.0.1"+":1099/rmiServer");
			
			productMap = rmiServer.startTransaction(InetAddress.getLocalHost().getHostAddress());
			
			
			Set<String> keys = productMap.keySet();
			
			/*for(String s : keys){
				System.out.println(s);
				System.out.println(productMap.get(s));
			}*/
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

		

	}

}
