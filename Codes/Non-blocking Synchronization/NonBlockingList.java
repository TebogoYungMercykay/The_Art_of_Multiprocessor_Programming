import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NonBlockingList<T> {
    private AtomicReference<Node<T>> head;

    public NonBlockingList() {
        head = new AtomicReference<>(new Node(Integer.MIN_VALUE, -1, 0));
        head.get().next = new Node(Integer.MAX_VALUE, -1, 0);
    }

    public boolean add(T item, int person, long time) {
        int key = item.hashCode();
        while (true) {
            Node<T> predecessor = head.get();
            Node<T> current = predecessor.next;
            while (current.key < key) {
                predecessor = current;
                current = current.next;
            }

            synchronized (predecessor) {
                synchronized (current) {
                    if (predecessor == head.get() && current == predecessor.next) {
                        if (key == current.key) {
                            return false; // Already exists
                        } else {
                            Node<T> newNode = new Node<>(item, person, time);
                            newNode.next = current;
                            predecessor.next = newNode;
                            System.out.println(newNode.getName() + ": ADD ([" + person + "],[" + time + "])");
                            return true;
                        }
                    }
                }
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        printList();
        while (true) {
            Node<T> predecessor = head.get();
            Node<T> current = predecessor.next;
            while (current.key < key) {
                predecessor = current;
                current = current.next;
            }

            synchronized (predecessor) {
                synchronized (current) {
                    if (predecessor == head.get() && current == predecessor.next) {
                        if (current.key == key) {
                            predecessor.next = current.next;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    public void printList() {
        Node<T> current = head.get().next;
        StringBuilder output = new StringBuilder("List: ");

        List<Node<T>> snapshot = new ArrayList<>();
        while (current != null) {
            snapshot.add(current);
            current = current.next;
        }
        for (Node<T> node : snapshot) {
            if (node.person != -1) {
                long remainingTime = node.time - (System.currentTimeMillis() - node.startTime);
                remainingTime = Math.max(remainingTime, 0);
                output.append("[").append(node.tName).append(" (P-").append(node.person).append(", ").append(remainingTime).append("ms)]");
            }
            if (node.next != null) {
                output.append(" -> ");
            }
        }
        String outputString = output.toString();
        System.out.println(outputString.substring(0, outputString.length() - 4));
    }
}
