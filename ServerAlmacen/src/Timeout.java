
public class Timeout extends Thread {
	Transaction t;
	public Timeout(Transaction t_) {
	 t = t_;
	}
	
	public void run (){
		try {
			Thread.sleep(600000);//100 min
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Murió una transacción." + t.getIp());
		t.setFin(false);
	}
}
