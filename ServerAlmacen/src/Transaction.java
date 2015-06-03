import java.util.HashMap;


public class Transaction {
	public String ip;
	public int id;
	public boolean escribio;
	HashMap<String, Integer> cart;
	
	
	
	public Transaction(String ip, int id) {
		super();
		this.ip = ip;
		this.id = id;
		escribio = false;
	}
	
	void yaEscribi(){
		escribio = true;
	}
	
	void addToCart(String product, int cant){
		cart.put(product, cant);
	}
}
