import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;


public class SendProducts extends Thread {
	private String clientIP;
	private HashMap<String, Integer> products;

	public SendProducts(String clientIP, HashMap<String, Integer> products) {
		super();
		this.clientIP = clientIP;
		this.products = products;
		run();
	}
	
	public SendProducts() {
		// TODO Auto-generated constructor stub
	}

	public void SendProducts(){
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
            DatagramPacket paqueteEnviar= new DatagramPacket(mensajeEnviar, mensajeEnviar.length,
            		InetAddress.getByName(clientIP),8887);
            System.out.println("voy a enviar products "+ productsString.toString());
            socket.send(paqueteEnviar);
            System.out.println("ya envie products");
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
	
	@Override
	public void run() {
		SendProducts();
	}
}
