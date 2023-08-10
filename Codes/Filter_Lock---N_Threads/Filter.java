import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Selepe Sello
// Student Number: uXXXXXXXX

public class Filter implements Lock
{
	private volatile int[] level;
	private volatile int[] victim;
	private int numThreads;

	public Filter(int numThreads) {
		this.numThreads = numThreads;
		this.level = new int[this.numThreads];
		this.victim = new int[this.numThreads];

		for(int i = 0; i < this.numThreads; i++) {
			this.level[i] = 0;
			this.victim[i] = 0;
		}
	}

	@Override
	public void lock() {
		// Get the Current thread's ID
		int index = (filterThread(Thread.currentThread().getName())) % 4;

		for(int i = 1; i < this.numThreads; i++) {
			this.level[index] = i;
			this.victim[i] = index;

			for(int j = 0; j < this.numThreads; j++) {
				while((j != index) && (this.level[j] >= i) && (this.victim[i] == index)) {
					// Waiting
				}
			}
		}
	}

	int[] getLevel() {
		return this.level;
	}

	int[] getVictim() {
		return this.victim;
	}

	@Override
	public void unlock() {
		int index = (filterThread(Thread.currentThread().getName())) % 4;
		this.level[index] = 0;
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