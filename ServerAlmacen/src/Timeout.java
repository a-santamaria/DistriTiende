import java.sql.Time;


public class Timeout extends Thread {
	Transaction t;
	public Timeout(Transaction t_) {
	 t = t_;
	}
	
	public void run (){

		for (long i = 0;i < Long.MAX_VALUE;++i)
			for (long j = 0;j < Long.MAX_VALUE;++j);

		System.out.println("Muri� una transacci�n." + t.getIp());
		t.setFin(false);
	}
	
}
