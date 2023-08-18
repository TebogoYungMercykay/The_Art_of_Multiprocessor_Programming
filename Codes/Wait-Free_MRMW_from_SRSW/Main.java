public class Main {
    public static void main(String[] args) {
	    MyThreads[] threads = new MyThreads[10];
        AtomicMRMW<Integer> reg = new AtomicMRMW<Integer>(5, -100);

        for(int i = 0; i < 10; i++) {
            threads[i] = new MyThreads(reg, true );
            threads[(++i)] = new MyThreads(reg, false);
        }

        for(MyThreads t : threads) {
            t.start();
        }
    }
}