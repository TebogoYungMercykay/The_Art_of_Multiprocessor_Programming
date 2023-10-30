import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {
    private final AtomicReference<Node<T>> top = new AtomicReference<>(null);

    public void push(T item) {
        Node<T> newNode = new Node<>(item, null);
        while (true) {
            Node<T> oldTop = top.get();
            newNode.next.set(oldTop);
            if (top.compareAndSet(oldTop, newNode)) {
                return;
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> oldTop = top.get();
            if (oldTop == null) {
                return null;
            } else {
                T item = oldTop.item;
                Node<T> newTop = oldTop.next.get();
                if (top.compareAndSet(oldTop, newTop)) {
                    return item;
                }
            }
        }
    }
}
