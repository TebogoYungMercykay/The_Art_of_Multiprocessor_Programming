import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    final T item;
    final AtomicReference<Node<T>> next;

    public Node(T item, Node<T> next) {
        this.item = item;
        this.next = new AtomicReference<>(next);
    }
}