import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.CountDownLatch;

class Developer implements Runnable {
    private final LockFreeStack<Job> queue;
    private final CountDownLatch latch;

    public Developer(LockFreeStack<Job> queue, CountDownLatch latch) {
        this.queue = queue;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                int hours = ThreadLocalRandom.current().nextInt(1, 25);
                Job job = new Job(i, hours, false);
                queue.push(job);
                System.out.println("(IN) [" + Thread.currentThread().getName() + "] [" + job.id + "] [" + job.hours + "]");
                if (i == 0) {
                    // Signal that the first job has been added
                    latch.countDown();
                }
                // Sleep for a random amount of time between 500 and 1500 milliseconds
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
