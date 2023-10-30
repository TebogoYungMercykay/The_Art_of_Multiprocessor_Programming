import java.util.concurrent.ThreadLocalRandom;

class SystemAdmin implements Runnable {
    private final LockFreeStack<Job> queue;

    public SystemAdmin(LockFreeStack<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Job job = queue.pop();

                if (job == null) {
                    break;
                } else {
                    int limit = ThreadLocalRandom.current().nextInt(1, 25);
                    String status = job.hours < limit ? "Approved" : "Disapproved";
                    System.out.println("(OUT) [" + Thread.currentThread().getName() + "] [" + job.id + "] [" + job.hours + "] [" + status + "]");
                    // Sleep for a random amount of time between 500 and 1500 milliseconds
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
