import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface InterfazServidor<String, Integer>  extends Remote{
	  public HashMap<String, Integer> startTransaction(String ip)  throws RemoteException;
	  public void addToCart(int idTransaction, String producto, int cantidad) throws RemoteException;
	  public void deleteItem(int idTransaction, Object producto) throws RemoteException;
	  public void modifyItem(int idTransaction, Object producto, int num) throws RemoteException;
}
