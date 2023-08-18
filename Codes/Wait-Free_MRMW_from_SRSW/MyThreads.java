public class MyThreads extends Thread {
    AtomicMRMW<Integer> register;
    boolean writer;

	public MyThreads(AtomicMRMW<Integer> register_, boolean writer) {
        this.register = register_;
        this.writer = writer;
	}

	@Override
	public void run() {
        // code here
        if (writer) {
            // Perform write operation
            register.write(filterThread(Thread.currentThread().getName()));
        } else {
            // Perform read operation
            register.read();
        }
	}

    public int filterThread(String thread) {
		//Start At Thread 0
		return Character.getNumericValue(thread.charAt(7));
	}
}