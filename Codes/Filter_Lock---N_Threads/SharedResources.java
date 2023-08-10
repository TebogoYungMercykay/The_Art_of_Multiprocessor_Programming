import java.util.concurrent.locks.Lock;
// Name: Selepe Sello
// Student Number: uXXXXXXXX

public class SharedResources {
    Lock l;
	Filter filterLock = new Filter(5);
	public void access(int resource_number){
		this.filterLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + ": level[" + resource_number + "] = " + this.filterLock.getLevel()[resource_number] + ", victim[" + this.filterLock.getLevel()[resource_number] + "] = " + this.filterLock.getVictim()[this.filterLock.getLevel()[resource_number]]);
			int sleeping_time = (int)(Math.random() * (1000 - 200 + 1) + 200);
			Thread.sleep(sleeping_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(Thread.currentThread().getName() + " ------------------- DONE");
			this.filterLock.unlock();
		}
	}
}