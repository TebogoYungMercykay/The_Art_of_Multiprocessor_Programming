import java.util.concurrent.ThreadLocalRandom;

class SystemAdmin implements Runnable {
    private final LockFreeQueue<Job> queue;

    public SystemAdmin(LockFreeQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Job job = queue.deq();
                if (job == null || job.isLastJob == true) {
                    break;
                } else {
                    int limit = ThreadLocalRandom.current().nextInt(1, 25);
                    String status = job.hours < limit ? "Approved" : "Disapproved";
                    System.out.println("(OUT) [" + Thread.currentThread().getName() + "] [" + job.id + "] [" + job.hours + "] [" + status + "]");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}