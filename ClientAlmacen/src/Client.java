import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	private static Integer idTransaction;

	
	
	
	public static void buy() {
		System.setProperty(
				"java.security.policy",
				"C:/Users/alfredo/Documents/distribuidos/Proyecto/DistriTiende/ClientAlmacen/src/policy.policy");

		try {
			Registry registry = LocateRegistry.getRegistry(dirServer, 1099);
			Task task = (Task) registry.lookup("rmi://" + "127.0.0.1"
					+ ":1099/Transaction");

			task.buy();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void startTransaction() {
		// change to real ip
		dirServer = "localhost";
		productMap = new HashMap<String, Integer>();

		try {

			Registry registry = LocateRegistry.getRegistry(dirServer, 1099);
			InterfazServidor rmiServer = (InterfazServidor) registry
					.lookup("rmi://" + dirServer + ":1099/rmiServer");

			InfoTransaction info = rmiServer.startTransaction(InetAddress.getLocalHost()
					.getHostAddress());
			
			productMap = info.products;
			idTransaction = info.idTransaction;
			/*
			 * Set<String> keys = productMap.keySet(); for(String s : keys){
			 * System.out.println(s); System.out.println(productMap.get(s)); }
			 */
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

	public static void main(String[] args) {
		
		idTransaction = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String opcion = "-1";
		do {
			System.out.println("Seleccione la Opción: ");
			System.out.println("1 - Registrarse");
			System.out.println("2 - Iniciar Sección");
			System.out.println("3 - Seleccionar Productos");
			System.out.println("4 - Mostrar Carrito");
			System.out.println("5 - Salir");
			try {
				opcion = br.readLine();
			

				switch (opcion) {
	
				case "1":
					
					break;
				case "2":
					startTransaction();
					System.out.println("id Transaction: " + idTransaction);
					break;
	
				case "3":
	
				
					break;
				case "4":
				
					break;
	
				case "5":
					System.out.println("--CHAO--");
					break;
				default:
					System.err.println("--OPCION INVALIDA--");
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!opcion.equals("5"));

	}

}
