import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings({ "rawtypes" })
public class Node<T> {
    public T item;
    public int key;
    public Node next;
    public int person;
    String name;
    public long startTime, time;
    String tName;
    public Lock lock = new ReentrantLock();

    public Node(T item, int person, long time) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
        this.person = person;
        this.startTime = System.currentTimeMillis();
        this.time = time;
        this.tName = Thread.currentThread().getName();
        name = Thread.currentThread().getName() + " (P-" + person + ")";
    }

    public String getName() {
        return Thread.currentThread().getName() + " (P-" + person  + ", "+ (time - (System.currentTimeMillis() - startTime)) + "ms)";
    }
}
