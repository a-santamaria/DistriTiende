import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface InterfazServidor<String, Integer>  extends Remote{
	  public InfoTransaction startTransaction(Object ip) throws RemoteException;
	  public void addToCart(int idTransaction, String producto, int cantidad) throws RemoteException;
	  public void deleteItem(int idTransaction, Object producto) throws RemoteException;
	  public void modifyItem(int idTransaction, Object producto, int num) throws RemoteException;
	  public boolean login (String user,String passwd)throws RemoteException;

	  public boolean signin (String user,String passwd)throws RemoteException;
}
