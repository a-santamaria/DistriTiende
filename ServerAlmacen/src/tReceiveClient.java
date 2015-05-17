import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class tReceiveClient implements Runnable{
    Thread hilo;
    private HashMap<String, Integer> products;
    
    
    
    
	public tReceiveClient(HashMap<String, Integer> products) {
		super();
		this.products = products;
	}

	public String listenNewClients(){
		  System.out.println("-------------Escuchando clientes------------");   
	        String direccionCliente="";
	        try{
	        	DatagramSocket socket =new DatagramSocket(8888);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);

	            socket.receive(paqueteRecibido);
	            System.out.println("Ip recibida: "+paqueteRecibido.getAddress().getHostAddress());
	            System.out.println("Ip recibida: "+new String(paqueteRecibido.getData()));
	            direccionCliente=paqueteRecibido.getAddress().getHostAddress();
	            
	            socket.close();
	            
	            
	            
	        }catch (Exception e){
	        
	        }
	    return direccionCliente;
	}
	
    public void run() {
      while(true){
    	  String add = listenNewClients();
    	  if(add != null){
    		  SendProducts sendProductsToClient = new SendProducts(add, products);
    		  sendProductsToClient.run();
    	  }
      }
    
    }

	
}