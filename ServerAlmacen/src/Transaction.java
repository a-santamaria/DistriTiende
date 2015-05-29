
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;


public class Transaction implements Task, Serializable {

	public void buy() throws RemoteException {
		try {
			System.out.println("hello server "+ InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
