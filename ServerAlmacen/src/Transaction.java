import java.util.HashMap;


public class Transaction {
	private String ip;
	private int idInicio;
	private int idFin;
	private boolean escribio;
	private HashMap<String, Integer> cart;
	
	
	
	public Transaction(String ip, int id) {
		super();
		this.ip = ip;
		this.idInicio = id;
		escribio = false;
		idInicio = -1;
		idFin = -1;
		cart = new HashMap<String, Integer>();
	}
	
	void yaEscribi(){
		escribio = true;
	}
	
	void addToCart(String product, int cant){
		cart.put(product, cant);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getId() {
		return idInicio;
	}

	public void setId(int id) {
		this.idInicio = id;
	}

	public boolean isEscribio() {
		return escribio;
	}

	public void setEscribio(boolean escribio) {
		this.escribio = escribio;
	}

	public HashMap<String, Integer> getCart() {
		return cart;
	}

	public void setCart(HashMap<String, Integer> cart) {
		this.cart = cart;
	}

	public int getIdInicio() {
		return idInicio;
	}

	public void setIdInicio(int idInicio) {
		this.idInicio = idInicio;
	}

	public int getIdFin() {
		return idFin;
	}

	public void setIdFin(int idFin) {
		this.idFin = idFin;
	}

	public void removeFromCart(String producto) {
		cart.remove(producto);
		
	}

	public void updateItem(String producto, int num) {
		cart.put(producto, cart.get(producto)+num);
		
	}
	
	
}
