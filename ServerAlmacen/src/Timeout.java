
public class Timeout extends Thread {
	Transaction t;
	public Timeout(Transaction t_) {
	 t = t_;
	}
	
	public void run (){

		long time = 100000000;
		while (time-- != 0);

		System.out.println("Muri� una transacci�n." + t.getIp());
		t.setFin(false);
	}
	
}
