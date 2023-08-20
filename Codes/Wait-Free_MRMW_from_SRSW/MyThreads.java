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
        if (this.writer) {
            // Generate a random value
            Integer writtenValue = Integer.valueOf((int) ((Math.random() * 81) + 10));
            // Perform write operation
            this.register.write(writtenValue);
            // Output:
            System.out.println("(writer)[" + Thread.currentThread().getName() + "] : [" + writtenValue + "]" );
        } else {
            // Perform read operation
            System.out.println("(reader)[" + Thread.currentThread().getName() + "] : [" + this.register.read() + "]" );
        }
	}
}
