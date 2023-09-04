import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;

public class Timeout implements Lock {
    static QNode AVAILABLE = new QNode();
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public Timeout() {
        tail = new AtomicReference<>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        // TODO: About Blank
    }

    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        QNode qNode = new QNode();
        myNode.set(qNode);
        qNode.predecessor = null;
        QNode myPredecessor = tail.getAndSet(qNode);
        if (myPredecessor == null || myPredecessor.predecessor == AVAILABLE) {
            return true;
        }
        while (System.currentTimeMillis() - startTime < patience) {
            QNode predPredecessor = myPredecessor.predecessor;
            if (predPredecessor == AVAILABLE) {
                return true;
            } else if (predPredecessor != null) {
                myPredecessor = predPredecessor;
            }
        }
        //check successor
        if (!tail.compareAndSet(qNode, myPredecessor)) {
            qNode.predecessor = myPredecessor;
        }
        return false;
    }

    @Override
    public void unlock() {
        QNode qNode = myNode.get();
        //if cas fails, successor can enter
        if (!tail.compareAndSet(qNode, null)) {
            qNode.predecessor = AVAILABLE;
        }
    }

    public String requestInfoString(int requestNumber)
    {
        String request = "{" + Thread.currentThread().getName() + ":Request-" + requestNumber + "}";
        return request;
    }

    // Other Lock methods not needed for this simulation
	public int filterThread(String thread) {
		return Character.getNumericValue(thread.charAt(7));
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		return null;
	}
}