public class Main {
    public static void main(String[] args) throws InterruptedException {
        LockFreeQueue<Job> queue = new LockFreeQueue<>();

        Thread[] threads = new Thread[6];

        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new Developer(queue));
            threads[i].start();
        }

        for (int i = 4; i < 6; i++) {
            threads[i] = new Thread(new SystemAdmin(queue));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}