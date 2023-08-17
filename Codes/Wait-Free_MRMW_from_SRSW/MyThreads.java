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
            register.write(ThreadID.get());
        } else {
            // Perform read operation
            register.read();
        }
	}
}