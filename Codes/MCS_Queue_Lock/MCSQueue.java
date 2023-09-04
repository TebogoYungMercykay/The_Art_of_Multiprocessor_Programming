import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;

public class MCSQueue implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSQueue() {
        this.tail = new AtomicReference<QNode>(null);
        this.myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode qNode = this.myNode.get();
        qNode.threadName = Thread.currentThread().getName();
        QNode predecessor = this.tail.getAndSet(qNode);
        if (predecessor != null) {
            qNode.locked = true;
            predecessor.next = qNode;
            while (qNode.locked) {}
        }
    }

    @Override
    public void unlock() {
        QNode qNode = this.myNode.get();
        if (qNode.next == null) {
            if (this.tail.compareAndSet(qNode, null)) {
                return;
            }
            while (qNode.next == null) {}
        }
        qNode.next.locked = false;
        // qNode.next = null;
    }

    public String requestInfoString(int requestNumber)
    {
        String request = "{" + Thread.currentThread().getName() + ":Request-" + requestNumber + "}";
        QNode successor = this.myNode.get().next;
        if(successor != null) {
            request += " -> {" + successor.threadName + ":Request-" + requestNumber + "}";
        }
        return request;
    }

    // Other Lock methods not needed for this simulation
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