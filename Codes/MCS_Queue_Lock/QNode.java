public class QNode {
    volatile boolean locked = false;
    volatile QNode next = null;
    // Some More Variables
    String threadName;
    int requestNumber = -1;
}