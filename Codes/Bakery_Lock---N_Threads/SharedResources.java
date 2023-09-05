import java.util.concurrent.locks.Lock;
// Name: Selepe Sello

public class SharedResources {
    Lock l;
	Bakery bakeryLock = new Bakery(5);
	public void access(int resource_number) {
		this.bakeryLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + ": flag[" + resource_number + "] = " + this.bakeryLock.getflag()[resource_number] + ", label[" + resource_number + "] = " + (this.bakeryLock.getMaximumLabel(this.bakeryLock.getlabel()) + 1));
			int sleeping_time = (int)(Math.random() * (801) + 200);
			Thread.sleep(sleeping_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(Thread.currentThread().getName() + " ------------------- DONE");
			this.bakeryLock.unlock();
		}
	}
}
