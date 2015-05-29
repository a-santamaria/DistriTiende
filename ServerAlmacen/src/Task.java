
import java.io.Serializable;
import java.rmi.RemoteException;


public interface Task  extends java.rmi.Remote{

	void buy() throws RemoteException;
}
