import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;


public class Client {

	private static String dirServer;
	private static String username = "yo";
	private static String password;
	private static HashMap<String, Integer> productMap;
	
	
	public static String listenProducts(){
		  System.out.println("-------------Escuchando Productos------------");   
	        String direccionCliente="";
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
	    return direccionCliente;
	}
	
	public static void main(String[] args) {
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
