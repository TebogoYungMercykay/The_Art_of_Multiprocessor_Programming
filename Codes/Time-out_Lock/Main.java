public class Main {
    public static void main(String[] args) {
        // Initialize the printer with either MCSQueue or Timeout
        Timeout lock = new Timeout(); // Change to Timeout if needed
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
