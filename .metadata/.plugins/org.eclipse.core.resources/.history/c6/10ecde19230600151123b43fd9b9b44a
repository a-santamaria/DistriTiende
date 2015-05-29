import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

public class tReceiveClient implements Runnable{
    Thread hilo;
    private HashMap<String, Integer> products;
    
	public tReceiveClient(HashMap<String, Integer> products) {
		super();
		this.products = products;
	}
	
	public String listenNewClients(){  
	        String direccionCliente="";
	        try{
	        	DatagramSocket socket =new DatagramSocket(8888);
	            socket.setBroadcast(true);
	            byte[] mensajeRecibido = new byte[15000];
	            DatagramPacket paqueteRecibido=new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
	            socket.receive(paqueteRecibido);
	            System.out.println("Ip Origen: "+paqueteRecibido.getAddress().getHostAddress());
	            //Login
	            direccionCliente=paqueteRecibido.getAddress().getHostAddress();          
	            socket.close();
	        }catch (Exception e){
	        
	        }
	    return direccionCliente;
	}
	
    public void run() {
      while(true){
    	  String ipCliente = listenNewClients();
    	  if(ipCliente != null){
    		 //Enviar productos
    		  try {
    				DatagramSocket socket =new DatagramSocket();
    				
    				StringBuilder productsString = new StringBuilder();
    				for(Entry<String, Integer> e : products.entrySet()){
    					productsString.append(e.getKey());
    					productsString.append(",");
    					productsString.append(e.getValue());
    					productsString.append(";");
    				}
    				
    				byte[] mensajeEnviar = productsString.toString().getBytes();
    	            DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,InetAddress.getByName(ipCliente),8887);
    	            socket.send(paqueteEnviar);
    	            System.out.println("Productos enviados: "+ productsString.toString());
    	            socket.close();
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
    }
    
  }

	