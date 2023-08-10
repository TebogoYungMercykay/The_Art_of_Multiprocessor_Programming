import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Selepe Sello
// Student Number: u20748052

public class Bakery implements Lock
{
	private volatile boolean[] flag;
	private volatile int[] label;
	private int numThreads;

	public Bakery(int numThreads) {
		this.numThreads = numThreads;
		this.flag = new boolean[this.numThreads];
		this.label = new int[this.numThreads];

		for(int i = 0; i < this.numThreads; i++) {
			this.flag[i] = false;
			this.label[i] = 0;
		}
	}

	@Override
	public void lock() {
		// Get the Current thread's ID
		int index = (filterThread(Thread.currentThread().getName())) % 4;

		this.flag[index] = true;
		this.label[index] = getMaximumLabel(label);

		for(int j = 0; j < this.numThreads; j++) {
			while((j != index) && this.flag[j] && (this.label[j] < this.label[index]) || (this.label[j] == this.label[index]) && (j < index)) {
				// Waiting
			}
		}
	}

	boolean[] getflag() {
		return this.flag;
	}

	int[] getlabel() {
		return this.label;
	}

	@Override
	public void unlock() {
		int index = filterThread(Thread.currentThread().getName());
		this.flag[index] = false;
	}

	public int getMaximumLabel(int[] labels_array) {
		int maximum_value = Integer.MIN_VALUE;
		for(int i = 0; i < labels_array.length; i++) {
			if(labels_array[i] > maximum_value) {
				maximum_value = labels_array[i];
			}
		}
		return maximum_value + 1;
	}

	public int filterThread(String thread) {
		return Character.getNumericValue(thread.charAt(7));
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}
}