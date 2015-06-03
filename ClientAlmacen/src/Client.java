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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale.Category;
import java.util.Map.Entry;

public class Client {

	private static String dirServer;
	private static String username = "yo";
	private static String password;
	private static HashMap<String, Integer> productMap;
	private static Integer idTransaction;
	private static BufferedReader br;
	private static HashMap<String, Integer> cart;
	private static InterfazServidor rmiServer;

	public static void main(String[] args) {
		
		System.setProperty(
				"java.security.policy",
				"C:/Users/js/Desktop/Distri/DistriTiende/ClientAlmacen/src/policy.policy");
		
		idTransaction = -1;
		cart = new HashMap();
		br = new BufferedReader(new InputStreamReader(System.in));

		String opcion = "-1";
		do {
			System.out.println("Seleccione la Opción: ");
			System.out.println("1 - Registrarse");
			System.out.println("2 - Iniciar Sección");
			System.out.println("3 - Agregar Productos a Carrito");
			System.out.println("4 - Mostrar Carrito");
			System.out.println("5 - Salir");
			try {
				opcion = br.readLine();
			

				switch (opcion) {
	
				case "1":
					
					break;
				case "2":
					startTransaction();
					System.out.println("Transaccion Iniciada con id = "+ idTransaction);
					break;
	
				case "3":
					agregarProductos();
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
		
		
		
	public static void startTransaction() {
		// change to real ip
		dirServer = "192.168.0.7";
		productMap = new HashMap<String, Integer>();

		try {

			Registry registry = LocateRegistry.getRegistry(dirServer, 1099);
			rmiServer = (InterfazServidor) registry
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
	
	private static void agregarProductos() {
		
		ArrayList<String> idProductos = new ArrayList<String>();
		while(true){
			int i = 0;
			for(Entry<String, Integer> e : productMap.entrySet()){
				idProductos.add(e.getKey());
				System.out.format("%d: %20s %10s", i++, e.getKey(), e.getValue());
				
			}
			System.out.println("-1 Salir");
			try {
				System.out.println("digite el numero de producto que quiere agregar:");
				String identi = br.readLine();
				if(identi == "-1") break;
				
				System.out.println("digite la cantidad:");
				String cantidad = br.readLine();
				
				int id = Integer.parseInt(identi.trim());
				int cant = Integer.parseInt(cantidad);
				
				if(id >= productMap.size()){
					System.err.println("no existe el producto con id "+id);
					continue;
				}
				
				if(cant <= 0 || cant > productMap.get(idProductos.get(id))){
					System.err.println("cantidad incorrecta");
					continue;
				}
				
				if(!cart.containsKey(idProductos.get(id))){
					int newValue = productMap.get(idProductos.get(id)) - cant;
					productMap.put(idProductos.get(id), newValue);
					cart.put(idProductos.get(id), cant);
					
					rmiServer.addToCart(idTransaction, idProductos.get(id), cant);
				}else{
					int newValue = productMap.get(idProductos.get(id)) - cant;
					productMap.put(idProductos.get(id), newValue);
					cart.put(idProductos.get(id), cart.get(idProductos.get(id))+cant);
					
					rmiServer.modifyItem(idTransaction, idProductos.get(id), cant);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
