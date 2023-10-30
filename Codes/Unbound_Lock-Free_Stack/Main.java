import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LockFreeStack<Job> queue = new LockFreeStack<>();
        int numDevelopers = 4;
        CountDownLatch latch = new CountDownLatch(numDevelopers);

        Thread[] threads = new Thread[6];

        for (int i = 0; i < numDevelopers; i++) {
            threads[i] = new Thread(new Developer(queue, latch));
            threads[i].start();
        }

        latch.await(); // Wait until all developers have added their first job

        for (int i = numDevelopers; i < 6; i++) {
            threads[i] = new Thread(new SystemAdmin(queue));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}

