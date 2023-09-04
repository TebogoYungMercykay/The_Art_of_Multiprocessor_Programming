import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Printer {
    Lock lock;
    MCSQueue queue = new MCSQueue(); // Either MCSQueue or Timeout

    public Printer(Lock l) {
        this.lock = l;
    }

    public void Print(int requestNumber) {
        System.out.println(Thread.currentThread().getName() + ":Request-" + requestNumber + " printing request: ");
        try {
            Random rand = new Random();
            int sleepTime = rand.nextInt(801) + 200;
            Thread.sleep(sleepTime);
        } catch(Exception exception){
			exception.printStackTrace();
		} finally {
            System.out.print("QUEUE: ");
            System.out.println(this.queue.requestInfoString(requestNumber));
        }
    }
}