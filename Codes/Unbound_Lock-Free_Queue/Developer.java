import java.util.concurrent.ThreadLocalRandom;

class Developer implements Runnable {
    private final LockFreeQueue<Job> queue;

    public Developer(LockFreeQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                int hours = ThreadLocalRandom.current().nextInt(1, 25);
                Job job = new Job(i, hours, false);
                queue.enq(job);
                System.out.println("(IN) [" + Thread.currentThread().getName() + "] [" + job.id + "] [" + job.hours + "]");
                Thread.sleep(1000);
            }
            // Last Job at the End
            Job lastJob = new Job(-1, -1, true);
            queue.enq(lastJob);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}