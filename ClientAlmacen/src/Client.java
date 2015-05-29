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


public class Client {

	private static String dirServer;
	private static String username = "yo";
	private static String password;
	private static HashMap<String, Integer> productMap;
	
	
	public static void listenProducts(){
		  System.out.println("-------------Escuchando Productos------------");
		  boolean flag = true;
		  while(flag){
	        try{
	        	DatagramSocket socket =new DatagramSocket(8887);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);

	            socket.receive(paqueteRecibido);
	            System.out.println("Ip recibida: "+paqueteRecibido.getAddress().getHostAddress());
	            String productsString = new String(paqueteRecibido.getData());
	            System.out.println("Productos: " + productsString);
	
	            socket.close();
	            if(!paqueteRecibido.getAddress().getHostAddress().equals(dirServer)){
	            	continue;
	            }
	            flag = false;
	            String[] products = productsString.split(";");
	            for(String s : products){	            	
	            	String[] productInfo = s.split(",");	            	
	            	productMap.put(productInfo[0], Integer.parseInt(productInfo[1]));	  
	            	System.out.println("agregue"+ productInfo[0]);
	            	System.out.println(">>>>>>>>>>>>>>>>>tam de map "+productMap.size());
	            }
	            
	            System.out.println(">>>>>>>ultimo>>>>>>>>>>tam de map "+productMap.size());
	            
	        }catch (Exception e){
	        
	        }
		  }
	}
	
	public static void buy(){
		System.setProperty("java.security.policy",
				"C:\\Users\\Nicol�s\\Documents\\GitHub\\DistriTiende/ClientAlmacen/src/policy.policy");
		if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
	
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
		dirServer = "127.0.0.1";
		productMap = new HashMap<String, Integer>();
		try {
			DatagramSocket socket =new DatagramSocket();
			byte[] mensajeEnviar = username.getBytes();
            DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,
            		InetAddress.getByName(dirServer),8888);
            System.out.println("voy a enviar");
            socket.send(paqueteEnviar);
            System.out.println("ya envie");
            socket.close();
            listenProducts();
            
            buy();
            
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
