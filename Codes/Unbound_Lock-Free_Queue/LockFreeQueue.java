import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    private final Node<T> initialNode = new Node<>(null, null);
    private final AtomicReference<Node<T>> head = new AtomicReference<>(initialNode);
    private final AtomicReference<Node<T>> tail = new AtomicReference<>(initialNode);

    public void enq(T item) {
        Node<T> newNode = new Node<>(item, null);
        while (true) {
            Node<T> curTail = tail.get();
            Node<T> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                        return;
                    }
                }
            }
        }
    }

    public T deq() {
        while (true) {
            Node<T> curHead = head.get();
            Node<T> curTail = tail.get();
            Node<T> headNext = curHead.next.get();
            if (curHead == head.get()) {
                if (curHead == curTail) {
                    if (headNext == null) {
                        return null;
                    }
                    tail.compareAndSet(curTail, headNext);
                } else {
                    T item = headNext.item;
                    if (head.compareAndSet(curHead, headNext)) {
                        return item;
                    }
                }
            }
        }
    }
}
