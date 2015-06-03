import java.io.Serializable;
import java.util.HashMap;


public class InfoTransaction implements Serializable{
	public HashMap<String, Integer> products;
	public int idTransaction;
	
	public InfoTransaction(HashMap<String, Integer> products, int idTransaction) {
		super();
		this.products = products;
		this.idTransaction = idTransaction;
	}
	
	
}
