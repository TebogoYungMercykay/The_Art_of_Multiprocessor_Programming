@SuppressWarnings({ "unchecked", "rawtypes" })
public class LazyList<T> {
    private Node head;

    public LazyList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);
    }

    public boolean add(T item, int person, long time) {
        int key = item.hashCode();
        while (true) {
            Node predecessor = head;
            Node current = predecessor.next;
            while (current.key < key) {
                predecessor = current;
                current = current.next;
            }
            predecessor.lock.lock();
            try {
                current.lock.lock();
                if (validate(predecessor, current)) {
                    if (key == current.key) {
                        return false;
                    } else {
                        Node node = new Node(item, person, time);
                        node.next = current;
                        predecessor.next = node;
                        System.out.println(node.getName() + ": ADD ([" + person + "],[" + time + "])");
                        return true;
                    }
                }
            } finally {
                current.lock.unlock();
                predecessor.lock.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        printList();
        while (true) {
            Node predecessor = head;
            Node current = predecessor.next;
            while (current.key < key) {
                predecessor = current;
                current = current.next;
            }
            predecessor.lock.lock();
            try {
                current.lock.lock();
                if (validate(predecessor, current)) {
                    if (item == current.item) {
                        predecessor.next = current.next;
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                current.lock.unlock();
                predecessor.lock.unlock();
            }
        }
    }

    private boolean validate(Node predecessor, Node current) {
        return !predecessor.marked && !current.marked && predecessor.next == current;
    }

    private void printList() {
        Node current = head.next;
        String output = "List: ";
        if (current.person != -1) {
            output += "[" + current.tName + " (P-" + current.person + ", " + ((current.time - (System.currentTimeMillis() - current.startTime) > 0) ? (current.time - (System.currentTimeMillis() - current.startTime)) : 0) + "ms)]";
        }
        while (current.next != null) {
            current = current.next;
            if (current.person != -1) {
                output += " -> [" + current.tName + " (P-" + current.person + ", " + ((current.time - (System.currentTimeMillis() - current.startTime) > 0) ? (current.time - (System.currentTimeMillis() - current.startTime)) : 0) + "ms)]";
            }
        }
        System.out.println(output);
    }
}
