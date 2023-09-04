public class Main {
    public static void main(String[] args) {
        // Initializing the printer with either MCSQueue or Timeout
        MCSQueue lock = new MCSQueue();
        Printer printer = new Printer(lock);
        // Create and start nodes
        Node[] Node = new Node[5];
        for(int i = 0; i < 5; i++) {
            Node[i] = new Node(printer);
        }
        // Starting the nodes
        for(Node Node2: Node) {
            Node2.start();
        }
    }
}
